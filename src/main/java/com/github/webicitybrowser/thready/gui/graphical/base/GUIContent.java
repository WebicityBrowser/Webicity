package com.github.webicitybrowser.thready.gui.graphical.base;

import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.LookAndFeel;
import com.github.webicitybrowser.thready.gui.tree.core.Component;
import com.github.webicitybrowser.thready.windowing.core.ScreenContent;

public interface GUIContent extends ScreenContent {

	void setRoot(Component component, LookAndFeel lookAndFeel);
	
}
