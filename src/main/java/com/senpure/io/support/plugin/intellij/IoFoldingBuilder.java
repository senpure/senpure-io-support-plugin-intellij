package com.senpure.io.support.plugin.intellij;

import com.intellij.lang.ASTNode;
import com.intellij.lang.folding.FoldingBuilderEx;
import com.intellij.lang.folding.FoldingDescriptor;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.util.TextRange;
import com.intellij.psi.PsiElement;
import com.intellij.psi.tree.TokenSet;
import com.intellij.psi.util.PsiTreeUtil;
import com.senpure.io.support.plugin.intellij.psi.IoEntity;
import com.senpure.io.support.plugin.intellij.psi.IoHead;
import com.senpure.io.support.plugin.intellij.psi.IoTypes;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * IoFoldingBuilder
 * 折叠管理
 *
 * @author senpure
 * @time 2019-05-31 11:41:57
 */
public class IoFoldingBuilder extends FoldingBuilderEx {

    private static TokenSet entitys = TokenSet.create(IoTypes.MESSAGE, IoTypes.BEAN, IoTypes.EVENT, IoTypes.ENUM);
    private static TokenSet left = TokenSet.create(IoTypes.LEFT_BRACE);

    @NotNull
    @Override
    public FoldingDescriptor[] buildFoldRegions(@NotNull PsiElement root, @NotNull Document document, boolean quick) {
        List<FoldingDescriptor> descriptors = new ArrayList<>();
        IoHead ioHead = PsiTreeUtil.findChildOfType(root, IoHead.class);
        // descriptors.add(new FoldingDescriptor(ioHead.getNode(), new TextRange(ioHead.getTextRange().getStartOffset() + 1, ioHead.getTextRange().getEndOffset() - 1)));
        if (ioHead != null) {
            ASTNode node = ioHead.getNode();
            TextRange textRange = ioHead.getTextRange();
            if (textRange.getLength() > 10) {
                descriptors.add(new FoldingDescriptor(node, textRange));
            }
        }
        Collection<IoEntity> entities = PsiTreeUtil.findChildrenOfType(root, IoEntity.class);
        for (IoEntity entity : entities) {
            ASTNode astNode[] = entity.getNode().getChildren(entitys);
            boolean not = true;
            for (ASTNode node : astNode) {
                ASTNode[] nodes = node.getChildren(left);
                if (nodes.length > 0) {
                    int start = nodes[0].getStartOffset() + 1;
                    int end = entity.getTextRange().getEndOffset() - 1;
                    if (end > start) {
                        descriptors.add(new FoldingDescriptor(entity.getNode(), new TextRange(start, end)));

                    }
                    not = false;
                }
            }
            if (not) {
                descriptors.add(new FoldingDescriptor(entity.getNode(), entity.getTextRange()));
            }
        }
        return descriptors.toArray(new FoldingDescriptor[descriptors.size()]);
    }


    @Nullable
    @Override
    public String getPlaceholderText(@NotNull ASTNode node) {
        if (node.getElementType().equals(IoTypes.HEAD)) {
            return "{...}";
        }
        return "...";
    }


    @Override
    public boolean isCollapsedByDefault(@NotNull ASTNode node) {
        if (node.getElementType().equals(IoTypes.HEAD)) {
            return true;
        }
        return false;
    }
}
