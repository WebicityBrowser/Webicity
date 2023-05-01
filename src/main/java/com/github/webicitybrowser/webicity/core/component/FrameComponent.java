package com.github.webicitybrowser.webicity.core.component;

import com.github.webicitybrowser.thready.gui.tree.basics.imp.BaseComponent;
import com.github.webicitybrowser.thready.gui.tree.core.Component;
import com.github.webicitybrowser.webicity.core.ui.Frame;

public class FrameComponent extends BaseComponent {
	
	private final Frame frame;
	
	public FrameComponent(Frame frame) {
		this.frame = frame;
	}
	
	@Override
	public Class<? extends Component> getPrimaryType() {
		return FrameComponent.class;
	}

	public Frame getFrame() {
		return this.frame;
	}

}
