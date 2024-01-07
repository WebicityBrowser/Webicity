package com.github.webicitybrowser.threadyweb.graphical.directive.text;

import com.github.webicitybrowser.thready.gui.directive.core.Directive;

public interface LineBreakDirective extends Directive {

	LineBreak getLineBreak();

	@Override
	default Class<? extends Directive> getPrimaryType() {
		return LineBreakDirective.class;
	}

	static LineBreakDirective of(LineBreak wordBreak) {
		return () -> wordBreak;
	}

	static enum LineBreak {
		AUTO, LOOSE, NORMAL, STRICT, ANYWHERE
	}

}
