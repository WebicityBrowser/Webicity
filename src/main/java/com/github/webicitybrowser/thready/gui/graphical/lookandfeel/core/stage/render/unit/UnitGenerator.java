package com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.unit;

public interface UnitGenerator {

	PartialUnitPreview previewNextUnit(ContextSwitch[] contextSwitches);

	Unit getMergedUnits();

	boolean completed();
	
}
