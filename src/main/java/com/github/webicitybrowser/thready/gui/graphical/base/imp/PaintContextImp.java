package com.github.webicitybrowser.thready.gui.graphical.base.imp;

import com.github.webicitybrowser.thready.gui.graphical.animation.InvalidationScheduler;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.paint.GlobalPaintContext;

public class PaintContextImp implements GlobalPaintContext {

	private final InvalidationScheduler scheduler;

	public PaintContextImp(InvalidationScheduler scheduler) {
		this.scheduler = scheduler;
	}
	
	@Override
	public InvalidationScheduler getInvalidationScheduler() {
		return this.scheduler;
	}

}
