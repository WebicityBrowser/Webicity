package com.github.webicitybrowser.webicity.renderer.frontend.thready.html.style.cssbinding.imp.decparser.size;

import java.util.Map;

import com.github.webicitybrowser.threadyweb.graphical.directive.layout.common.size.HeightDirective;
import com.github.webicitybrowser.threadyweb.graphical.directive.layout.common.size.MaxHeightDirective;
import com.github.webicitybrowser.threadyweb.graphical.directive.layout.common.size.MaxWidthDirective;
import com.github.webicitybrowser.threadyweb.graphical.directive.layout.common.size.MinHeightDirective;
import com.github.webicitybrowser.threadyweb.graphical.directive.layout.common.size.MinWidthDirective;
import com.github.webicitybrowser.threadyweb.graphical.directive.layout.common.size.WidthDirective;
import com.github.webicitybrowser.webicity.renderer.frontend.thready.html.style.cssbinding.imp.CSSOMNamedDeclarationParser;

public class CSSOMSizeBindings {
	
	private CSSOMSizeBindings() {}

	public static void installTo(Map<String, CSSOMNamedDeclarationParser<?>> namedDeclarationParsers) {
			namedDeclarationParsers.put("box-sizing", new CSSOMBoxSizingDeclarationParser());
		namedDeclarationParsers.put("width", new CSSOMDimensionDeclarationParser(WidthDirective::of));
		namedDeclarationParsers.put("min-width", new CSSOMDimensionDeclarationParser(MinWidthDirective::of));
		namedDeclarationParsers.put("max-width", new CSSOMDimensionDeclarationParser(MaxWidthDirective::of));
		namedDeclarationParsers.put("height", new CSSOMDimensionDeclarationParser(HeightDirective::of));
		namedDeclarationParsers.put("min-height", new CSSOMDimensionDeclarationParser(MinHeightDirective::of));
		namedDeclarationParsers.put("max-height", new CSSOMDimensionDeclarationParser(MaxHeightDirective::of));
	}

}
