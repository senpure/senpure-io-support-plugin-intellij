package com.senpure.io.support.plugin.intellij.usages;

import com.intellij.psi.PsiElement;
import com.intellij.usages.UsageTarget;
import com.intellij.usages.impl.rules.UsageType;
import com.intellij.usages.impl.rules.UsageTypeProviderEx;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * IoUsageTypeProviderEx
 *
 * @author senpure
 * @time 2019-06-15 13:45:53
 */
public class IoUsageTypeProviderEx implements UsageTypeProviderEx {
    @Nullable
    @Override
    public UsageType getUsageType(PsiElement element, @NotNull UsageTarget[] targets) {
        return null;
    }

    @Nullable
    @Override
    public UsageType getUsageType(PsiElement element) {
        return null;
    }
}
