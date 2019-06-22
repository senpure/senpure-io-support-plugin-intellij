package com.senpure.io.support.plugin.intellij;

import com.intellij.lang.Commenter;
import org.jetbrains.annotations.Nullable;

/**
 * IoCommenter
 *
 * @author senpure
 * @time 2019-06-21 14:27:59
 */
public class IoCommenter implements Commenter {
    @Nullable
    @Override
    public String getLineCommentPrefix() {
        return "#";
    }


    @Nullable
    @Override
    public String getBlockCommentPrefix() {
        return null;
    }

    @Nullable
    @Override
    public String getBlockCommentSuffix() {
        return null;
    }

    @Nullable
    @Override
    public String getCommentedBlockCommentPrefix() {
        return null;
    }

    @Nullable
    @Override
    public String getCommentedBlockCommentSuffix() {
        return null;
    }


}
