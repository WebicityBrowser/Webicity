package com.github.webicitybrowser.webicitybrowser;

import com.github.webicitybrowser.spec.dom.node.Document;
import com.github.webicitybrowser.spec.dom.node.imp.DocumentImp;
import com.github.webicitybrowser.spec.dom.node.imp.DocumentTypeImp;
import com.github.webicitybrowser.spec.html.node.HTMLHtmlElement;
import com.github.webicitybrowser.spec.html.node.imp.HTMLBodyElementImp;
import com.github.webicitybrowser.spec.html.node.imp.HTMLHeadElementImp;
import com.github.webicitybrowser.spec.html.node.imp.HTMLHtmlElementImp;
import com.github.webicitybrowser.thready.gui.graphical.base.GUIContent;
import com.github.webicitybrowser.thready.gui.graphical.base.imp.GUIContentImp;
import com.github.webicitybrowser.thready.gui.graphical.directive.directive.ChildrenDirective;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.LookAndFeel;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.LookAndFeelBuilder;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.simplelaf.SimpleLookAndFeel;
import com.github.webicitybrowser.thready.gui.tree.basics.ContainerComponent;
import com.github.webicitybrowser.thready.gui.tree.core.Component;
import com.github.webicitybrowser.thready.windowing.core.GraphicsSystem;
import com.github.webicitybrowser.thready.windowing.skija.SkijaGraphicsSystem;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.WebLookAndFeel;
import com.github.webicitybrowser.threadyweb.tree.DocumentComponent;

public class Main {

	public static void main(String[] args) {
		Document document = new DocumentImp();
		document.appendChild(new DocumentTypeImp("html"));
		HTMLHtmlElement htmlElement = new HTMLHtmlElementImp();
		//document.appendChild(htmlElement);
		htmlElement.appendChild(new HTMLHeadElementImp());
		htmlElement.appendChild(new HTMLBodyElementImp());
		
		System.out.println(document);
		
		GraphicsSystem graphicsSystem = SkijaGraphicsSystem.createDefault();
		
		Component documentComponent = DocumentComponent.create(document);
		Component rootComponent = ContainerComponent.create()
			.directive(ChildrenDirective.of(documentComponent));
		
		LookAndFeelBuilder lookAndFeelBuilder = LookAndFeelBuilder.create();
		SimpleLookAndFeel.installTo(lookAndFeelBuilder);
		WebLookAndFeel.installTo(lookAndFeelBuilder);
		LookAndFeel lookAndFeel = lookAndFeelBuilder.build();
		
		GUIContent content = new GUIContentImp(graphicsSystem.getResourceLoader());
		content.setRoot(rootComponent, lookAndFeel);
		
		graphicsSystem.createWindow(window -> {
			window
				.getScreen()
				.setScreenContent(content);
		});
		
	}
	
}
