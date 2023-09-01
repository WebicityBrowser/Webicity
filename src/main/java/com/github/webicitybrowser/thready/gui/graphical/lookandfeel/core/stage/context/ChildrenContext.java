package com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.context;

import com.github.webicitybrowser.thready.gui.directive.core.style.StyleGenerator;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.LookAndFeel;

public interface ChildrenContext extends Context {
	
	Context[] children(LookAndFeel lookAndFeel);

	StyleGenerator[] childrenStyleGenerators(StyleGenerator styleGenerator, Context[] children);

}
