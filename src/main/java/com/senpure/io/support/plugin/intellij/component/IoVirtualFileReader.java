package com.senpure.io.support.plugin.intellij.component;

import com.intellij.openapi.vfs.LocalFileSystem;
import com.intellij.openapi.vfs.VirtualFile;
import com.senpure.io.antlr.IoParser;
import com.senpure.io.generator.reader.IoErrorListener;
import com.senpure.io.generator.reader.IoProtocolReader;
import com.senpure.template.FileUtil;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.Map;

/**
 * IoVirtualFileReader
 *
 * @author senpure
 * @time 2019-06-13 17:18:05
 */
public class IoVirtualFileReader extends IoProtocolReader {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public void enterImportValue(IoParser.ImportValueContext ctx) {

        String path = ctx.getText();

        if (path.length() > 0) {
            importIos.add(path);

        }

    }

    @Override
    public void exitProtocol(IoParser.ProtocolContext ctx) {

    }

    @Override
    protected void readImports() {
        for (String path : importIos) {
            String parent = new File(filePath).getParent();
            File importFile = FileUtil.file(path, parent);
            if (importFile.exists()) {
                //统一用虚拟文件系统避免文件分隔符不相同的情况
                VirtualFile file = LocalFileSystem.getInstance().findFileByPath(importFile.getAbsolutePath());
                read(file, ioProtocolReaderMap);
                importKeys.add(file.getPath());
            } else {
                // logger.debug("path {}  parent {} vf {}",path,parent,LocalFileSystem.getInstance().findFileByPath(importFile.getAbsolutePath()));
                checkErrorBuilder();
                errorBuilder.append(filePath).append("引用文件 不存在 ").append(path);
            }
        }
    }

    public static IoProtocolReader read(VirtualFile file, Map<String, IoProtocolReader> ioProtocolReaderMap) {
        IoProtocolReader ioProtocolReader = ioProtocolReaderMap.get(file.getPath());
        if (ioProtocolReader == null) {
            IoVirtualFileReader virtualFileReader = new IoVirtualFileReader();
            try {
                ioProtocolReaderMap.put(file.getPath(), virtualFileReader);
                virtualFileReader.read(file.getPath(), CharStreams.fromStream(file.getInputStream()), ioProtocolReaderMap);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return virtualFileReader;
        }
        return ioProtocolReader;
    }

    public static IoProtocolReader read(String filePath, String text, Map<String, IoProtocolReader> ioProtocolReaderMap) {
        IoProtocolReader ioProtocolReader = ioProtocolReaderMap.get(filePath);
        if (ioProtocolReader == null) {
            IoVirtualFileReader virtualFileReader = new IoVirtualFileReader();
            ioProtocolReaderMap.put(filePath, virtualFileReader);
            CharStream input = CharStreams.fromString(text);
            virtualFileReader.read(filePath, input, ioProtocolReaderMap);
            return virtualFileReader;
        }
        return ioProtocolReader;
    }

    private void read(String filePath, CharStream input, Map<String, IoProtocolReader> ioProtocolReaderMap) {
        this.filePath = filePath;
        this.ioProtocolReaderMap = ioProtocolReaderMap;
        ioErrorListener = new IoErrorListener(filePath);
        read(input);
    }


}
