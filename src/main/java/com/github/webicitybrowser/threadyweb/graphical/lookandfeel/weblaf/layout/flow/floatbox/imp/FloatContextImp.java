package com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.layout.flow.floatbox.imp;

import java.util.ArrayList;
import java.util.List;

import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.box.Box;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.unit.RenderedUnit;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.layout.flow.floatbox.FloatContext;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.layout.flow.floatbox.FloatTracker;

public class FloatContextImp implements FloatContext {
	
	private final FloatTracker floatTracker;
	private final List<FloatEntry> endFloats;

	public FloatContextImp(FloatTracker floatTracker) {
		this.floatTracker = floatTracker;
		this.endFloats = new ArrayList<>();
	}

	@Override
	public FloatTracker getFloatTracker() {
		return floatTracker;
	}

	@Override
	public List<FloatEntry> getEndFloats() {
		return endFloats;
	}

	@Override
	public void addEndFloat(RenderedUnit floatUnit, Box orginatingBox) {
		endFloats.add(new FloatEntry(floatUnit, orginatingBox));
	}

}
