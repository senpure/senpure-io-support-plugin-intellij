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
            // String parent = LocalFileSystem.getInstance().findFileByPath(filePath).getParent().getPath();
            String parent = new File(filePath).getParent();
            File importFile = FileUtil.file(path, parent);
            if (importFile.exists()) {
                //统一用虚拟文件系统避免文件分隔符不相同的情况
                VirtualFile file = LocalFileSystem.getInstance().findFileByPath(importFile.getAbsolutePath());
                if (ioProtocolReaderMap.get(file.getPath()) == null) {

                    IoVirtualFileReader reader = new IoVirtualFileReader();
                    reader.read(file, ioProtocolReaderMap);
                    ioProtocolReaderMap.put(file.getPath(), reader);
                }
                importKeys.add(file.getPath());
            } else {

                // logger.debug("path {}  parent {} vf {}",path,parent,LocalFileSystem.getInstance().findFileByPath(importFile.getAbsolutePath()));
                checkErrorBuilder();
                errorBuilder.append(filePath).append("引用文件 不存在 ").append(path);

            }
        }

    }

    @Override
    public void exitProtocol(IoParser.ProtocolContext ctx) {

    }

    public void read(VirtualFile file, Map<String, IoProtocolReader> ioProtocolReaderMap) {
        filePath = file.getPath();
        super.ioProtocolReaderMap = ioProtocolReaderMap;
        ioErrorListener = new IoErrorListener(filePath);
        try {
            read(CharStreams.fromStream(file.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void read(String filePath,String text, Map<String, IoProtocolReader> ioProtocolReaderMap) {
        this.filePath=filePath;
        super.ioProtocolReaderMap = ioProtocolReaderMap;
        ioErrorListener = new IoErrorListener(filePath);
        try {
            read(CharStreams.fromString(text));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected boolean canWalk2() {
        return true;
    }

    @Override
    protected void read(CharStream input) {
        try {
            super.read(input);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
