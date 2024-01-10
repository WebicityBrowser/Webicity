package com.github.webicitybrowser.webicity.renderer.frontend.thready.html.style.cssbinding;

import com.github.webicitybrowser.spec.css.rule.CSSRuleList;
import com.github.webicitybrowser.webicity.renderer.backend.html.cssom.CSSOMTree;
import com.github.webicitybrowser.webicity.renderer.frontend.thready.html.style.cssbinding.imp.CSSOMBinderImp;
import com.github.webicitybrowser.webicity.renderer.frontend.thready.html.style.generator.DocumentStyleGenerator;

public interface CSSOMBinder {

	CSSOMTree<DocumentStyleGenerator, CSSRuleList> createCSSOMFor(CSSRuleList ruleList);
	
	public static CSSOMBinder create() {
		return new CSSOMBinderImp();
	}
	
}
