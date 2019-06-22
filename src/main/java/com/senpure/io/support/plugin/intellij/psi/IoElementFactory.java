package com.senpure.io.support.plugin.intellij.psi;

import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiFileFactory;
import com.senpure.io.support.plugin.intellij.IoFileType;

/**
 * IoElementFactory
 *
 * @author senpure
 * @time 2019-06-14 11:31:08
 */
public class IoElementFactory {


    public static String DUMMY_FILE_NAME = "dummy." + IoFileType.INSTANCE.getDefaultExtension();

    public static final String SPACE = " ";

    public static void create(Project project) {
        PsiFileFactory.getInstance(project);
        IoEntity ioEntity;

    }


    public static IoEnum createEnum(Project project, String enumName) {

        return createEnum(project, enumName, "X");
    }

    public static IoEnum createEnum(Project project, String enumName, String fieldName) {
        StringBuilder sb = new StringBuilder();
        sb.append("enum").append(SPACE).append(enumName).append(SPACE).append("{");
        sb.append(fieldName).append(";");
        sb.append(fieldName).append("1").append(";");
        sb.append("}");
        IoFile file = (IoFile) PsiFileFactory.getInstance(project).createFileFromText(DUMMY_FILE_NAME, IoFileType.INSTANCE, sb.toString());
        IoEntity entity = file.findChildByClass(IoEntity.class);
        return entity.getEnum();

    }

    public static IoMessage createMessage(Project project, String messageName) {
        return createMessage(project, "CS", messageName, 10086, "int", "age");
    }

    public static IoMessage createMessage(Project project, String messageType, String messageName, int messageId, String fieldType, String fieldName) {
        StringBuilder sb = new StringBuilder();
        sb.append("message").append(SPACE)
                .append(messageType).append(SPACE)
                .append(messageName).append(SPACE)
                .append(messageId).append(SPACE)
                .append("{");

        sb.append(fieldType).append(SPACE).append(fieldName).append(";");
        sb.append(fieldType).append(SPACE).append(fieldName).append("1").append(";");
        sb.append("}");
        IoFile file = (IoFile) PsiFileFactory.getInstance(project).createFileFromText(DUMMY_FILE_NAME, IoFileType.INSTANCE, sb.toString());
        IoEntity entity = file.findChildByClass(IoEntity.class);
        return entity.getMessage();
    }

    public static IoEvent createEvent(Project project, String eventName) {
        return createEvent(project, eventName, 10086, "int", "age");
    }

    public static IoEvent createEvent(Project project, String eventName, int eventId, String fieldType, String fieldName) {
        StringBuilder sb = new StringBuilder();
        sb.append("event").append(SPACE)
                .append(eventName).append(SPACE)
                .append(eventId).append(SPACE)
                .append("{");

        sb.append(fieldType).append(SPACE).append(fieldName).append(";");
        sb.append("}");
        IoFile file = (IoFile) PsiFileFactory.getInstance(project).createFileFromText(DUMMY_FILE_NAME, IoFileType.INSTANCE, sb.toString());
        IoEntity entity = file.findChildByClass(IoEntity.class);
        return entity.getEvent();
    }

    public static IoBean createBean(Project project, String beanName) {

        return createBean(project, beanName, "int", "name");

    }

    public static IoBean createBean(Project project, String beanName, String fieldType, String fieldName) {
        StringBuilder sb = new StringBuilder();
        sb.append("bean").append(SPACE).append(beanName).append(SPACE).append("{");
        sb.append(SPACE).append(fieldType).append(SPACE).append(fieldName).append(";");
        sb.append("}");
        IoFile file = (IoFile) PsiFileFactory.getInstance(project).createFileFromText(DUMMY_FILE_NAME, IoFileType.INSTANCE, sb.toString());
        IoEntity entity = file.findChildByClass(IoEntity.class);
        return entity.getBean();
    }

    public static IoField createField(Project project, String fieldType) {
        IoBean bean = createBean(project, "Dummy", fieldType, "name");
        return bean.getFieldList().get(0);

    }

    public static void main(String[] args) {

    }
}
