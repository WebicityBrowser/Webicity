package com.github.webicitybrowser.webicity.renderer.frontend.thready.html;

import java.io.IOException;
import java.io.Reader;

import com.github.webicitybrowser.spec.css.parser.CSSParser;
import com.github.webicitybrowser.spec.css.parser.tokenizer.CSSTokenizer;
import com.github.webicitybrowser.spec.css.parser.tokens.Token;
import com.github.webicitybrowser.spec.css.rule.CSSRule;
import com.github.webicitybrowser.spec.css.rule.CSSStyleSheet;
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
import com.github.webicitybrowser.webicity.core.AssetLoader;
import com.github.webicitybrowser.webicity.core.renderer.RendererContext;
import com.github.webicitybrowser.webicity.renderer.backend.html.HTMLRendererBackend;
import com.github.webicitybrowser.webicity.renderer.frontend.thready.core.ThreadyRendererFrontend;
import com.github.webicitybrowser.webicity.renderer.frontend.thready.html.style.cssbinding.CSSOMBinder;
import com.github.webicitybrowser.webicity.renderer.frontend.thready.html.style.cssom.CSSOMNode;
import com.github.webicitybrowser.webicity.renderer.frontend.thready.html.style.generator.DocumentStyleGeneratorRoot;

public class ThreadyHTMLRendererFrontend implements ThreadyRendererFrontend {

	private final HTMLRendererBackend backend;
	private final RendererContext rendererContext;
	private final ScreenContent content;

	public ThreadyHTMLRendererFrontend(HTMLRendererBackend backend, RendererContext rendererContext) {
		this.backend = backend;
		this.rendererContext = rendererContext;
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
	
	private StyleGeneratorRoot createStyleGenerator() {
		CSSOMNode cssomNode = loadUAStylesheet();
		
		return new DocumentStyleGeneratorRoot(() -> new CSSOMNode[] { cssomNode });
	}

	private CSSOMNode loadUAStylesheet() {
		// TODO: Better error handling
		AssetLoader assetLoader = rendererContext.getAssetLoader();
		try (Reader reader = assetLoader.streamAsset("static", "renderer/html/ua.css")) {
			Token[] tokens = CSSTokenizer.create().tokenize(reader);
			CSSRule[] rules = CSSParser.create().parseAListOfRules(tokens);
			CSSStyleSheet stylesheet = () -> rules;
			return CSSOMBinder.create().createCSSOMFor(stylesheet);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

}
