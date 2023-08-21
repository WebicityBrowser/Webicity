package com.github.webicitybrowser.webicitybrowser.gui.ui.button;

import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.ComponentUI;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.UIDisplay;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.context.Context;

public record CircularButtonContext(UIDisplay<?, ?, ?> display, ComponentUI componentUI) implements Context {

}
