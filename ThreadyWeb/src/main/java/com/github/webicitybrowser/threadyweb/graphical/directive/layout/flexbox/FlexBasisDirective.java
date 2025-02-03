package com.github.webicitybrowser.threadyweb.graphical.directive.layout.flexbox;

import com.github.webicitybrowser.thready.gui.directive.core.Directive;
import com.github.webicitybrowser.threadyweb.graphical.directive.layout.common.size.SizeCalculationDirective;
import com.github.webicitybrowser.threadyweb.graphical.value.SizeCalculation;

public record FlexBasisDirective(SizeCalculation getSizeCalculation, boolean isAuto) implements SizeCalculationDirective {

	@Override
	public Class<? extends Directive> getPrimaryType() {
		return FlexBasisDirective.class;
	}

}
