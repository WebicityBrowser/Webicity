package com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.ui.image;

import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.ComponentUI;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.UIDisplay;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.context.Context;

public record ImageContext(UIDisplay<?, ?, ?> display, ComponentUI componentUI) implements Context {

}
