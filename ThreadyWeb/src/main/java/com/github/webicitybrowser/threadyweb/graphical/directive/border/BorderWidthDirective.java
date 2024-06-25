package com.github.webicitybrowser.threadyweb.graphical.directive.border;

import com.github.webicitybrowser.thready.gui.directive.core.Directive;
import com.github.webicitybrowser.threadyweb.graphical.value.SizeCalculation;

public interface BorderWidthDirective extends Directive {

	SizeCalculation getSizeCalculation();
	
	interface LeftBorderWidthDirective extends BorderWidthDirective {
		
		@Override
		default Class<? extends Directive> getPrimaryType() {
			return LeftBorderWidthDirective.class;
		}
		
	}

	interface RightBorderWidthDirective extends BorderWidthDirective {
		
		@Override
		default Class<? extends Directive> getPrimaryType() {
			return RightBorderWidthDirective.class;
		}
		
	}

	interface TopBorderWidthDirective extends BorderWidthDirective {
		
		@Override
		default Class<? extends Directive> getPrimaryType() {
			return TopBorderWidthDirective.class;
		}
		
	}

	interface BottomBorderWidthDirective extends BorderWidthDirective {
		
		@Override
		default Class<? extends Directive> getPrimaryType() {
			return BottomBorderWidthDirective.class;
		}
		
	}

	static LeftBorderWidthDirective ofLeft(SizeCalculation sizeCalculation) {
		return () -> sizeCalculation;
	}

	static RightBorderWidthDirective ofRight(SizeCalculation sizeCalculation) {
		return () -> sizeCalculation;
	}

	static TopBorderWidthDirective ofTop(SizeCalculation sizeCalculation) {
		return () -> sizeCalculation;
	}

	static BottomBorderWidthDirective ofBottom(SizeCalculation sizeCalculation) {
		return () -> sizeCalculation;
	}

}
