package com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.layout.flow.floatbox;

import java.util.List;

import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.box.Box;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.unit.RenderedUnit;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.layout.flow.floatbox.imp.FloatContextImp;

public interface FloatContext {

	FloatTracker getFloatTracker();

	List<FloatEntry> getEndFloats();

	void addEndFloat(RenderedUnit floatUnit, Box orginatingBox);
	
	static record FloatEntry(RenderedUnit floatUnit, Box orginatingBox) {}

	static FloatContext create(FloatTracker floatTracker) {
		return new FloatContextImp(floatTracker);
	}

}
