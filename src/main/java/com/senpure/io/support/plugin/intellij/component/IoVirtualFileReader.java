package com.senpure.io.support.plugin.intellij.component;

import com.intellij.openapi.vfs.LocalFileSystem;
import com.intellij.openapi.vfs.VirtualFile;
import com.senpure.io.antlr.IoParser;
import com.senpure.io.generator.reader.IoErrorListener;
import com.senpure.io.generator.reader.IoProtocolReader;
import com.senpure.template.FileUtil;
import org.antlr.v4.runtime.CharStreams;

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

    @Override
    public void enterImportValue(IoParser.ImportValueContext ctx) {
        String path = ctx.getText();
        this.importIos.add(path);
        File importFile = FileUtil.file(path, (new File(this.filePath)).getParent());
        if (importFile.exists()) {
            if (ioProtocolReaderMap.get(importFile.getAbsolutePath()) == null) {
                VirtualFile file = LocalFileSystem.getInstance().findFileByPath(importFile.getAbsolutePath());
                IoVirtualFileReader reader = new IoVirtualFileReader();
                reader.read(file, ioProtocolReaderMap);
                ioProtocolReaderMap.put(file.getPath(), reader);
            }
            this.importKeys.add(importFile.getAbsolutePath());
        } else {
            this.checkErrorBuilder();
            this.errorBuiler.append(this.filePath).append("引用文件 不存在 ").append(path);
        }

    }

    @Override
    public void exitProtocol(IoParser.ProtocolContext ctx) {

    }

    public void read(VirtualFile file, Map<String, IoProtocolReader> ioProtocolReaderMap) {
        this.filePath = file.getPath();
        this.ioProtocolReaderMap = ioProtocolReaderMap;
        this.ioErrorListener = new IoErrorListener(filePath);
        try {
            read(CharStreams.fromStream(file.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
