package com.github.webicitybrowser.webicity.renderer.frontend.thready.html.style.cssbinding.decparser;

import java.util.List;

import com.github.webicitybrowser.spec.css.parser.property.PropertyValueParser;
import com.github.webicitybrowser.spec.css.parser.property.display.DisplayPropertyValueParser;
import com.github.webicitybrowser.spec.css.property.display.DisplayValue;
import com.github.webicitybrowser.spec.css.property.display.InnerDisplayType;
import com.github.webicitybrowser.spec.css.property.display.OuterDisplayType;
import com.github.webicitybrowser.thready.gui.directive.core.Directive;
import com.github.webicitybrowser.threadyweb.graphical.directive.InnerDisplayDirective;
import com.github.webicitybrowser.threadyweb.graphical.directive.OuterDisplayDirective;
import com.github.webicitybrowser.threadyweb.graphical.value.InnerDisplay;
import com.github.webicitybrowser.threadyweb.graphical.value.OuterDisplay;
import com.github.webicitybrowser.webicity.renderer.frontend.thready.html.style.cssbinding.CSSOMNamedDeclarationParser;

public class CSSOMDisplayDeclarationParser implements CSSOMNamedDeclarationParser<DisplayValue> {
	
	private final PropertyValueParser<DisplayValue> displayParser = new DisplayPropertyValueParser();
	
	@Override
	public PropertyValueParser<DisplayValue> getPropertyValueParser() {
		return this.displayParser;
	}

	@Override
	public Directive[] translatePropertyValue(DisplayValue value) {
		return new Directive[] {
			OuterDisplayDirective.of(convertOuterDisplayType(value.outerDisplayType())),
			InnerDisplayDirective.of(convertInnerDisplayType(value.innerDisplayType()))
		};
	}

	@Override
	public List<Class<? extends Directive>> getResultantDirectiveClasses() {
		return List.of(OuterDisplayDirective.class, InnerDisplayDirective.class);
	}

	private OuterDisplay convertOuterDisplayType(OuterDisplayType outerDisplayType) {
		switch (outerDisplayType) {
		case BLOCK:
			return OuterDisplay.BLOCK;
		case INLINE:
			return OuterDisplay.INLINE;
		case NONE:
			return OuterDisplay.NONE;
		case CONTENTS:
			return OuterDisplay.CONTENTS;
		default:
			throw new UnsupportedOperationException("Unsupported display type: " + outerDisplayType);
		}
	}

	private InnerDisplay convertInnerDisplayType(InnerDisplayType innerDisplayType) {
		switch (innerDisplayType) {
		case FLOW:
			return InnerDisplay.FLOW;
		case FLOW_ROOT:
			return InnerDisplay.FLOW_ROOT;
		case FLEX:
			return InnerDisplay.FLEX;
		default:
			throw new UnsupportedOperationException("Unsupported display type: " + innerDisplayType);
		}
	}
	
}
