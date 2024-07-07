package com.github.webicitybrowser.thready.gui.graphical.base.layout;

import java.util.List;

import com.github.webicitybrowser.thready.gui.graphical.layout.core.TreeTracker;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.box.Box;

public record StaticTreeTracker(Box parentBox, List<Box> children) implements TreeTracker {
	
}
