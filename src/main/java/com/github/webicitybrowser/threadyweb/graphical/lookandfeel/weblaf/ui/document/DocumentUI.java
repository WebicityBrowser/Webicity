package com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.ui.document;

import com.github.webicitybrowser.thready.gui.graphical.base.InvalidationLevel;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.ComponentUI;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.UIDisplay;
import com.github.webicitybrowser.thready.gui.tree.core.Component;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.display.scroll.ScrollDisplay;
import com.github.webicitybrowser.threadyweb.tree.DocumentComponent;

public class DocumentUI implements ComponentUI {
	
	private static final UIDisplay<?, ?, ?> DOCUMENT_DISPLAY = new ScrollDisplay(new DocumentDisplay());
	
	private final DocumentComponent component;
	private final ComponentUI parent;
	
	public DocumentUI(Component component, ComponentUI parent) {
		this.component = (DocumentComponent) component;
		this.parent = parent;
		this.component.addStylesheetsChangedListener(() -> invalidate(InvalidationLevel.BOX));
	}

	@Override
	public DocumentComponent getComponent() {
		return this.component;
	}
	
	@Override
	public void invalidate(InvalidationLevel level) {
		parent.invalidate(level);
	}

	@Override
	public UIDisplay<?, ?, ?> getRootDisplay() {
		return DOCUMENT_DISPLAY;
	}

}
