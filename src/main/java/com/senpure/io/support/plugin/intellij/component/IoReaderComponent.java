package com.senpure.io.support.plugin.intellij.component;

import com.intellij.openapi.components.ProjectComponent;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.project.ProjectManager;
import com.intellij.openapi.vfs.*;
import com.senpure.base.AppEvn;
import com.senpure.io.generator.reader.IoProtocolReader;
import com.senpure.io.generator.reader.IoReader;
import com.senpure.io.support.plugin.intellij.util.IoUtil;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * IoReaderComponent
 *
 * @author senpure
 * @time 2019-06-13 14:37:31
 */
public class IoReaderComponent implements ProjectComponent {

    static {
        AppEvn.markClassRootPath(IoReaderComponent.class);
        AppEvn.installAnsiConsole(IoReaderComponent.class);
    }

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public void projectOpened() {

        LocalFileSystem.getInstance().addVirtualFileListener(new VirtualFileContentsChangedAdapter() {
            @Override
            protected void onFileChange(@NotNull VirtualFile fileOrDirectory) {

                logger.debug("文件改变 {}", fileOrDirectory.getUrl());
                if (Objects.equals("io", fileOrDirectory.getExtension())) {
                    IoVirtualFileReader ioVirtualFileReader = new IoVirtualFileReader();
                   // ioVirtualFileReader.read(fileOrDirectory, IoReader.getInstance().getIoProtocolReaderMap());
                   // if (!ioVirtualFileReader.isHasError()) {
                       // IoReader.getInstance().replace(fileOrDirectory.getPath(), ioVirtualFileReader);
                   // }
                }
            }

            @Override
            protected void onBeforeFileChange(@NotNull VirtualFile fileOrDirectory) {

            }

            @Override
            public void fileCopied(@NotNull VirtualFileCopyEvent event) {
                logger.debug("文件复制 {}", event.getFile());
                if (Objects.equals("io", event.getFile().getExtension())) {
                    IoVirtualFileReader ioVirtualFileReader = new IoVirtualFileReader();
                    ioVirtualFileReader.read(event.getFile(), IoReader.getInstance().getIoProtocolReaderMap());
                    if (!ioVirtualFileReader.isHasError()) {
                      //  IoReader.getInstance().replace(event.getOriginalFile().getPath(), ioVirtualFileReader);
                    }
                }
            }

            @Override
            public void fileDeleted(@NotNull VirtualFileEvent event) {
                logger.debug("文件删除 {}", event.getFile());
               // IoReader.getInstance().getIoProtocolReaderMap().remove(event.getFile().getPath());
            }
        });
        List<VirtualFile> list = new ArrayList<>(16);
        for (Project openProject : ProjectManager.getInstance().getOpenProjects()) {
            IoUtil.findEntities(openProject, "Student");
            logger.debug("打开Project {}  -> {}", openProject.getName(), openProject.getBasePath());
           // findIo(list, LocalFileSystem.getInstance().findFileByPath(openProject.getBasePath()));
        }

        for (VirtualFile file : list) {
            logger.debug(file.getPath());
        }
        for (VirtualFile file : list) {

            IoVirtualFileReader ioVirtualFileReader = new IoVirtualFileReader();
            Map<String, IoProtocolReader> ioProtocolReaderMap = IoReader.getInstance().getIoProtocolReaderMap();
            if (ioProtocolReaderMap.get(file.getPath()) == null) {
                ioVirtualFileReader.read(file, ioProtocolReaderMap);
                ioProtocolReaderMap.put(file.getPath(), ioVirtualFileReader);
            }
        }

    }

    private void findIo(List<VirtualFile> list, VirtualFile file) {
        VirtualFile[] files = VfsUtil.getChildren(file);
        for (VirtualFile virtualFile : files) {
            if (virtualFile.isDirectory()) {
                findIo(list, virtualFile);
            } else if (Objects.equals("io", virtualFile.getExtension())) {
                list.add(virtualFile);
            }
        }

    }
}
