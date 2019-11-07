package com.senpure.io.support.plugin.intellij;

import com.intellij.lang.annotation.AnnotationHolder;
import com.intellij.lang.annotation.Annotator;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.util.TextRange;
import com.intellij.psi.PsiDocumentManager;
import com.intellij.psi.PsiElement;
import com.senpure.io.generator.util.ProtocolUtil;
import com.senpure.io.support.plugin.intellij.psi.*;
import com.senpure.io.support.plugin.intellij.util.IoUtil;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * IoAnnotator
 *
 * @author senpure
 * @time 2019-05-24 17:17:31
 */
public class IoAnnotator implements Annotator {
    @Override
    public void annotate(@NotNull PsiElement element, @NotNull AnnotationHolder holder) {

        if (element instanceof IoBean) {
            IoBean bean = (IoBean) element;
            if (bean.getFieldList().size() == 0) {
                holder.createErrorAnnotation(bean.getBeanName(), "没有定义字段");
            }
            checkField(bean.getFieldList(), holder);
        } else if (element instanceof IoMessage) {
            IoMessage bean = (IoMessage) element;
            checkField(bean.getFieldList(), holder);
        } else if (element instanceof IoEvent) {
            IoEvent bean = (IoEvent) element;
            checkField(bean.getFieldList(), holder);
        } else if (element instanceof IoEnum) {
            IoEnum bean = (IoEnum) element;
            if (bean.getEnumFieldList().size() < 2) {
                holder.createErrorAnnotation(bean.getEnumName(), "枚举至少需要两个字段");
            }
            checkEnum(bean.getEnumFieldList(), holder);

        } else if (element instanceof IoBeanName) {
            checkBeanName((IoBeanName) element, holder);
        } else if (element instanceof IoEnumName) {
            checkEnumName((IoEnumName) element, holder);
        } else if (element instanceof IoMessageId) {
            checkMessageId((IoMessageId) element, holder);
        } else if (element instanceof IoEventId) {
            checkEventId((IoEventId) element, holder);
        } else if (element instanceof IoEventName) {
            checkEventName((IoEventName) element, holder);
        } else if (element instanceof IoMessageName) {
            IoMessageName messageName = (IoMessageName) element;
            checkMessageName(IoUtil.getPreEffectiveSibling(element.getNode()).getText(), messageName, holder);
        }
    }


    private String getFilePath(PsiElement element) {
        return IoUtil.getFilePath(element);
    }

    private void checkMessageName(String type, IoMessageName messageName, @NotNull AnnotationHolder holder) {

        String filePath = getFilePath(messageName);
        List<IoMessage> messages = IoUtil.findMessage(messageName.getProject(),
                IoUtil.getModule(messageName),
                IoUtil.getFileNamespace(filePath)
        );
        //  String name = type + messageName.getText();
        for (IoMessage message : messages) {
            // String temp = message.getMessageType().getText() + message.getMessageName().getText();
            if (Objects.equals(message.getMessageName().getText(), messageName.getText())) {
                if (!Objects.equals(messageName, message.getMessageName())) {
                    if (Objects.equals(type, message.getMessageType().getText())) {
                        holder.createErrorAnnotation(messageName, "name相同(" +
                                message.getContainingFile().getName()
                                + getPosition(message.getMessageName()) + ")"
                        );     //holder.createErrorAnnotation(message.getMessageName(), "name相同" + messageName.getContainingFile().getName());

                    }
                }
            }
        }
    }

    private void checkEventName(IoEventName eventName, @NotNull AnnotationHolder holder) {

        String filePath = getFilePath(eventName);
        List<IoEvent> events = IoUtil.findEvent(eventName.getProject(),
                IoUtil.getModule(eventName),
                IoUtil.getFileNamespace(filePath)
        );
        for (IoEvent event : events) {
            if (Objects.equals(eventName.getText(), event.getEventName().getText())) {
                if (!Objects.equals(eventName, event.getEventName())) {

                    holder.createErrorAnnotation(eventName, "name相同(" +
                            event.getContainingFile().getName()
                            + getPosition(event.getEventName()) + ")"
                    );
                    // holder.createErrorAnnotation(event.getEventName(), "name相同" + eventName.getContainingFile().getName());
                }
            }
        }
    }

    private void checkEventId(IoEventId eventId, @NotNull AnnotationHolder holder) {
        // String filePath = getFilePath(eventId);
        List<IoEvent> events = IoUtil.findEvent(eventId.getProject(),
                IoUtil.getModule(eventId));
        for (IoEvent event : events) {
            if (Objects.equals(event.getEventId().getText(), eventId.getText())) {
                if (!Objects.equals(eventId, event.getEventId())) {

                    holder.createErrorAnnotation(eventId, "eventId相同(" + event.getEventName().getName() + "<->" +
                            event.getContainingFile().getName()
                            + getPosition(event.getEventId()) + ")"
                    );

                    //holder.createErrorAnnotation(event.getEventId(), "eventId相同" + eventId.getContainingFile().getName());
                }
            }
        }
    }

