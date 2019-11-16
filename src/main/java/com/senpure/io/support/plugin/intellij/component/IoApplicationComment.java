package com.senpure.io.support.plugin.intellij.component;

import com.intellij.openapi.components.BaseComponent;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.project.ProjectManager;
import com.intellij.openapi.project.VetoableProjectManagerListener;
import com.intellij.openapi.vfs.*;
import com.intellij.psi.*;
import com.intellij.psi.search.FileTypeIndex;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.psi.tree.IElementType;
import com.senpure.base.AppEvn;
import com.senpure.io.generator.model.Bean;
import com.senpure.io.generator.model.Enum;
import com.senpure.io.generator.model.Event;
import com.senpure.io.generator.model.Message;
import com.senpure.io.generator.reader.IoProtocolReader;
import com.senpure.io.generator.reader.IoReader;
import com.senpure.io.support.plugin.intellij.IoFileType;
import com.senpure.io.support.plugin.intellij.psi.IoFile;
import com.senpure.io.support.plugin.intellij.util.IoUtil;
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

    private long lastUpdate = 0;

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
                        afterRead(project, ioVirtualFileReader);
                    }
                }

                //  PsiFileFactory.
                //

                PsiManager.getInstance(project).addPsiTreeChangeListener(new PsiTreeChangeAdapter() {
                    @Override
                    public void childAdded(@NotNull PsiTreeChangeEvent event) {
//                        logger.debug("childAdded oldValue {} newValue {} child {}  oldChild {} newChild {} parent {}" +
//                                        " oldParent {} newParent {} element {} propertyName {}",
//                                event.getOldValue(), event.getNewValue()
//                                , event.getChild(), event.getOldChild(), event.getNewChild(),
//                                event.getParent(),
//                                event.getOldParent(), event.getNewParent()
//                                , event.getElement(),
//                                event.getPropertyName()
//                        );
                        logger.debug("childAdded {}", event.getChild());
                        if (!(event.getChild().getContainingFile() instanceof IoFile)) {
                            return;
                        }
                        IElementType type = event.getChild().getNode().getElementType();
                        if (checkElement(type) && checkUpdateTime()) {
                            updateRead(event.getChild());
                        }

                    }


                    @Override
                    public void childReplaced(@NotNull PsiTreeChangeEvent event) {
                        logger.debug("childReplaced {}", event.getChild());
                        if (!(event.getChild().getContainingFile() instanceof IoFile)) {
                            return;
                        }
                        IElementType type = event.getNewChild().getNode().getElementType();
                        if (checkElement(type) && checkUpdateTime()) {
                            updateRead(event.getNewChild());
                        }
                    }
                });

            }

            @Override
            public boolean canClose(@NotNull Project project) {

                return true;
            }
        });


        LocalFileSystem.getInstance().addVirtualFileListener(new VirtualFileContentsChangedAdapter() {


            protected void onFileChange(@NotNull VirtualFile fileOrDirectory) {
//
//                if (Objects.equals("io", fileOrDirectory.getExtension())) {
//                    logger.debug("文件改变 {}", fileOrDirectory.getPath());
//                    PsiFile psiFile = getPsiFile(fileOrDirectory);
//                    if (psiFile == null) {
//                        logger.warn("{} 没有找到对应的psiFile", fileOrDirectory.getPath());
//                        return;
//                    }
//                    // updateRead(psiFile.getProject(), fileOrDirectory);
//                }
            }

            @Override
            protected void onBeforeFileChange(@NotNull VirtualFile fileOrDirectory) {

            }

            @Override
            public void fileCopied(@NotNull VirtualFileCopyEvent event) {

//                if (Objects.equals("io", event.getFile().getExtension())) {
//                    logger.debug("文件复制 {}", event.getFile().getPath());
//                    PsiFile psiFile = getPsiFile(event.getFile());
//                    if (psiFile == null) {
//                        logger.warn("{} 没有找到对应的psiFile", event.getFile());
//                        return;
//                    }
//                    IoVirtualFileReader ioVirtualFileReader = new IoVirtualFileReader();
//                    IoReader ioReader = IoReader.getInstance(psiFile.getProject().getBasePath());
//                    ioVirtualFileReader.read(event.getFile(), ioReader.getIoProtocolReaderMap());
//                    afterRead(psiFile.getProject(), ioVirtualFileReader);
//                    ioReader.getIoProtocolReaderMap().
//                            put(event.getOriginalFile().getPath(), ioVirtualFileReader);
//
//                }
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

    private boolean checkElement(IElementType type) {
        if (type.
                equals(TokenType.ERROR_ELEMENT)
        ) {
            return false;
        }
        return true;
    }

    private boolean checkUpdateTime() {
        long now = System.currentTimeMillis();
        if (now - lastUpdate >= 333) {
            return true;
        }
        return false;
    }

    private void updateRead(Project project, String filePath,String text) {
        IoVirtualFileReader ioVirtualFileReader = new IoVirtualFileReader();
        IoReader ioReader = IoReader.getInstance(project.getBasePath());
        ioVirtualFileReader.read(filePath,text, ioReader.getIoProtocolReaderMap());
        afterRead(project, ioVirtualFileReader);
        if (!ioVirtualFileReader.isSyntaxError()) {
            logger.debug("替换 {}", filePath);
            ioReader.replace(filePath, ioVirtualFileReader);
        } else {
            IoVirtualFileReader old = (IoVirtualFileReader) ioReader.getIoProtocolReaderMap().
                    get(filePath);
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

    private void updateRead(PsiElement element) {
        lastUpdate = System.currentTimeMillis();

       // logger.debug("{}", element.getContainingFile().getVirtualFile());
        VirtualFile virtualFile = element.getContainingFile().getVirtualFile();
        updateRead(element.getProject(), virtualFile.getPath(),element.getContainingFile().getText());

    }

    private void afterRead(Project project, IoVirtualFileReader fileReader) {
        if (!fileReader.isSyntaxError()) {
            IoUtil.setFileNamespace(fileReader.getFilePath(), fileReader.getNamespace());
            for (Message message : fileReader.getMessages()) {
                IoUtil.setMessageId(project.getBasePath(), message.getNamespace(), message.getType(), message.getName(), message.getId());
                IoUtil.setBeanIdentity(fileReader.getFilePath(), message);
            }
            for (Event event : fileReader.getEvents()) {
                IoUtil.setEventId(project.getBasePath(), event.getNamespace(), event.getName(), event.getId());
                IoUtil.setBeanIdentity(fileReader.getFilePath(), event);
            }
            for (Enum anEnum : fileReader.getEnums()) {
                IoUtil.setBeanIdentity(fileReader.getFilePath(), anEnum);
            }
            for (Bean bean : fileReader.getBeans()) {
                IoUtil.setBeanIdentity(fileReader.getFilePath(), bean);
            }
        }
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
