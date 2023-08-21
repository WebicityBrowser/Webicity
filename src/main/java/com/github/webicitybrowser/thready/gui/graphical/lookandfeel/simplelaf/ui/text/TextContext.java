package com.github.webicitybrowser.thready.gui.graphical.lookandfeel.simplelaf.ui.text;

import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.ComponentUI;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.UIDisplay;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.context.Context;

public record TextContext(UIDisplay<?, ?, ?> display, ComponentUI componentUI) implements Context {

}
