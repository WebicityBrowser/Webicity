package com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.unit;

public interface UnitGenerator {

	PartialUnitPreview previewNextUnit();

	Unit getMergedUnits();

	boolean completed();
	
}
