package com.github.webicitybrowser.webicitybrowser.gui.ui.menu;

import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.ComponentUI;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.UIDisplay;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.context.Context;

public record MenuButtonContext(UIDisplay<?, ?, ?> display, ComponentUI componentUI) implements Context {

}
