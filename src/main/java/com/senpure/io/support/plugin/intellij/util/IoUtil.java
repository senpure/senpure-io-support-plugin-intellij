package com.senpure.io.support.plugin.intellij.util;

import com.intellij.lang.ASTNode;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiManager;
import com.intellij.psi.TokenType;
import com.intellij.psi.search.FileTypeIndex;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.psi.tree.IElementType;
import com.intellij.psi.util.PsiTreeUtil;
import com.senpure.io.support.plugin.intellij.IoFileType;
import com.senpure.io.support.plugin.intellij.IoIcons;
import com.senpure.io.support.plugin.intellij.model.IoEntityWrapper;
import com.senpure.io.support.plugin.intellij.psi.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
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
     * key命名空间+messageType+name
     */
    private static Map<String, Integer> messageIdMap = new HashMap<>();
    private static Map<String, Integer> namespaceMaxMessageIdMap = new HashMap<>();

    private static Map<String, Integer> eventIdMap = new HashMap<>();
    private static Map<String, Integer> namespaceMaxEventIdMap = new HashMap<>();
    private static int maxMessageId = 0;
    private static int maxEventId = 0;

    private static String getKey(String namespace, String messageType, String name) {
        return namespace + messageType + name;
    }

    private static String getKey(String namespace, String name) {
        return namespace + name;
    }

    public static void setMessageId(String namespace, String messageType, String name, Integer messageId) {

        messageIdMap.put(getKey(namespace, messageType, name), messageId);
        Integer namespaceMaxId = namespaceMaxMessageIdMap.get(namespace);
        if (namespaceMaxId == null || messageId > namespaceMaxId) {
            namespaceMaxMessageIdMap.put(namespace, messageId);
        }
        if (messageId > maxMessageId) {
            maxMessageId = messageId;
        }
    }

    public static void setEventId(String namespace, String name, Integer eventId) {

        eventIdMap.put(getKey(namespace, name), eventId);
        Integer namespaceMaxId = namespaceMaxEventIdMap.get(namespace);
        if (namespaceMaxId == null || eventId > namespaceMaxId) {
            namespaceMaxEventIdMap.put(namespace, eventId);
        }
        if (eventId > maxEventId) {
            maxEventId = eventId;
        }
    }

    private static int initMessageId(boolean cs) {
        int messageId=initId(maxMessageId);

        return cs ? messageId + 1 : messageId + 2;
    }

    private static int initId(int maxId) {
        int id;
        if (maxId == 0) {
            id = 10000;
        } else {
            String str = maxId + "";
            int temp = (int) Math.pow(10, str.length() - 1);
            id = maxId  / temp * temp;
        }
        if (id< 10) {
            id= 10000;
        }
        return id;
    }

    private static int initEventId() {
        return initId(maxEventId);
    }
    public static Integer getAutoEventId(String namespace, String name) {
        Integer namespaceMaxId = namespaceMaxEventIdMap.get(namespace);
        if (namespaceMaxId == null) {
            return initEventId();
        }
        return namespaceMaxId + 1;
    }


    public static Integer getAutoMessageId(String namespace, String messageType, String name) {
        Integer otherId;
        if (messageType.equalsIgnoreCase("CS")) {
            otherId = messageIdMap.get(getKey(namespace, "SC", name));
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

    public static void setFileNamespace(String filePath, String namespace) {
        fileNamespaceMap.put(filePath, namespace);
    }

    public static String getFileNamespace(String filePath) {
        return fileNamespaceMap.get(filePath);
    }

    public static IElementType preEffectiveElementType(ASTNode node) {

        ASTNode prev = node.getTreePrev();

        if (prev == null) {
            return null;
        }
        if (prev.getElementType().equals(TokenType.WHITE_SPACE)) {
            return preEffectiveElementType(prev);
        }
        return prev.getElementType();
    }

    public static IElementType nexEffectiveElementType(ASTNode node) {

        ASTNode prev = node.getTreeNext();

        if (prev == null) {
            return null;
        }
        if (prev.getElementType().equals(TokenType.WHITE_SPACE)) {
            return nexEffectiveElementType(prev);
        }
        return prev.getElementType();
    }

    public static ASTNode nexEffectiveNode(ASTNode node) {

        ASTNode prev = node.getTreeNext();

        if (prev == null) {
            return null;
        }
        if (prev.getElementType().equals(TokenType.WHITE_SPACE)) {
            return nexEffectiveNode(prev);
        }
        return prev;
    }

    public static ASTNode childEffectiveNode(ASTNode node) {

        ASTNode prev = node.getFirstChildNode();

        if (prev == null) {
            return null;
        }
        if (prev.getElementType().equals(TokenType.WHITE_SPACE)) {
            return nexEffectiveNode(prev);
        }
        return prev;
    }

    public static ASTNode preEffectiveNode(ASTNode node) {

        ASTNode prev = node.getTreePrev();

        if (prev == null) {
            return null;
        }
        if (prev.getElementType().equals(TokenType.WHITE_SPACE)) {
            return preEffectiveNode(prev);
        }
        return prev;
    }

    private static ASTNode preEffectiveNode(ASTNode node, int times, int num) {
        ASTNode prev = node.getTreePrev();

        if (prev == null) {
            return null;
        }
        if (prev.getElementType().equals(TokenType.WHITE_SPACE)) {
            return preEffectiveNode(prev, times, num);
        }
        num++;
        if (num >= times) {
            return prev;
        }
        return preEffectiveNode(prev, times, num);
    }

    public static ASTNode preEffectiveNode(ASTNode node, int times) {


        return preEffectiveNode(node, times, 0);
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


    public static IoEntityWrapper findEntityWrapper(Project project) {

        IoEntityWrapper wrapper = new IoEntityWrapper();
        Collection<VirtualFile> virtualFiles =
                FileTypeIndex.getFiles(IoFileType.INSTANCE, GlobalSearchScope.allScope(project));
        for (VirtualFile virtualFile : virtualFiles) {
            IoFile ioFile = (IoFile) PsiManager.getInstance(project).findFile(virtualFile);
            if (ioFile != null) {
                Collection co = PsiTreeUtil.findChildrenOfType(ioFile, IoEntity.class);
                // logger.debug("co {}", co);
                //可以找到
                Collection co2 = PsiTreeUtil.findChildrenOfType(ioFile, IoBean.class);
                //logger.debug("co2 {}", co2);
            }
        }
        return wrapper;
    }

    public static List<IoEntity> findEntities(Project project) {
        List<IoEntity> results = new ArrayList<>();
        Collection<VirtualFile> virtualFiles =
                FileTypeIndex.getFiles(IoFileType.INSTANCE, GlobalSearchScope.allScope(project));
        for (VirtualFile virtualFile : virtualFiles) {
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
        return results;
    }

    public static List<IoNamedElement> findEntities(Project project, String name) {
        List<IoNamedElement> results = new ArrayList<>();

        Collection<VirtualFile> virtualFiles =
                FileTypeIndex.getFiles(IoFileType.INSTANCE, GlobalSearchScope.allScope(project));
        for (VirtualFile virtualFile : virtualFiles) {
            IoFile ioFile = (IoFile) PsiManager.getInstance(project).findFile(virtualFile);
            if (ioFile != null) {
                IoEntity[] ioEntities = PsiTreeUtil.getChildrenOfType(ioFile, IoEntity.class);
                if (ioEntities != null) {
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
                        IoMessage message = ioEntity.getMessage();
                        if (message != null && message.getMessageName() != null) {
                            if (Objects.equals(message.getMessageName().getText(), name))
                                results.add(message.getMessageName());
                        }
                        IoEvent event = ioEntity.getEvent();
                        if (event != null && event.getEventName() != null) {
                            if (Objects.equals(event.getEventName().getText(), name))
                                results.add(event.getEventName());
                        }

                    }
                }
            }

        }
        return results;
    }

}
