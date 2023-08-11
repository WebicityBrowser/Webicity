package com.github.webicitybrowser.webicitybrowser.gui.ui.menu;

import com.github.webicitybrowser.thready.dimensions.AbsoluteSize;
import com.github.webicitybrowser.thready.drawing.core.text.Font2D;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.unit.RenderedUnit;

public record MenuButtonUnit(AbsoluteSize preferredSize, MenuButtonBox box, Font2D font) implements RenderedUnit {

}
