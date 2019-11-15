package com.senpure.io.support.plugin.intellij.component;

import com.intellij.openapi.components.BaseComponent;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.project.ProjectManager;
import com.intellij.openapi.project.VetoableProjectManagerListener;
import com.intellij.openapi.vfs.*;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiManager;
import com.intellij.psi.search.FileTypeIndex;
import com.intellij.psi.search.GlobalSearchScope;
import com.senpure.base.AppEvn;
import com.senpure.io.generator.model.Bean;
import com.senpure.io.generator.model.Enum;
import com.senpure.io.generator.reader.IoProtocolReader;
import com.senpure.io.generator.reader.IoReader;
import com.senpure.io.support.plugin.intellij.IoFileType;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.Map;
import java.util.Objects;

/**
 * IoApplicationComment
 *
 * @author senpure
 * @time 2019-11-15 13:33:52
 */
public class IoApplicationComment implements BaseComponent {

    static {
        AppEvn.markClassRootPath(IoApplicationComment.class);
        AppEvn.installAnsiConsole(IoApplicationComment.class);
    }

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public void initComponent() {

        ProjectManager.getInstance().addProjectManagerListener(new VetoableProjectManagerListener() {
            @Override
            public void projectOpened(@NotNull Project project) {
                logger.debug("打开{} {}", project.getName(), project.getBasePath());
                IoReader reader = IoReader.getInstance(project.getBasePath());
                Collection<VirtualFile> files = FileTypeIndex.getFiles(IoFileType.INSTANCE, GlobalSearchScope.projectScope(project));
                for (VirtualFile file : files) {
                    logger.debug("{}", file.getPath());
                }
                for (VirtualFile file : files) {
                    IoVirtualFileReader ioVirtualFileReader = new IoVirtualFileReader();
                    Map<String, IoProtocolReader> ioProtocolReaderMap = reader.getIoProtocolReaderMap();
                    if (ioProtocolReaderMap.get(file.getPath()) == null) {
                        ioVirtualFileReader.read(file, ioProtocolReaderMap);
                        ioProtocolReaderMap.put(file.getPath(), ioVirtualFileReader);
                    }
                }

            }

            @Override
            public boolean canClose(@NotNull Project project) {

                return true;
            }
        });


        LocalFileSystem.getInstance().addVirtualFileListener(new VirtualFileContentsChangedAdapter() {
            @Override
            protected void onFileChange(@NotNull VirtualFile fileOrDirectory) {

                if (Objects.equals("io", fileOrDirectory.getExtension())) {
                    logger.debug("文件改变 {}", fileOrDirectory.getPath());
                    PsiFile psiFile = getPsiFile(fileOrDirectory);
                    if (psiFile == null) {
                        logger.warn("{} 没有找到对应的psiFile", fileOrDirectory.getPath());
                        return;
                    }
                    IoVirtualFileReader ioVirtualFileReader = new IoVirtualFileReader();
                    IoReader ioReader = IoReader.getInstance(psiFile.getProject().getBasePath());
                    ioVirtualFileReader.read(fileOrDirectory, ioReader.getIoProtocolReaderMap());
                    if (!ioVirtualFileReader.isSyntaxError()) {
                        logger.debug("替换 {}", fileOrDirectory.getPath());
                        ioReader.replace(fileOrDirectory.getPath(), ioVirtualFileReader);
                    } else {
                        IoVirtualFileReader old = (IoVirtualFileReader) ioReader.getIoProtocolReaderMap().
                                get(fileOrDirectory.getPath());
                        if (old != null) {
                            for (Bean bean : ioVirtualFileReader.getBeans()) {
                                if (!old.getBeans().contains(bean)) {
                                    old.getBeans().add(bean);
                                }
                            }
                            for (Enum bean : ioVirtualFileReader.getEnums()) {
                                if (!old.getEnums().contains(bean)) {
                                    old.getEnums().add(bean);
                                }
                            }
                        }
                    }
                }
            }

            @Override
            protected void onBeforeFileChange(@NotNull VirtualFile fileOrDirectory) {

            }

            @Override
            public void fileCopied(@NotNull VirtualFileCopyEvent event) {

                if (Objects.equals("io", event.getFile().getExtension())) {
                    logger.debug("文件复制 {}", event.getFile().getPath());
                    PsiFile psiFile = getPsiFile(event.getFile());
                    if (psiFile == null) {
                        logger.warn("{} 没有找到对应的psiFile", event.getFile());
                        return;
                    }
                    IoVirtualFileReader ioVirtualFileReader = new IoVirtualFileReader();
                    IoReader ioReader = IoReader.getInstance(psiFile.getProject().getBasePath());
                    ioVirtualFileReader.read(event.getFile(), ioReader.getIoProtocolReaderMap());
                    ioReader.getIoProtocolReaderMap().
                            put(event.getOriginalFile().getPath(), ioVirtualFileReader);

                }
            }

            @Override
            public void fileDeleted(@NotNull VirtualFileEvent event) {
                if (Objects.equals("io", event.getFile().getExtension())) {
                    logger.debug("文件删除 {}", event.getFile().getPath());
                    PsiFile psiFile = getPsiFile(event.getFile());
                    if (psiFile == null) {
                        logger.warn("{} 没有找到对应的psiFile", event.getFile());
                        return;
                    }
                    IoReader.getInstance(psiFile.getProject().getBasePath()).getIoProtocolReaderMap().remove(event.getFile().getPath());
                }

            }
        });
    }

    private PsiFile getPsiFile(VirtualFile virtualFile) {
        PsiFile psiFile = null;
        for (Project project : ProjectManager.getInstance().getOpenProjects()) {
            psiFile = PsiManager.getInstance(project).findFile(virtualFile);
            if (psiFile != null) {
                break;
            }
        }
        return psiFile;
    }
}
