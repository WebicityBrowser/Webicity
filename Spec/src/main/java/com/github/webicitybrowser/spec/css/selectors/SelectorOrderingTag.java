package com.github.webicitybrowser.spec.css.selectors;

import com.github.webicitybrowser.spec.css.selectors.SelectorSpecificity.Source;

// TODO: How will imported resources be handled?
public record SelectorOrderingTag(Source source, int documentOrder) {
	
}
