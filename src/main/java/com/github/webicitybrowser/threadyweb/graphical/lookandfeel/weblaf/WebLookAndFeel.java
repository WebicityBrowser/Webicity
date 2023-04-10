package com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf;

import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.LookAndFeelBuilder;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.ui.document.DocumentUI;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.ui.element.ElementUI;
import com.github.webicitybrowser.threadyweb.tree.DocumentComponent;
import com.github.webicitybrowser.threadyweb.tree.ElementComponent;

public final class WebLookAndFeel {

	private WebLookAndFeel() {}
	
	public static void installTo(LookAndFeelBuilder lookAndFeelBuilder) {
		lookAndFeelBuilder.registerComponentUI(DocumentComponent.class, DocumentUI::new);
		lookAndFeelBuilder.registerComponentUI(ElementComponent.class, ElementUI::new);
	}
	
}
