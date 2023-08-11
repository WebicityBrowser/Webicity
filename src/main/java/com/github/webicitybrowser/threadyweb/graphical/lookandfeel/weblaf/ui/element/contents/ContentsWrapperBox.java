package com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.ui.element.contents;

import com.github.webicitybrowser.thready.gui.directive.core.pool.DirectivePool;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.pipeline.BoundBox;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.box.WrapperBox;
import com.github.webicitybrowser.thready.gui.tree.core.Component;

public record ContentsWrapperBox(Component owningComponent, DirectivePool styleDirectives, BoundBox<?, ?> wrappedBox) implements ContentsBox, WrapperBox {

    @Override
    public BoundBox<?, ?> innerBox() {
        return wrappedBox();
    }

    @Override
    public WrapperBox rewrap(BoundBox<?, ?> newInnerBox) {
        return new ContentsWrapperBox(owningComponent(), styleDirectives(), newInnerBox);
    }

}
