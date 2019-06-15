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

    public static void create(Project project) {
        PsiFileFactory.getInstance(project);
        IoEntity ioEntity;

    }


    public static IoBean createBean(Project project, String beanName) {

        return createBean(project, beanName, "int", "name");

    }

    public static IoBean createBean(Project project, String beanName, String fieldType, String fieldName) {
        StringBuilder sb = new StringBuilder();
        sb.append("bean ").append(beanName).append(" {");
        sb.append(" ").append(fieldType).append(" ").append(fieldName).append(";");
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
