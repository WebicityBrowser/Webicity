package com.github.webicitybrowser.webicity.renderer.frontend.html.thready.style.cssom;

import com.github.webicitybrowser.threadyweb.tree.WebComponent;

public interface CSSOMFilter {

	boolean isApplicable(WebComponent component, int index);
	
}
