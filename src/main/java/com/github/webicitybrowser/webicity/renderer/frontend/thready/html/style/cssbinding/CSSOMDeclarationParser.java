package com.github.webicitybrowser.webicity.renderer.frontend.thready.html.style.cssbinding;

import com.github.webicitybrowser.spec.css.rule.Declaration;
import com.github.webicitybrowser.thready.gui.directive.core.Directive;

public interface CSSOMDeclarationParser {

	Directive[] parseDeclaration(Declaration rule);

}
