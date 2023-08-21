package com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.layout.flow.inline;

import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.box.Box;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.unit.ContextSwitch;

public interface FlowRecursiveContextSwitch extends ContextSwitch {
	
	void onBoxEnter(BoxEnterContext context);

	interface BoxEnterContext {
		
		Box getBox();
		
		void skipBox();
		
	}
	
}
