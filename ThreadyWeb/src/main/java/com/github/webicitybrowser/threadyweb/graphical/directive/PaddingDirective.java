package com.github.webicitybrowser.threadyweb.graphical.directive;

import com.github.webicitybrowser.thready.gui.directive.core.Directive;
import com.github.webicitybrowser.threadyweb.graphical.value.SizeCalculation;

public interface PaddingDirective extends Directive {

	SizeCalculation getSizeCalculation();
	
	interface LeftPaddingDirective extends PaddingDirective {
		
		@Override
		default Class<? extends Directive> getPrimaryType() {
			return LeftPaddingDirective.class;
		}
		
	}

	interface RightPaddingDirective extends PaddingDirective {
		
		@Override
		default Class<? extends Directive> getPrimaryType() {
			return RightPaddingDirective.class;
		}
		
	}

	interface TopPaddingDirective extends PaddingDirective {
		
		@Override
		default Class<? extends Directive> getPrimaryType() {
			return TopPaddingDirective.class;
		}
		
	}

	interface BottomPaddingDirective extends PaddingDirective {
		
		@Override
		default Class<? extends Directive> getPrimaryType() {
			return BottomPaddingDirective.class;
		}
		
	}

	static LeftPaddingDirective ofLeft(SizeCalculation sizeCalculation) {
		return () -> sizeCalculation;
	}

	static RightPaddingDirective ofRight(SizeCalculation sizeCalculation) {
		return () -> sizeCalculation;
	}

	static TopPaddingDirective ofTop(SizeCalculation sizeCalculation) {
		return () -> sizeCalculation;
	}

	static BottomPaddingDirective ofBottom(SizeCalculation sizeCalculation) {
		return () -> sizeCalculation;
	}

}
