package com.github.webicitybrowser.webicitybrowser;

import java.io.IOException;
import java.io.StringReader;

import com.github.webicitybrowser.spec.dom.node.Document;
import com.github.webicitybrowser.spec.dom.node.imp.DocumentImp;
import com.github.webicitybrowser.spec.html.binding.BindingHTMLTreeBuilder;
import com.github.webicitybrowser.spec.html.parse.HTMLTreeBuilder;
import com.github.webicitybrowser.spec.infra.Namespace;
import com.github.webicitybrowser.spiderhtml.SpiderHTMLParserImp;
import com.github.webicitybrowser.thready.color.Colors;
import com.github.webicitybrowser.thready.gui.directive.basics.pool.BasicDirectivePool;
import com.github.webicitybrowser.thready.gui.directive.core.DirectivePool;
import com.github.webicitybrowser.thready.gui.directive.core.StyleGeneratorRoot;
import com.github.webicitybrowser.thready.gui.graphical.base.GUIContent;
import com.github.webicitybrowser.thready.gui.graphical.base.imp.GUIContentImp;
import com.github.webicitybrowser.thready.gui.graphical.directive.ChildrenDirective;
import com.github.webicitybrowser.thready.gui.graphical.directive.ForegroundColorDirective;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.LookAndFeel;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.LookAndFeelBuilder;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.simplelaf.SimpleLookAndFeel;
import com.github.webicitybrowser.thready.gui.tree.basics.ContainerComponent;
import com.github.webicitybrowser.thready.gui.tree.core.Component;
import com.github.webicitybrowser.thready.windowing.core.GraphicsSystem;
import com.github.webicitybrowser.thready.windowing.skija.SkijaGraphicsSystem;
import com.github.webicitybrowser.threadyweb.graphical.directive.OuterDisplayDirective;
import com.github.webicitybrowser.threadyweb.graphical.directive.OuterDisplayDirective.OuterDisplay;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.WebLookAndFeel;
import com.github.webicitybrowser.threadyweb.tree.DocumentComponent;
import com.github.webicitybrowser.webicity.renderer.frontend.html.thready.style.cssom.CSSOMNode;
import com.github.webicitybrowser.webicity.renderer.frontend.html.thready.style.cssom.filters.TypeFilter;
import com.github.webicitybrowser.webicity.renderer.frontend.html.thready.style.generator.DocumentStyleGeneratorRoot;

public class Main {

	public static void main(String[] args) {
		String html =
			"<!doctype html><html><head></head><body><a>ATime<a>by</a><div>Once</div>there</a></body></html>";
		Document document = parseHTML(html);
		System.out.println(document);
		createGUIFor(document);
	}

	private static Document parseHTML(String html) {
		Document document = new DocumentImp();
		HTMLTreeBuilder treeBuilder = new BindingHTMLTreeBuilder(document);
		try {
			new SpiderHTMLParserImp().parse(new StringReader(html), treeBuilder);
		} catch (IOException e) {
			throw new RuntimeException(e);
		};
		
		return document;
	}
	
	private static void createGUIFor(Document document) {
		GraphicsSystem graphicsSystem = SkijaGraphicsSystem.createDefault();
		
		Component documentComponent = DocumentComponent.create(document);
		Component rootComponent = ContainerComponent.create()
			.directive(ChildrenDirective.of(documentComponent));
		
		LookAndFeelBuilder lookAndFeelBuilder = LookAndFeelBuilder.create();
		SimpleLookAndFeel.installTo(lookAndFeelBuilder);
		WebLookAndFeel.installTo(lookAndFeelBuilder);
		LookAndFeel lookAndFeel = lookAndFeelBuilder.build();
		
		GUIContent content = new GUIContentImp(graphicsSystem.getResourceLoader());
		content.setRoot(rootComponent, lookAndFeel, createStyleGenerator());
		
		graphicsSystem.createWindow(window -> {
			window
				.getScreen()
				.setScreenContent(content);
		});
	}

	private static StyleGeneratorRoot createStyleGenerator() {
		DirectivePool aDirectives = new BasicDirectivePool()
			.directive(ForegroundColorDirective.of(Colors.BLUE))
			.directive(OuterDisplayDirective.of(OuterDisplay.INLINE));
		DirectivePool divDirectives = new BasicDirectivePool()
			.directive(ForegroundColorDirective.of(Colors.GREEN));
		
		CSSOMNode cssomNode = CSSOMNode.create();
		cssomNode
			.getChild(new TypeFilter(Namespace.HTML_NAMESPACE, "a"))
			.addDirectivePool(aDirectives);
		cssomNode
			.getChild(new TypeFilter(Namespace.HTML_NAMESPACE, "div"))
			.addDirectivePool(divDirectives);
		
		return new DocumentStyleGeneratorRoot(() -> cssomNode);
	}
	
}
