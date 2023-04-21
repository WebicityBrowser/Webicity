package com.github.webicitybrowser.webicity.renderer.frontend.thready.html.style.cssbinding;

import com.github.webicitybrowser.spec.css.rule.CSSStyleSheet;
import com.github.webicitybrowser.webicity.renderer.frontend.thready.html.style.cssbinding.imp.CSSOMBinderImp;
import com.github.webicitybrowser.webicity.renderer.frontend.thready.html.style.cssom.CSSOMNode;

public interface CSSOMBinder {

	CSSOMNode createCSSOMFor(CSSStyleSheet stylesheet);
	
	public static CSSOMBinder create() {
		return new CSSOMBinderImp();
	}
	
}
