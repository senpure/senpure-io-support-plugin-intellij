package com.senpure.io.support.plugin.intellij;

import com.intellij.navigation.ChooseByNameContributor;
import com.intellij.navigation.NavigationItem;
import com.intellij.openapi.project.Project;
import com.senpure.io.support.plugin.intellij.psi.*;
import com.senpure.io.support.plugin.intellij.util.IoUtil;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * IoChooseByNameContributor
 * <p>
 * idea 搜索
 *
 * @author senpure
 * @time 2019-06-14 09:46:01
 */
public class IoChooseByNameContributor implements ChooseByNameContributor {
    @NotNull
    @Override
    public String[] getNames(Project project, boolean includeNonProjectItems) {
        List<String> names = new ArrayList<String>(16);
        List<IoEntity> entities = IoUtil.findEntities(project);

        for (IoEntity entity : entities) {
            IoBean bean = entity.getBean();
            if (bean != null) {
                IoBeanName beanName = bean.getBeanName();
                if (beanName != null) {
                    names.add(beanName.getText());

                }
            }
            IoEnum ioEnum = entity.getEnum();
            if (ioEnum != null) {
                IoEnumName enumName = ioEnum.getEnumName();
                if (enumName != null) {
                    names.add(ioEnum.getText());
                }
            }

            IoMessage message = entity.getMessage();
            if (message != null) {
                IoMessageName messageName = message.getMessageName();
                if (messageName != null) {
                    names.add(messageName.getText());
                }
            }

            IoEvent event = entity.getEvent();
            if (event != null) {
                IoEventName eventName = event.getEventName();
                if (eventName != null) {
                    names.add(eventName.getText());
                }
            }
        }


        return names.toArray(new String[names.size()]);
    }

    @NotNull
    @Override
    public NavigationItem[] getItemsByName(String name, String pattern, Project project, boolean includeNonProjectItems) {

        List<IoNamedElement> ioNamedElements = new ArrayList<>();

        List<IoEntity> entities = IoUtil.findEntities(project);

        for (IoEntity entity : entities) {
            IoBean bean = entity.getBean();
            if (bean != null) {
                IoBeanName beanName = bean.getBeanName();
                if (beanName != null) {
                    ioNamedElements.add(beanName);

                }
            }
            IoEnum ioEnum = entity.getEnum();
            if (ioEnum != null) {
                IoEnumName enumName = ioEnum.getEnumName();
                if (enumName != null) {
                    ioNamedElements.add(enumName);
                }
            }

            IoMessage message = entity.getMessage();
            if (message != null) {
                IoMessageName messageName = message.getMessageName();
                if (messageName != null) {
                    ioNamedElements.add(messageName);
                }
            }

            IoEvent event = entity.getEvent();
            if (event != null) {
                IoEventName eventName = event.getEventName();
                if (eventName != null) {
                    ioNamedElements.add(eventName);
                }
            }
        }


        return ioNamedElements.toArray(new NavigationItem[ioNamedElements.size()]);
    }
}
