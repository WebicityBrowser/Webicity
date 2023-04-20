package com.github.webicitybrowser.webicity.renderer.frontend.thready.html.style;

import com.github.webicitybrowser.thready.gui.directive.core.StyleGeneratorRoot;
import com.github.webicitybrowser.thready.gui.graphical.base.GUIContent;
import com.github.webicitybrowser.thready.gui.graphical.base.imp.GUIContentImp;
import com.github.webicitybrowser.thready.gui.graphical.directive.ChildrenDirective;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.LookAndFeel;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.LookAndFeelBuilder;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.simplelaf.SimpleLookAndFeel;
import com.github.webicitybrowser.thready.gui.tree.basics.ContainerComponent;
import com.github.webicitybrowser.thready.gui.tree.core.Component;
import com.github.webicitybrowser.thready.windowing.core.ScreenContent;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.WebLookAndFeel;
import com.github.webicitybrowser.threadyweb.tree.DocumentComponent;
import com.github.webicitybrowser.webicity.renderer.backend.html.HTMLRendererBackend;
import com.github.webicitybrowser.webicity.renderer.frontend.thready.core.ThreadyRendererFrontend;
import com.github.webicitybrowser.webicity.renderer.frontend.thready.html.style.cssom.CSSOMNode;
import com.github.webicitybrowser.webicity.renderer.frontend.thready.html.style.generator.DocumentStyleGeneratorRoot;

public class ThreadyHTMLRendererFrontent implements ThreadyRendererFrontend {

	private final HTMLRendererBackend backend;
	private final ScreenContent content;

	public ThreadyHTMLRendererFrontent(HTMLRendererBackend backend) {
		this.backend = backend;
		this.content = createContent();
	}

	@Override
	public ScreenContent getContent() {
		return content;
	}
	
	private ScreenContent createContent() {
		Component documentComponent = DocumentComponent.create(backend.getDocument());
		Component rootComponent = ContainerComponent.create()
			.directive(ChildrenDirective.of(documentComponent));
		
		LookAndFeelBuilder lookAndFeelBuilder = LookAndFeelBuilder.create();
		SimpleLookAndFeel.installTo(lookAndFeelBuilder);
		WebLookAndFeel.installTo(lookAndFeelBuilder);
		LookAndFeel lookAndFeel = lookAndFeelBuilder.build();
		
		GUIContent content = new GUIContentImp();
		content.setRoot(rootComponent, lookAndFeel, createStyleGenerator());
		
		return content;
	}
	
	private static StyleGeneratorRoot createStyleGenerator() {
		CSSOMNode cssomNode = CSSOMNode.create();
		
		return new DocumentStyleGeneratorRoot(() -> cssomNode);
	}

}