    private void checkMessageId(IoMessageId messageId, @NotNull AnnotationHolder holder) {

        // String filePath = getFilePath(messageId);
        List<IoMessage> messages = IoUtil.findMessage(messageId.getProject(),
                IoUtil.getModule(messageId));

        for (IoMessage message : messages) {
            if (Objects.equals(message.getMessageId().getText(), messageId.getText())) {
                if (!Objects.equals(messageId, message.getMessageId())) {
                    holder.createErrorAnnotation(messageId, "messageId相同(" + message.getMessageName().getName() + "<->" +
                            message.getContainingFile().getName()
                            + getPosition(message.getMessageId()) + ")"
                    );
                    //holder.createErrorAnnotation(message.getMessageId(), "messageId相同" + messageId.getContainingFile().getName());
                }
            }
        }

    }

    private void checkBeanName(IoBeanName beanName, @NotNull AnnotationHolder holder) {
        checkName(beanName, holder);
    }

    private Position getPosition(PsiElement element) {

        Document document = PsiDocumentManager.getInstance(element.getProject()).
                getCachedDocument(element.getContainingFile().getOriginalFile());
        TextRange textRange = element.getTextRange();
        int line = document.getLineNumber(textRange.getStartOffset());
        int offset = document.getLineStartOffset(line);
        offset = textRange.getStartOffset() - offset;
        Position position = new Position();
        position.line = line + 1;
        position.offset = offset + 1;
        return position;
    }

    private void checkName(IoNamedElement name, @NotNull AnnotationHolder holder) {
        String filePath = getFilePath(name);
        List<IoNamedElement> namedElements = IoUtil.findBeansOrEnums(name.getProject(),
                IoUtil.getModule(name),
                IoUtil.getFileNamespace(filePath),
                name.getName());

//        if (name.getName().startsWith("SameName")) {
//            System.out.println(name.getName());
//            for (IoNamedElement element : namedElements) {
//                System.out.println(element.getContainingFile().getName() + element.getName() + IoUtil.getFileNamespace(IoUtil.getFilePath(element)));
//            }
//
//        }
        for (IoNamedElement element : namedElements) {
            if (Objects.equals(name.getName(), element.getName())) {
                if (!element.equals(name)) {

                    Position position = getPosition(element);

                    holder.createErrorAnnotation(name, "name相同("
                            + element.getContainingFile().getName()
                            + position + ")"
                    );
                    // holder.createErrorAnnotation(element, "name相同" + name.getContainingFile().getName());

                }
            }
        }
    }

    private void checkEnumName(IoEnumName enumName, @NotNull AnnotationHolder holder) {
        checkName(enumName, holder);
    }

    private void checkEnum(List<IoEnumField> fields, @NotNull AnnotationHolder holder) {

        for (int i = 0; i < fields.size() - 1; i++) {
            IoEnumField a = fields.get(i);
            for (int j = i + 1; j < fields.size(); j++) {
                IoEnumField b = fields.get(j);
                if (Objects.equals(a.getFieldName().getText(), b.getFieldName().getText())) {
                    holder.createErrorAnnotation(a.getFieldName(), "fieldName相同(" +
                            a.getContainingFile().getName()
                            + getPosition(b.getFieldName()) + ")"
                    );
                    // holder.createErrorAnnotation(b.getFieldName(), "相同字段名");
                }
            }
        }


        int index = 1;
        int[] indexes = new int[fields.size()];

        for (int i = 0; i < indexes.length; i++) {
            IoFieldIndex fieldIndex = fields.get(i).getFieldIndex();
            if (fieldIndex == null) {
                indexes[i] = index++;
            } else {
                indexes[i] = Integer.parseInt(fieldIndex.getText());
            }
        }
        if (indexes.length > 1) {
            if (indexes[0] != 1) {
                IoEnumField a = fields.get(0);
                if (a.getFieldIndex() == null) {
                    holder.createErrorAnnotation(a.getFieldName(), "枚举第一个字段index必须为一,作为默认值");
                } else {
                    holder.createErrorAnnotation(a.getFieldIndex(), "枚举第一个字段index必须为一,作为默认值");
                }

            }
        }
        for (int i = 0; i < indexes.length - 1; i++) {
            for (int j = i + 1; j < indexes.length; j++) {
                if (indexes[i] == indexes[j]) {
                    IoEnumField a = fields.get(i);
                    IoEnumField b = fields.get(j);

                    Position bPosition;
                    Position aPosition;
                    if (b.getFieldIndex() == null) {
                        bPosition = getPosition(b.getFieldName());
                    } else {
                        bPosition = getPosition(b.getFieldIndex());
                    }
                    if (a.getFieldIndex() == null) {
                        aPosition = getPosition(a.getFieldName());
                    } else {
                        aPosition = getPosition(a.getFieldIndex());
                    }

                    if (a.getFieldIndex() == null) {

                        holder.createErrorAnnotation(a.getFieldName(), "相同index " + indexes[i] +
                                bPosition
                        );
                    } else {
                        holder.createErrorAnnotation(a.getFieldIndex(), "相同index " + indexes[i]
                                + bPosition
                        );
                    }
                    if (b.getFieldIndex() == null) {
                        holder.createErrorAnnotation(b.getFieldName(), "相同index " + indexes[i]
                                + aPosition
                        )
                        ;
                    } else {
                        holder.createErrorAnnotation(b.getFieldIndex(), "相同index " + indexes[i]
                                + aPosition
                        );
                    }


                }
            }
        }
    }

