package com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf;

import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.LookAndFeelBuilder;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.ui.document.DocumentUI;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.ui.element.ElementUI;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.ui.image.ImageUI;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.ui.text.TextUI;
import com.github.webicitybrowser.threadyweb.tree.DocumentComponent;
import com.github.webicitybrowser.threadyweb.tree.ElementComponent;
import com.github.webicitybrowser.threadyweb.tree.ImageComponent;
import com.github.webicitybrowser.threadyweb.tree.TextComponent;

public final class WebLookAndFeel {

	private WebLookAndFeel() {}
	
	public static void installTo(LookAndFeelBuilder lookAndFeelBuilder) {
		lookAndFeelBuilder.registerComponentUI(DocumentComponent.class, DocumentUI::new);
		lookAndFeelBuilder.registerComponentUI(ElementComponent.class, ElementUI::new);
		lookAndFeelBuilder.registerComponentUI(TextComponent.class, TextUI::new);
		lookAndFeelBuilder.registerComponentUI(ImageComponent.class, ImageUI::new);
	}
	
}
