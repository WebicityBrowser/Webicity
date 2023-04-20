package com.github.webicitybrowser.spec.css.parser.selectors;

import com.github.webicitybrowser.spec.css.parser.ParseFormatException;
import com.github.webicitybrowser.spec.css.parser.TokenStream;
import com.github.webicitybrowser.spec.css.selectors.ComplexSelectorPart;

public interface SelectorParser {

	ComplexSelectorPart parse(TokenStream stream) throws ParseFormatException;
	
}
