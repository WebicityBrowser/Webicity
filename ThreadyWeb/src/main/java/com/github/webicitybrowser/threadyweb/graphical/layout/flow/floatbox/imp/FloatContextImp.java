package com.github.webicitybrowser.threadyweb.graphical.layout.flow.floatbox.imp;

import java.util.ArrayDeque;
import java.util.Queue;

import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.box.Box;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.unit.RenderedUnit;
import com.github.webicitybrowser.threadyweb.graphical.layout.flow.floatbox.FloatContext;
import com.github.webicitybrowser.threadyweb.graphical.layout.flow.floatbox.FloatTracker;

public class FloatContextImp implements FloatContext {
	
	private final FloatTracker floatTracker;
	private final Queue<FloatEntry> endFloats;

	public FloatContextImp(FloatTracker floatTracker) {
		this.floatTracker = floatTracker;
		this.endFloats = new ArrayDeque<>();
	}

	@Override
	public FloatTracker getFloatTracker() {
		return floatTracker;
	}

	@Override
	public Queue<FloatEntry> getEndFloats() {
		return endFloats;
	}

	@Override
	public void addEndFloat(Box floatBox, RenderedUnit floatUnit, Box orginatingBox) {
		endFloats.add(new FloatEntry(floatBox, floatUnit, orginatingBox));
	}

}
