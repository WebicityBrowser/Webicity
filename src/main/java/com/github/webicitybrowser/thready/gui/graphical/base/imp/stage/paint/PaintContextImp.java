package com.github.webicitybrowser.thready.gui.graphical.base.imp.stage.paint;

import com.github.webicitybrowser.thready.gui.graphical.animation.InvalidationScheduler;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.paint.GlobalPaintContext;

public record PaintContextImp(InvalidationScheduler invalidationScheduler) implements GlobalPaintContext {

}
