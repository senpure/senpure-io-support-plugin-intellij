package com.senpure.io.support.plugin.intellij.util;

import com.intellij.lang.ASTNode;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.module.ModuleUtil;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.TextRange;
import com.intellij.openapi.vfs.LocalFileSystem;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiManager;
import com.intellij.psi.TokenType;
import com.intellij.psi.search.FileTypeIndex;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.psi.tree.IElementType;
import com.intellij.psi.util.PsiTreeUtil;
import com.senpure.io.generator.model.Bean;
import com.senpure.io.generator.model.Event;
import com.senpure.io.generator.model.Field;
import com.senpure.io.generator.model.Message;
import com.senpure.io.support.plugin.intellij.IoFileType;
import com.senpure.io.support.plugin.intellij.IoIcons;
import com.senpure.io.support.plugin.intellij.psi.*;
import com.senpure.template.FileUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

/**
 * IoUtil
 *
 * @author senpure
 * @time 2019-06-13 19:31:42
 */
public class IoUtil {

    private static Logger logger = LoggerFactory.getLogger(IoUtil.class);

    /**
     * 根据文件路径,获取命名空间
     */
    private static Map<String, String> fileNamespaceMap = new HashMap<>();
    /**
     * key filePath + bean.type + bean.name + [bean.id]
     */
    private static Map<String, Bean> beanMap = new HashMap<>();
    private static Map<String, Integer> beanLastIndexMap = new HashMap<>();
    /**
     * key projectPath+命名空间+messageType+name
     */
    private static Map<String, Integer> messageIdMap = new HashMap<>();
    private static Map<String, Integer> namespaceMaxMessageIdMap = new HashMap<>();

    private static Map<String, Integer> eventIdMap = new HashMap<>();
    private static Map<String, Integer> namespaceMaxEventIdMap = new HashMap<>();
    private static int maxMessageId = 0;
    private static int maxEventId = 0;


    private static String getKey(String projectPath, String namespace, String messageType, String name) {
        return projectPath + namespace + messageType + name;
    }

    private static String getKey(String projectPath, String namespace, String name) {
        return projectPath + namespace + name;
    }

    public static void setMessageId(String projectPath, String namespace, String messageType, String name, Integer messageId) {

        messageIdMap.put(getKey(projectPath, namespace, messageType, name), messageId);
        Integer namespaceMaxId = namespaceMaxMessageIdMap.get(namespace);
        if (namespaceMaxId == null || messageId > namespaceMaxId) {
            namespaceMaxMessageIdMap.put(namespace, messageId);
        }
        if (messageId > maxMessageId) {
            maxMessageId = messageId;
        }
    }

    public static void setEventId(String projectPath, String namespace, String name, Integer eventId) {

        eventIdMap.put(getKey(projectPath, namespace, name), eventId);
        Integer namespaceMaxId = namespaceMaxEventIdMap.get(namespace);
        if (namespaceMaxId == null || eventId > namespaceMaxId) {
            namespaceMaxEventIdMap.put(namespace, eventId);
        }
        if (eventId > maxEventId) {
            maxEventId = eventId;
        }
    }

    private static int initMessageId(boolean cs) {
        int messageId = initId(maxMessageId);

        return cs ? messageId + 1 : messageId + 2;
    }

    private static int initId(int maxId) {
        int id;
        if (maxId == 0) {
            id = 10000;
        } else {
            String str = maxId + "";
            int temp = (int) Math.pow(10, str.length() - 1);
            id = maxId / temp * temp;
        }
        if (id < 10000) {
            id = 10000;
        }
        return id;
    }

    private static int initEventId() {
        return initId(maxEventId);
    }

    public static Integer getAutoEventId(String projectPath, String namespace, String name) {
        Integer namespaceMaxId = namespaceMaxEventIdMap.get(projectPath + namespace);
        if (namespaceMaxId == null) {
            return initEventId();
        }
        return namespaceMaxId + 1;
    }


