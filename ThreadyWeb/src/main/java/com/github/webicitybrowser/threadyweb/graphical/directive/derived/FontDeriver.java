package com.github.webicitybrowser.threadyweb.graphical.directive.derived;

import java.util.List;
import java.util.Optional;

import com.github.webicitybrowser.thready.gui.directive.basics.pool.DirectiveDeriver;
import com.github.webicitybrowser.thready.gui.directive.core.Directive;
import com.github.webicitybrowser.thready.gui.directive.core.pool.DirectivePool;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.style.StyleContext;
import com.github.webicitybrowser.threadyweb.graphical.directive.text.FontFamilyDirective;
import com.github.webicitybrowser.threadyweb.graphical.directive.text.FontSizeDirective;
import com.github.webicitybrowser.threadyweb.graphical.directive.text.FontWeightDirective;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.util.WebFontUtil;

public class FontDeriver implements DirectiveDeriver<DerivedFontDirective> {

	private final StyleContext styleContext;

	public FontDeriver(StyleContext styleContext) {
		this.styleContext = styleContext;
	}

	@Override
	public Class<DerivedFontDirective> getDerivedType() {
		return DerivedFontDirective.class;
	}

	@Override
	public List<Class<? extends Directive>> getOwnDependencies() {
		return List.of(FontFamilyDirective.class, FontSizeDirective.class, FontWeightDirective.class);
	}

	@Override
	public List<Class<? extends Directive>> getParentDependencies() {
		return List.of(DerivedFontDirective.class);
	}

	@Override
	public Optional<DerivedFontDirective> derive(DirectivePool ownPool, DirectivePool parentPool) {
		return Optional.of(WebFontUtil.deriveFont(ownPool, parentPool, styleContext));
	}
	
}
