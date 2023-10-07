package com.github.webicitybrowser.thready.gui.graphical.base.imp.stage.box;

import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.LookAndFeel;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.box.BoxContext;

public class BoxContextImp implements BoxContext {

	private final LookAndFeel lookAndFeel;

	public BoxContextImp(LookAndFeel lookAndFeel) {
		this.lookAndFeel = lookAndFeel;
	}

	@Override
	public LookAndFeel getLookAndFeel() {
		return this.lookAndFeel;
	}

}