    public static Integer getAutoMessageId(String projectPath, String namespace, String messageType, String name) {
        Integer otherId;
        if (messageType.equalsIgnoreCase("CS")) {
            otherId = messageIdMap.get(getKey(projectPath, namespace, "SC", name));
            if (otherId != null) {
                return otherId - 1;
            } else {
                Integer namespaceMaxId = namespaceMaxMessageIdMap.get(namespace);
                if (namespaceMaxId == null) {
                    return initMessageId(true);
                } else {
                    if (namespaceMaxId % 2 == 0) {
                        return namespaceMaxId + 1;
                    } else {
                        return namespaceMaxId + 2;
                    }
                }
            }
        } else {
            otherId = messageIdMap.get(getKey(namespace, "CS", name));
            if (otherId != null) {
                return otherId + 1;
            } else {
                Integer namespaceMaxId = namespaceMaxMessageIdMap.get(namespace);
                if (namespaceMaxId == null) {
                    return initMessageId(false);
                } else {
                    if (namespaceMaxId % 2 == 0) {
                        return namespaceMaxId + 2;
                    } else {
                        return namespaceMaxId + 1;
                    }
                }
            }
        }
    }

    public static void setBeanIdentity(String filePath, Bean bean) {

        beanMap.put(filePath + bean.getName(), bean);

    }

    public static void setBeanIdentity(String filePath, Event bean) {
        beanMap.put(filePath + bean.getName() + bean.getId(), bean);
    }

    public static void setBeanIdentity(String filePath, Message bean) {
        beanMap.put(filePath + bean.getType() + bean.getName() + bean.getId(), bean);
        beanMap.put(filePath + (bean.getType().toLowerCase()) + bean.getName() + bean.getId(), bean);
    }

    public static Bean getBean(String filePath, String identity) {
        Bean bean = beanMap.get(filePath + identity);
        return bean;
    }

    public static void markLastIndex(String filePath, String identity, int index) {
        beanLastIndexMap.put(filePath + identity, index);
    }

    public static int getLastIndex(String filePath, String identity) {
        Integer integer = beanLastIndexMap.get(filePath + identity);
        return integer == null ? 0 : integer;
    }

    private static int addIndex(Set<Integer> indexes, int index, List<Integer> infer) {
        if (indexes.add(index)) {
            return index;
        }
        index = infer.get(0);
        infer.set(0, index + 1);
        return addIndex(indexes, index, infer);
    }

    public static int getBeanNextIndex(String filePath, String identity) {
        int index = 0;
        Bean bean = beanMap.get(filePath + identity);
        if (bean != null) {
            //读取index是如果没有显示指定,则是单独从1开始增值,这里推测一个正确的index;
            List<Integer> infer = new ArrayList<>();
            infer.add(1);
            Set<Integer> indexes = new HashSet<>();
            for (Field field : bean.getFields()) {
                int temp = addIndex(indexes, field.getIndex(), infer);
                index = temp > index ? temp : index;
            }
           // index += bean.getFields().size() - indexes.size();
            int lastIndex = getLastIndex(filePath, identity);
            if (index < lastIndex) {
                return 0;
            }
            return index + 1;
        } else {
            return 0;
        }
    }


    public static String getNextText(int start, Document document) {

        int end = document.getTextLength();
        String nextText = "";
        while (start < end) {
            nextText = document.getText(new TextRange(start, ++start));
            if (!nextText.equals(" ")) {
                break;
            }
        }
        return nextText;
    }


    public static void setFileNamespace(String filePath, String namespace) {
        fileNamespaceMap.put(filePath, namespace);
    }

    public static String getFileNamespace(String filePath) {
        return fileNamespaceMap.get(filePath);
    }

    public static IElementType getPreEffectiveSiblingElementType(ASTNode node) {
        ASTNode pre = getPreEffectiveSibling(node);
        return pre == null ? null : pre.getElementType();
    }


    /**
     * 获取下一个非 TokenType.WHITE_SPACE 节点
     *
     * @param node
     * @return
     */
    public static ASTNode getNexEffectiveSibling(ASTNode node) {

        ASTNode prev = node.getTreeNext();

        if (prev == null) {
            return null;
        }
        if (prev.getElementType().equals(TokenType.WHITE_SPACE)) {
            return getNexEffectiveSibling(prev);
        }
        return prev;
    }

    /**
     * 获取上一个非 TokenType.WHITE_SPACE 同级节点
     *
     * @param node
     * @return
     */
    public static ASTNode getPreEffectiveSibling(ASTNode node) {
        return getPreEffectiveSibling(node, 1);
    }

