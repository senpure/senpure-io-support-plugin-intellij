package com.senpure.io.support.plugin.intellij;

import com.intellij.openapi.fileTypes.FileTypeConsumer;
import com.intellij.openapi.fileTypes.FileTypeFactory;
import org.jetbrains.annotations.NotNull;

/**
 * IoFileTypeFactory
 *
 * @author senpure
 * @time 2019-05-21 20:03:02
 */
public class IoFileTypeFactory extends FileTypeFactory {
    @Override
    public void createFileTypes(@NotNull FileTypeConsumer fileTypeConsumer) {
        fileTypeConsumer.consume(IoFileType.INSTANCE);
    }
}
