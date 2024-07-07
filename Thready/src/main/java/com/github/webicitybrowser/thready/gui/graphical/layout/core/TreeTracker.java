package com.github.webicitybrowser.thready.gui.graphical.layout.core;

import java.util.List;

import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.box.Box;

public interface TreeTracker {

	Box parentBox();
	
	List<Box> children();

}
