package com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.ui.document;

import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.ComponentUI;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.context.Context;
import com.github.webicitybrowser.threadyweb.tree.DocumentComponent;

public class DocumentContext implements Context {

	private final ComponentUI componentUI;
	
	private ComponentUI childComponentUI;

	public DocumentContext(ComponentUI componentUI) {
		this.componentUI = componentUI;
	}
	
	public ComponentUI componentUI() {
		return this.componentUI;
	}
	
	public DocumentComponent component() {
		return (DocumentComponent) componentUI.getComponent();
	}

	public void setChildComponentUI(ComponentUI childComponentUI) {
		this.childComponentUI = childComponentUI;
	}
	
	public ComponentUI getChildComponentUI() {
		return this.childComponentUI;
	}
	
}