    /**
     * 获取上{times} 个非 TokenType.WHITE_SPACE 同级节点
     *
     * @param node
     * @param times
     * @return
     */
    public static ASTNode getPreEffectiveSibling(ASTNode node, int times) {

        return getPreEffectiveSibling(node, times, 0);
    }

    private static ASTNode getPreEffectiveSibling(ASTNode node, int times, int num) {
        ASTNode prev = node.getTreePrev();
        if (prev == null) {
            return null;
        }
        if (prev.getElementType().equals(TokenType.WHITE_SPACE)) {
            return getPreEffectiveSibling(prev, times, num);
        }
        num++;
        if (num >= times) {
            return prev;
        }
        return getPreEffectiveSibling(prev, times, num);
    }


    public static Icon getIoIcon() {
        return IoIcons.FILE;
    }


    public static List<IoFieldType> findFieldTypes(Project project, String name) {
        List<IoFieldType> results = new ArrayList<>();
        Collection<VirtualFile> virtualFiles =
                FileTypeIndex.getFiles(IoFileType.INSTANCE, GlobalSearchScope.allScope(project));
        for (VirtualFile virtualFile : virtualFiles) {
            IoFile ioFile = (IoFile) PsiManager.getInstance(project).findFile(virtualFile);
            if (ioFile != null) {
                IoEntity[] ioEntities = PsiTreeUtil.getChildrenOfType(ioFile, IoEntity.class);
                if (ioEntities != null) {
                    List<IoField> fields = new ArrayList<>();
                    for (IoEntity ioEntity : ioEntities) {
                        IoBean bean = ioEntity.getBean();
                        if (bean != null) {
                            fields.addAll(bean.getFieldList());
                        }
                        IoMessage message = ioEntity.getMessage();
                        if (message != null) {
                            fields.addAll(message.getFieldList());
                        }
                        IoEvent event = ioEntity.getEvent();
                        if (event != null) {
                            fields.addAll(event.getFieldList());
                        }
                    }
                    for (IoField field : fields) {
                        IoFieldType ioFieldType = field.getFieldType();
                        if (ioFieldType != null) {
                            if (Objects.equals(ioFieldType.getText(), name)) {
                                results.add(ioFieldType);
                            }
                        }
                    }
                }
            }

        }

        return results;

    }


    public static String findBeanIdentity(ASTNode node) {
        if (node == null) {
            return null;
        }
        String identity = "";
        boolean start = false;
        ASTNode pre = node;
        do {
            pre = getPreEffectiveSibling(pre);
            if (pre != null) {
                String text = pre.getText();
                if (text.equals("}")) {
                    break;
                } else if (text.equals("bean")
                        || text.equals("enum")
                        || text.equals("message")
                        || text.equals("event")
                ) {

                    return identity;
                }
                if (start) {
                    identity = pre.getText() + identity;
                }
                if (text.equals("{")) {
                    start = true;
                }
            }
        } while (pre != null);

        return null;
    }

    /**
     * 获取引用文件含自己本身
     *
     * @return
     */
    public static List<String> getImports(PsiElement element) {
        IoFile ioFile = (IoFile) element.getContainingFile();
        IoHead ioHead = ioFile.findChildByClass(IoHead.class);
        List<String> imports = new ArrayList<>(16);
        String filePath = getFilePath(element);
        imports.add(filePath);
        if (ioHead != null) {
            List<IoHeadContent> headContents = ioHead.getHeadContentList();
            for (IoHeadContent headContent : headContents) {
                if (headContent.getImport() != null) {
                    String parent = LocalFileSystem.getInstance().findFileByPath(filePath).getParent().getPath();
                    File importFile = FileUtil.file(headContent.getImport().
                            getImportValue().getText(), parent);
                    if (importFile.exists()) {
                        VirtualFile file = LocalFileSystem.getInstance().findFileByPath(importFile.getAbsolutePath());
                        //统一用虚拟文件系统避免文件分隔符不相同的情况
                        imports.add(file.getPath());
                    } else {
                        logger.debug("{} 不存在 ", importFile);
                    }

                }

            }
        }
        return imports;
    }

