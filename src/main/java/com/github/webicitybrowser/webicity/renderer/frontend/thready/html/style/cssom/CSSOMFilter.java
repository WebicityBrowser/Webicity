package com.github.webicitybrowser.webicity.renderer.frontend.thready.html.style.cssom;

import com.github.webicitybrowser.threadyweb.tree.WebComponent;

public interface CSSOMFilter {

	boolean isApplicable(WebComponent component, int index);
	
}
