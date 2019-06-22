package com.senpure.io.support.plugin.intellij.model;

import com.senpure.io.support.plugin.intellij.psi.IoBean;
import com.senpure.io.support.plugin.intellij.psi.IoEnum;

import java.util.ArrayList;
import java.util.List;

/**
 * IoBeanAndEnum
 *
 * @author senpure
 * @time 2019-06-17 17:24:08
 */
public class IoEntityWrapper {

    private List<IoBean> beans = new ArrayList<>(16);

    private List<IoEnum> enums = new ArrayList<>(16);

    public void addBean(IoBean bean) {
        beans.add(bean);
    }

    public void addEnum(IoEnum bean) {
        enums.add(bean);
    }

    public List<IoBean> getBeans() {
        return beans;
    }

    public void setBeans(List<IoBean> beans) {
        this.beans = beans;
    }

    public List<IoEnum> getEnums() {
        return enums;
    }

    public void setEnums(List<IoEnum> enums) {
        this.enums = enums;
    }
}
