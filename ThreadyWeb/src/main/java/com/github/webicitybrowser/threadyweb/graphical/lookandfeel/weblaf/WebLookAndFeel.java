package com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf;

import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.LookAndFeelBuilder;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.ui.br.BreakUI;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.ui.document.DocumentUI;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.ui.element.ElementUI;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.ui.image.ImageUI;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.ui.text.TextUI;
import com.github.webicitybrowser.threadyweb.tree.BreakComponent;
import com.github.webicitybrowser.threadyweb.tree.DocumentComponent;
import com.github.webicitybrowser.threadyweb.tree.ElementComponent;
import com.github.webicitybrowser.threadyweb.tree.TextComponent;
import com.github.webicitybrowser.threadyweb.tree.image.ImageComponent;

public final class WebLookAndFeel {

	private WebLookAndFeel() {}
	
	public static void installTo(LookAndFeelBuilder lookAndFeelBuilder) {
		lookAndFeelBuilder.registerComponentUI(DocumentComponent.class, DocumentUI::new);
		lookAndFeelBuilder.registerComponentUI(ElementComponent.class, ElementUI::new);
		lookAndFeelBuilder.registerComponentUI(BreakComponent.class, BreakUI::new);
		lookAndFeelBuilder.registerComponentUI(TextComponent.class, TextUI::new);
		lookAndFeelBuilder.registerComponentUI(ImageComponent.class, ImageUI::new);
	}
	
}