    public static String getFilePath(PsiElement element) {
        //getContainingFile().getVirtualFile().getPath() 可能会出现空指针
        return element.getContainingFile().getOriginalFile().getVirtualFile().getPath();
    }

    /**
     * 计算 filePath 对于standard 的相对路径
     *
     * @param standard
     * @param filePath
     * @return
     */
    public static String getRelativePath(String standard, String filePath) {

        Path a = Paths.get(standard).getParent();
        return a.relativize(Paths.get(filePath)).toString();
    }

    public static Module getModule(PsiElement element) {


        return ModuleUtil.findModuleForPsiElement(element);


    }

    public static List<IoMessage> findMessage(Project project, Module module) {

        return findMessage(project, module, null);
    }

    public static List<IoMessage> findMessage(Project project, Module module, String namespace) {
        List<IoMessage> results = new ArrayList<>();
        List<IoEntity> ioEntities = findEntities(project, module, namespace);
        for (IoEntity ioEntity : ioEntities) {
            IoMessage message = ioEntity.getMessage();
            if (message != null) {
                results.add(message);
            }
        }
        return results;
    }

    public static List<IoEvent> findEvent(Project project, Module module) {
        return findEvent(project, module, null);
    }

    public static List<IoEvent> findEvent(Project project, Module module, String namespace) {
        List<IoEvent> results = new ArrayList<>();
        List<IoEntity> ioEntities = findEntities(project, module, namespace);
        for (IoEntity ioEntity : ioEntities) {
            IoEvent event = ioEntity.getEvent();
            if (event != null) {
                results.add(event);
            }
        }
        return results;
    }

    public static List<IoEntity> findEntities(Project project) {

        return findEntities(project, null, null);
    }

    public static List<IoEntity> findEntities(Project project, Module module) {

        return findEntities(project, module, null);
    }

    public static List<IoEntity> findEntities(Project project, Module module, String namespace) {

        List<IoEntity> results = new ArrayList<>();
//        Collection<VirtualFile> virtualFiles =
//                FileTypeIndex.getFiles(IoFileType.INSTANCE, GlobalSearchScope.allScope(project));
        Collection<VirtualFile> virtualFiles = null;
        if (module == null) {
            virtualFiles = FileTypeIndex.getFiles(IoFileType.INSTANCE, GlobalSearchScope.projectScope(project));
        } else {

            //  FileTypeIndex.getFiles(IoFileType.INSTANCE, GlobalSearchScopeUtil.)
            virtualFiles = FileTypeIndex.getFiles(IoFileType.INSTANCE, GlobalSearchScope.moduleScope(module));
        }
        for (VirtualFile virtualFile : virtualFiles) {
            if (namespace == null || Objects.equals(IoUtil.getFileNamespace(virtualFile.getPath()), namespace)) {
                IoFile ioFile = (IoFile) PsiManager.getInstance(project).findFile(virtualFile);
                if (ioFile != null) {
                    IoEntity[] ioEntities = PsiTreeUtil.getChildrenOfType(ioFile, IoEntity.class);
                    if (ioEntities != null) {
                        for (IoEntity ioEntity : ioEntities) {
                            results.add(ioEntity);
                        }
                    }

                }
            }
        }

        return results;
    }

    public static List<IoNamedElement> findBeansOrEnums(List<IoEntity> ioEntities, String name) {
        List<IoNamedElement> results = new ArrayList<>();
        for (IoEntity ioEntity : ioEntities) {
            IoEnum ioEnum = ioEntity.getEnum();
            if (ioEnum != null && ioEnum.getEnumName() != null) {
                if (Objects.equals(ioEnum.getEnumName().getText(), name))
                    results.add(ioEnum.getEnumName());
            }
            IoBean bean = ioEntity.getBean();
            if (bean != null && bean.getBeanName() != null) {
                if (Objects.equals(bean.getBeanName().getText(), name))
                    results.add(bean.getBeanName());
            }
        }

        return results;
    }

    public static List<IoNamedElement> findBeansOrEnums(Project project, Module module, String namespace, String name) {

        List<IoEntity> ioEntities = findEntities(project, module, namespace);
        return findBeansOrEnums(ioEntities, name);

    }

    public static List<IoNamedElement> findBeansOrEnums(Project project, Module module, String name) {

        return findBeansOrEnums(project, module, null, name);
    }

}
