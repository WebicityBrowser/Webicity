package com.github.webicitybrowser.threadyweb.graphical.layout.flow.floatbox;

import java.util.Queue;

import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.box.Box;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.unit.RenderedUnit;
import com.github.webicitybrowser.threadyweb.graphical.layout.flow.floatbox.imp.FloatContextImp;

public interface FloatContext {

	FloatTracker getFloatTracker();

	Queue<FloatEntry> getEndFloats();

	void addEndFloat(Box floatBox, RenderedUnit floatUnit, Box orginatingBox);
	
	static record FloatEntry(Box floatBox, RenderedUnit floatUnit, Box orginatingBox) {}

	static FloatContext create(FloatTracker floatTracker) {
		return new FloatContextImp(floatTracker);
	}

}
