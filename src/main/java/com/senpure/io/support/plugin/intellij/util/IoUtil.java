package com.senpure.io.support.plugin.intellij.util;

import com.intellij.lang.ASTNode;
import com.intellij.navigation.NavigationItem;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiManager;
import com.intellij.psi.TokenType;
import com.intellij.psi.search.FileTypeIndex;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.psi.util.PsiTreeUtil;
import com.senpure.io.generator.model.Bean;
import com.senpure.io.generator.model.Enum;
import com.senpure.io.generator.reader.IoProtocolReader;
import com.senpure.io.generator.reader.IoReader;
import com.senpure.io.support.plugin.intellij.IoFileType;
import com.senpure.io.support.plugin.intellij.psi.IoBean;
import com.senpure.io.support.plugin.intellij.psi.IoBeanName;
import com.senpure.io.support.plugin.intellij.psi.IoEntity;
import com.senpure.io.support.plugin.intellij.psi.IoFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * IoUtil
 *
 * @author senpure
 * @time 2019-06-13 19:31:42
 */
public class IoUtil {

    private static Logger logger = LoggerFactory.getLogger(IoUtil.class);

    public static ASTNode preNode(ASTNode node) {

        ASTNode prev = node.getTreePrev();

        if (prev == null) {
            return null;
        }
        if (prev.getElementType().equals(TokenType.WHITE_SPACE)) {
            return preNode(prev);
        }
        return prev;
    }

    private static ASTNode preNode(ASTNode node, int times, int num) {
        ASTNode prev = node.getTreePrev();

        if (prev == null) {
            return null;
        }
        if (prev.getElementType().equals(TokenType.WHITE_SPACE)) {
            return preNode(prev, times, num);
        }
        num++;
        if (num >= times) {
            return prev;
        }
        return preNode(prev, times, num);
    }

    public static ASTNode preNode(ASTNode node, int times) {


        return preNode(node, times, 0);
    }

    public static Icon getIoIcon() {
        return IoFileType.FILE;
    }

    public static List<IoBeanName> findNames(Project project) {
        Collection<VirtualFile> virtualFiles =
                FileTypeIndex.getFiles(IoFileType.INSTANCE, GlobalSearchScope.allScope(project));
        List<IoBeanName> results = new ArrayList<>();
        for (VirtualFile virtualFile : virtualFiles) {
            IoFile ioFile = (IoFile) PsiManager.getInstance(project).findFile(virtualFile);
            if (ioFile != null) {
                IoEntity[] ioEntities = PsiTreeUtil.getChildrenOfType(ioFile, IoEntity.class);
                if (ioEntities != null) {
                    for (IoEntity ioEntity : ioEntities) {
                        IoBean bean = ioEntity.getBean();
                        if (bean != null) {
                            IoBeanName ioBeanName = bean.getBeanName();
                            if (ioBeanName != null) {
                                results.add(ioBeanName);
                            }
                        }
                    }
                }

            }
        }
       // logger.debug("findNames {} ", results.size());
        return results;
    }

    public static List<String> findEntities(Project project) {
        List<String> names = new ArrayList<>();
        for (IoProtocolReader value : IoReader.getInstance().getIoProtocolReaderMap().values()) {
            for (Enum anEnum : value.getEnums()) {
                names.add(anEnum.getName());
            }
            for (Bean bean : value.getBeans()) {
                names.add(bean.getName());
            }
            for (Bean bean : value.getMessages()) {
                names.add(bean.getName());
            }
            for (Bean bean : value.getEvents()) {
                names.add(bean.getName());
            }
        }

        return names;

    }

    public static NavigationItem[] findEntities(Project project, String name) {


        List<NavigationItem> results = new ArrayList<>();
        Collection<VirtualFile> virtualFiles =
                FileTypeIndex.getFiles(IoFileType.INSTANCE, GlobalSearchScope.allScope(project));

        for (VirtualFile virtualFile : virtualFiles) {
            IoFile ioFile = (IoFile) PsiManager.getInstance(project).findFile(virtualFile);

        }
        // PsiTreeUtil.findChildrenOfType()
        return null;
    }
}
