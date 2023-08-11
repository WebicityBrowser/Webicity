package com.github.webicitybrowser.thready.dimensions.util;

import com.github.webicitybrowser.thready.dimensions.AbsoluteSize;
import com.github.webicitybrowser.thready.dimensions.RelativeDimension;

public final class AbsoluteSizeMath {

	private AbsoluteSizeMath() {}
	
	public static boolean fits(AbsoluteSize compared, AbsoluteSize reference) {
		return
			componentFits(compared.width(), reference.width()) &&
			componentFits(compared.height(), reference.height());
	}
	
	private static boolean componentFits(float compared, float reference) {
		if (reference == RelativeDimension.UNBOUNDED) {
			return true;
		} else if (compared == RelativeDimension.UNBOUNDED) {
			return false;
		} else {
			return compared <= reference;
		}
	}
	
}
