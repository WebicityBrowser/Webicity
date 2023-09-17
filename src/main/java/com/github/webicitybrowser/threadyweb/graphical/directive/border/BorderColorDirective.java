package com.github.webicitybrowser.threadyweb.graphical.directive.border;

import com.github.webicitybrowser.thready.color.format.ColorFormat;
import com.github.webicitybrowser.thready.gui.directive.core.Directive;

public interface BorderColorDirective extends Directive {

	ColorFormat getColor();
	
	interface LeftBorderColorDirective extends BorderColorDirective {
		
		@Override
		default Class<? extends Directive> getPrimaryType() {
			return LeftBorderColorDirective.class;
		}
		
	}

	interface RightBorderColorDirective extends BorderColorDirective {
		
		@Override
		default Class<? extends Directive> getPrimaryType() {
			return RightBorderColorDirective.class;
		}
		
	}

	interface TopBorderColorDirective extends BorderColorDirective {
		
		@Override
		default Class<? extends Directive> getPrimaryType() {
			return TopBorderColorDirective.class;
		}
		
	}

	interface BottomBorderColorDirective extends BorderColorDirective {
		
		@Override
		default Class<? extends Directive> getPrimaryType() {
			return BottomBorderColorDirective.class;
		}
		
	}

	static LeftBorderColorDirective ofLeft(ColorFormat color) {
		return () -> color;
	}

	static RightBorderColorDirective ofRight(ColorFormat color) {
		return () -> color;
	}

	static TopBorderColorDirective ofTop(ColorFormat color) {
		return () -> color;
	}

	static BottomBorderColorDirective ofBottom(ColorFormat color) {
		return () -> color;
	}

}
