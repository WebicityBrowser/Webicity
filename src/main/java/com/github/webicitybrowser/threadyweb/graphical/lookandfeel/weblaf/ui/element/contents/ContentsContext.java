package com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.ui.element.contents;

import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.ComponentUI;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.pipeline.PipelinedContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.context.Context;

public record ContentsContext(ComponentUI componentUI, PipelinedContext<?, ?, ?> innerContext) implements Context {

}
