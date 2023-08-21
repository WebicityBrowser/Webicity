package com.github.webicitybrowser.thready.gui.graphical.lookandfeel.simplelaf.display.wrapper;

import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.ComponentUI;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.UIDisplay;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.context.Context;

public record SimpleWrapperContext<T extends Context>(UIDisplay<?, ?, ?> display, ComponentUI componentUI, T childContext) implements Context {

}
