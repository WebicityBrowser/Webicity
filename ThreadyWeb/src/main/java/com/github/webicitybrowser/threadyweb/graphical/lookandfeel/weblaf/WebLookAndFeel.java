package com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf;

import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.LookAndFeelBuilder;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.ui.br.BreakDisplay;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.ui.document.DocumentDisplay;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.ui.element.ElementDisplay;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.ui.image.ImageDisplay;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.ui.text.TextDisplay;
import com.github.webicitybrowser.threadyweb.tree.BreakComponent;
import com.github.webicitybrowser.threadyweb.tree.DocumentComponent;
import com.github.webicitybrowser.threadyweb.tree.ElementComponent;
import com.github.webicitybrowser.threadyweb.tree.TextComponent;
import com.github.webicitybrowser.threadyweb.tree.image.ImageComponent;

public final class WebLookAndFeel {

	private WebLookAndFeel() {}
	
	public static void installTo(LookAndFeelBuilder lookAndFeelBuilder) {
		lookAndFeelBuilder.registerComponentUI(DocumentComponent.class, DocumentDisplay::componentUI);
		lookAndFeelBuilder.registerComponentUI(ElementComponent.class, ElementDisplay::componentUI);
		lookAndFeelBuilder.registerComponentUI(BreakComponent.class, BreakDisplay::componentUI);
		lookAndFeelBuilder.registerComponentUI(TextComponent.class, TextDisplay::componentUI);
		lookAndFeelBuilder.registerComponentUI(ImageComponent.class, ImageDisplay::componentUI);
	}
	
}
