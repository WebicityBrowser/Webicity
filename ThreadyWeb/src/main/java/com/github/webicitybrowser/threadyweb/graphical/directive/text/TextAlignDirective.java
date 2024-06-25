package com.github.webicitybrowser.threadyweb.graphical.directive.text;

import com.github.webicitybrowser.thready.gui.directive.core.Directive;

public interface TextAlignDirective extends Directive {
	
	TextAlign getTextAlign();

	@Override
	default Class<? extends Directive> getPrimaryType() {
		return TextAlignDirective.class;
	}

	static TextAlignDirective of(TextAlign textAlign) {
		return () -> textAlign;
	}

	static enum TextAlign {
		START, END, LEFT, RIGHT, CENTER, JUSTIFY, MATCH_PARENT, JUSTIFY_ALL
	}

}
