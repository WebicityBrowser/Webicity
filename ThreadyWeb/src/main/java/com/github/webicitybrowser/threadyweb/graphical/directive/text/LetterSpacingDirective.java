package com.github.webicitybrowser.threadyweb.graphical.directive.text;

import com.github.webicitybrowser.thready.gui.directive.core.Directive;
import com.github.webicitybrowser.threadyweb.graphical.value.SizeCalculation;

public interface LetterSpacingDirective extends Directive {
	
	SizeCalculation getLetterSpacing();

	@Override
	default Class<? extends Directive> getPrimaryType() {
		return LetterSpacingDirective.class;
	}
	
	static LetterSpacingDirective of(SizeCalculation letterSpacing) {
		return (LetterSpacingDirective) () -> letterSpacing;
	}

}
