package com.senpure.io.support.plugin.intellij;

import com.intellij.navigation.ChooseByNameContributor;
import com.intellij.navigation.NavigationItem;
import com.intellij.openapi.project.Project;
import com.senpure.io.support.plugin.intellij.psi.IoBeanName;
import com.senpure.io.support.plugin.intellij.util.IoUtil;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * IoChooseByNameContributor
 *
 * @author senpure
 * @time 2019-06-14 09:46:01
 */
public class IoChooseByNameContributor implements ChooseByNameContributor {
    @NotNull
    @Override
    public String[] getNames(Project project, boolean includeNonProjectItems) {

        List<IoBeanName> beanNames = IoUtil.findNames(project);
        List<String> names = new ArrayList<String>(16);
        for (IoBeanName beanName : beanNames) {
            names.add(beanName.getText());
        }
        return names.toArray(new String[names.size()]);
    }

    @NotNull
    @Override
    public NavigationItem[] getItemsByName(String name, String pattern, Project project, boolean includeNonProjectItems) {
        List<IoBeanName> beanNames = IoUtil.findNames(project);
        return beanNames.toArray(new NavigationItem[beanNames.size()]);
    }
}
