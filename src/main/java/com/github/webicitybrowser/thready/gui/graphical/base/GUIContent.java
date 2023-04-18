package com.github.webicitybrowser.thready.gui.graphical.base;

import com.github.webicitybrowser.thready.gui.directive.core.StyleGeneratorRoot;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.LookAndFeel;
import com.github.webicitybrowser.thready.gui.tree.core.Component;
import com.github.webicitybrowser.thready.windowing.core.ScreenContent;

/**
 * Allows binding a GUI tree to a screen.
 */
public interface GUIContent extends ScreenContent {

	/**
	 * Set the tree to be shown.
	 * @param component The root of the tree to be shown.
	 * @param lookAndFeel The appearence of how the tree is shown.
	 * @param styleGeneratorRoot The styling system to automatically
	 *  determine component styles.
	 */
	void setRoot(Component component, LookAndFeel lookAndFeel, StyleGeneratorRoot styleGeneratorRoot);
	
}
