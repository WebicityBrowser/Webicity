package com.github.webicitybrowser.threadyweb.graphical.directive.layout.common;

import com.github.webicitybrowser.thready.gui.directive.core.Directive;
import com.github.webicitybrowser.threadyweb.graphical.value.SizeCalculation;

public interface MarginDirective extends Directive {

	SizeCalculation getSizeCalculation();
	
	interface LeftMarginDirective extends MarginDirective {
		
		@Override
		default Class<? extends Directive> getPrimaryType() {
			return LeftMarginDirective.class;
		}
		
	}

	interface RightMarginDirective extends MarginDirective {
		
		@Override
		default Class<? extends Directive> getPrimaryType() {
			return RightMarginDirective.class;
		}
		
	}

	interface TopMarginDirective extends MarginDirective {
		
		@Override
		default Class<? extends Directive> getPrimaryType() {
			return TopMarginDirective.class;
		}
		
	}

	interface BottomMarginDirective extends MarginDirective {
		
		@Override
		default Class<? extends Directive> getPrimaryType() {
			return BottomMarginDirective.class;
		}
		
	}

	static LeftMarginDirective ofLeft(SizeCalculation sizeCalculation) {
		return () -> sizeCalculation;
	}

	static RightMarginDirective ofRight(SizeCalculation sizeCalculation) {
		return () -> sizeCalculation;
	}

	static TopMarginDirective ofTop(SizeCalculation sizeCalculation) {
		return () -> sizeCalculation;
	}

	static BottomMarginDirective ofBottom(SizeCalculation sizeCalculation) {
		return () -> sizeCalculation;
	}

}
