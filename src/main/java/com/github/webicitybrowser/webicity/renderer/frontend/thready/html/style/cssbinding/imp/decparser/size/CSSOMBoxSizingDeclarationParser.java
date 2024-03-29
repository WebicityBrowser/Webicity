package com.github.webicitybrowser.webicity.renderer.frontend.thready.html.style.cssbinding.imp.decparser.size;

import com.github.webicitybrowser.spec.css.parser.property.PropertyValueParser;
import com.github.webicitybrowser.spec.css.parser.property.boxsizing.BoxSizingValueParser;
import com.github.webicitybrowser.spec.css.property.boxsizing.BoxSizingValue;
import com.github.webicitybrowser.thready.gui.directive.core.Directive;
import com.github.webicitybrowser.threadyweb.graphical.directive.BoxSizingDirective;
import com.github.webicitybrowser.threadyweb.graphical.directive.BoxSizingDirective.BoxSizing;
import com.github.webicitybrowser.webicity.renderer.frontend.thready.html.style.cssbinding.imp.CSSOMNamedDeclarationParser;

public class CSSOMBoxSizingDeclarationParser implements CSSOMNamedDeclarationParser<BoxSizingValue> {

	private final BoxSizingValueParser boxSizingParser = new BoxSizingValueParser();

	@Override
	public PropertyValueParser<BoxSizingValue> getPropertyValueParser() {
		return this.boxSizingParser;
	}

	@Override
	public Directive[] translatePropertyValue(BoxSizingValue value) {
		return new Directive[] {
			BoxSizingDirective.of(value == BoxSizingValue.BORDER_BOX ?
				BoxSizing.BORDER_BOX :
				BoxSizing.CONTENT_BOX)
		};
	}
	
}
