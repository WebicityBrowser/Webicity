package com.github.webicitybrowser.webicity.renderer.frontend.thready.html;

import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import com.github.webicitybrowser.spec.css.parser.CSSParser;
import com.github.webicitybrowser.spec.css.parser.tokenizer.CSSTokenizer;
import com.github.webicitybrowser.spec.css.parser.tokens.Token;
import com.github.webicitybrowser.spec.css.rule.CSSRule;
import com.github.webicitybrowser.spec.css.rule.CSSRuleList;
import com.github.webicitybrowser.spec.html.node.HTMLDocument;
import com.github.webicitybrowser.thready.drawing.core.text.CommonFontWeights;
import com.github.webicitybrowser.thready.drawing.core.text.FontDecoration;
import com.github.webicitybrowser.thready.drawing.core.text.FontSettings;
import com.github.webicitybrowser.thready.drawing.core.text.source.FontSource;
import com.github.webicitybrowser.thready.drawing.core.text.source.NamedFontSource;
import com.github.webicitybrowser.thready.gui.directive.core.style.StyleGeneratorRoot;
import com.github.webicitybrowser.thready.gui.graphical.base.GUIContent;
import com.github.webicitybrowser.thready.gui.graphical.base.GUIContent.GUIContentConfiguration;
import com.github.webicitybrowser.thready.gui.graphical.base.imp.GUIContentImp;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.LookAndFeel;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.LookAndFeelBuilder;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.simplelaf.SimpleLookAndFeel;
import com.github.webicitybrowser.thready.gui.tree.core.Component;
import com.github.webicitybrowser.thready.windowing.core.ScreenContent;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.WebLookAndFeel;
import com.github.webicitybrowser.threadyweb.tree.DocumentComponent;
import com.github.webicitybrowser.webicity.core.AssetLoader;
import com.github.webicitybrowser.webicity.renderer.backend.html.HTMLRendererBackend;
import com.github.webicitybrowser.webicity.renderer.backend.html.HTMLRendererContext;
import com.github.webicitybrowser.webicity.renderer.backend.html.cssom.CSSOMFilterCreator;
import com.github.webicitybrowser.webicity.renderer.backend.html.cssom.CSSOMTree;
import com.github.webicitybrowser.webicity.renderer.backend.html.cssom.CSSOMTreeGenerator;
import com.github.webicitybrowser.webicity.renderer.frontend.thready.core.ThreadyRendererFrontend;
import com.github.webicitybrowser.webicity.renderer.frontend.thready.html.component.WebComponentContextImp;
import com.github.webicitybrowser.webicity.renderer.frontend.thready.html.style.generator.DocumentStyleGenerator;
import com.github.webicitybrowser.webicity.renderer.frontend.thready.html.style.generator.DocumentStyleGeneratorRoot;
import com.github.webicitybrowser.webicity.renderer.frontend.thready.html.style.generator.DocumentStyleSheetSet;

public class ThreadyHTMLRendererFrontend implements ThreadyRendererFrontend {

	private final HTMLRendererBackend backend;
	private final HTMLRendererContext htmlRendererContext;
	private final ScreenContent content;

	public ThreadyHTMLRendererFrontend(HTMLRendererBackend backend, HTMLRendererContext htmlRendererContext) {
		this.backend = backend;
		this.htmlRendererContext = htmlRendererContext;
		this.content = createContent();
	}

	@Override
	public ScreenContent getContent() {
		return content;
	}
	
	private ScreenContent createContent() {
		WebComponentContextImp componentContext = new WebComponentContextImp(htmlRendererContext);
		Component documentComponent = DocumentComponent.create(backend.getDocument(), componentContext);
		LookAndFeelBuilder lookAndFeelBuilder = LookAndFeelBuilder.create();
		SimpleLookAndFeel.installTo(lookAndFeelBuilder);
		WebLookAndFeel.installTo(lookAndFeelBuilder);
		LookAndFeel lookAndFeel = lookAndFeelBuilder.build();
		FontSettings fontSettings = new FontSettings(
			new FontSource[] { new NamedFontSource("Times New Roman") }, 16,
			CommonFontWeights.NORMAL, new FontDecoration[0]);
		
		GUIContent content = new GUIContentImp();
		content.setRoot(new GUIContentConfiguration(
			documentComponent, lookAndFeel, createStyleGenerator(), fontSettings));
		
		return content;
	}
	
	private StyleGeneratorRoot createStyleGenerator() {
		HTMLDocument document = backend.getDocument();
		DocumentStyleSheetSet styleSheetSet = new DocumentStyleSheetSet(document.getStyleSheets());
		styleSheetSet.addUARules(loadUAStylesheet());
		
		return new DocumentStyleGeneratorRoot(backend.getDocument(), () -> createCSSOMTrees(styleSheetSet));
	}

	private CSSRuleList loadUAStylesheet() {
		// TODO: Better error handling
		AssetLoader assetLoader = htmlRendererContext.rendererContext().getRenderingEngine().getAssetLoader();
		try (Reader reader = assetLoader.streamAsset("static", "renderer/html/ua.css")) {
			Token[] tokens = CSSTokenizer.create().tokenize(reader);
			CSSRule[] rules = CSSParser.create().parseAListOfRules(tokens);
			return CSSRuleList.create(rules);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	@SuppressWarnings("unchecked")
	private CSSOMTree<DocumentStyleGenerator, CSSRuleList>[] createCSSOMTrees(DocumentStyleSheetSet styleSheetSet) {
		List<CSSOMTree<DocumentStyleGenerator, CSSRuleList>> cssomTrees = new ArrayList<>();
		CSSOMTreeGenerator<DocumentStyleGenerator> binder = CSSOMTreeGenerator.create(CSSOMFilterCreator.create(node -> node.getDOMNode()));
		for (CSSRuleList rules: styleSheetSet.getRuleLists()) {
			cssomTrees.add(binder.createCSSOMFor(rules));
		}
		
		return cssomTrees.toArray(new CSSOMTree[0]);
	}

}
