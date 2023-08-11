package com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.ui.element.contents;

import java.util.List;

import com.github.webicitybrowser.thready.gui.directive.core.pool.DirectivePool;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.pipeline.BoundBox;
import com.github.webicitybrowser.thready.gui.tree.core.Component;

public record ContentsProxyBox(Component owningComponent, DirectivePool styleDirectives, List<BoundBox<?, ?>> childBoxes) implements ContentsBox {

    @Override
    public List<BoundBox<?, ?>> getAdjustedBoxTree(BoundBox<?, ?> self) {
        return childBoxes;
	};
    
}