    private void checkField(List<IoField> fields, @NotNull AnnotationHolder holder) {

        for (int i = 0; i < fields.size() - 1; i++) {
            IoField a = fields.get(i);
            for (int j = i + 1; j < fields.size(); j++) {
                IoField b = fields.get(j);
                if (Objects.equals(a.getFieldName().getText(), b.getFieldName().getText())) {
                    holder.createErrorAnnotation(a.getFieldName(), "fieldName相同(" +
                            a.getContainingFile().getName()
                            + getPosition(b.getFieldName()) + ")"
                    );
                    holder.createErrorAnnotation(b.getFieldName(), "fieldName相同(" +
                            b.getContainingFile().getName()
                            + getPosition(a.getFieldName()) + ")"
                    );
                }
            }
        }

        String filePath = null;
        List<IoEntity> ioEntities = null;
        for (IoField field : fields) {
            String type = field.getFieldType().getText();
            if (!ProtocolUtil.isBaseField(type)) {
                if (filePath == null) {
                    filePath = getFilePath(field);
                    // Module module = IoUtil.getModule(field);
                    ioEntities = IoUtil.findEntities(field.getProject(),
                            IoUtil.getModule(field));
                }
                List<IoNamedElement> namedElements = IoUtil.findBeansOrEnums(ioEntities, type);
                if (namedElements.size() == 0) {
                    holder.createErrorAnnotation(field.getFieldType(), "没有找到定义");
                }
                if (namedElements.size() > 0) {

                    List<String> imports = IoUtil.getImports(field);

                    List<String> finds = new ArrayList<>();
                    List<String> has = new ArrayList<>();
                    for (IoNamedElement namedElement : namedElements) {

                        String refFilePath = IoUtil.getFilePath(namedElement);
                        has.add(refFilePath);
                        for (String anImport : imports) {
                            if (Objects.equals(anImport, refFilePath)) {
                                finds.add(anImport);
                                break;
                            }
                        }
                    }
                    if (finds.size() == 0) {
                        List<String> temps = new ArrayList<>();
                        for (String ha : has) {
                            temps.add(IoUtil.getRelativePath(filePath, ha));
                        }
                        holder.createErrorAnnotation(field.getFieldType(),
                                "没有导入.io文件" + temps.toString());
                    } else if (finds.size() > 1) {
                        List<String> temps = new ArrayList<>();
                        for (String ha : finds) {
                            temps.add(IoUtil.getRelativePath(filePath, ha));
                        }
                        holder.createErrorAnnotation(field.getFieldType(),
                                "无法准确知道引用那个.io 文件中的定义" + temps.toString());
                    }


                }
            }
        }
        int index = 1;
        int[] indexes = new int[fields.size()];

        for (int i = 0; i < indexes.length; i++) {
            IoFieldIndex fieldIndex = fields.get(i).getFieldIndex();
            if (fieldIndex == null) {
                indexes[i] = index++;
            } else {
                indexes[i] = Integer.parseInt(fieldIndex.getText());
            }
        }

        for (int i = 0; i < indexes.length - 1; i++) {
            for (int j = i + 1; j < indexes.length; j++) {
                if (indexes[i] == indexes[j]) {
                    IoField a = fields.get(i);
                    IoField b = fields.get(j);
                    Position bPosition;
                    Position aPosition;
                    if (b.getFieldIndex() == null) {
                        bPosition = getPosition(b.getFieldName());
                    } else {
                        bPosition = getPosition(b.getFieldIndex());
                    }
                    if (a.getFieldIndex() == null) {
                        aPosition = getPosition(a.getFieldName());
                    } else {
                        aPosition = getPosition(a.getFieldIndex());
                    }

                    if (a.getFieldIndex() == null) {
                        holder.createErrorAnnotation(a.getFieldName(), "相同index " + indexes[i]
                                + bPosition);
                    } else {
                        holder.createErrorAnnotation(a.getFieldIndex(), "相同index " + indexes[i]
                                + bPosition
                        );
                    }
                    if (b.getFieldIndex() == null) {
                        holder.createErrorAnnotation(b.getFieldName(), "相同index " + indexes[i]
                                +aPosition
                        );
                    } else {
                        holder.createErrorAnnotation(b.getFieldIndex(), "相同index " + indexes[i]
                                +aPosition
                        );
                    }


                }
            }
        }


    }

    private class Position {
        int line;
        int offset;

        @Override
        public String toString() {
            return " line " + line + ":" + offset;
        }
    }
}
