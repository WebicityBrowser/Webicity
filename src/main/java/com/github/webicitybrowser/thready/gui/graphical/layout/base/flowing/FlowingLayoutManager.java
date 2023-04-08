package com.github.webicitybrowser.thready.gui.graphical.layout.base.flowing;

import com.github.webicitybrowser.thready.gui.graphical.layout.base.flowing.imp.FlowingLayoutManagerImp;
import com.github.webicitybrowser.thready.gui.graphical.layout.core.SolidLayoutManager;

public interface FlowingLayoutManager extends SolidLayoutManager {

	public static FlowingLayoutManager create() {
		return new FlowingLayoutManagerImp();
	}

}
