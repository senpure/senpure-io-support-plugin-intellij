package com.senpure.io.support.plugin.intellij.component;

import com.intellij.openapi.vfs.LocalFileSystem;
import com.intellij.openapi.vfs.VirtualFile;
import com.senpure.io.antlr.IoParser;
import com.senpure.io.generator.model.Event;
import com.senpure.io.generator.model.Message;
import com.senpure.io.generator.reader.IoErrorListener;
import com.senpure.io.generator.reader.IoProtocolReader;
import com.senpure.io.support.plugin.intellij.util.IoUtil;
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


    @Override
    protected void read(CharStream input) {
        super.read(input);
        if (!isHasError()) {
            IoUtil.setFileNamespace(filePath, getNamespace());

            for (Message message : getMessages()) {
                IoUtil.setMessageId(message.getNamespace(), message.getType(), message.getName(), message.getId());
            }
            for (Event event : getEvents()) {
                IoUtil.setEventId(event.getNamespace(), event.getName(), event.getId());
            }
        }
    }
}
