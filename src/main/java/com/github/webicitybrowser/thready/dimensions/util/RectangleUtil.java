package com.github.webicitybrowser.thready.dimensions.util;

import com.github.webicitybrowser.thready.dimensions.AbsolutePosition;
import com.github.webicitybrowser.thready.dimensions.AbsoluteSize;
import com.github.webicitybrowser.thready.dimensions.Rectangle;

public final class RectangleUtil {

	private RectangleUtil() {}
	
	public static boolean intersects(Rectangle rectangleA, Rectangle rectangleB) {
		AbsolutePosition positionA = rectangleA.position();
		AbsolutePosition positionB = rectangleB.position();
		AbsoluteSize sizeA = rectangleA.size();
		AbsoluteSize sizeB = rectangleB.size();
		
		boolean rALeftOfRB = positionA.x() + sizeA.width() < positionB.x();
		boolean rBLeftOfRA = positionB.x() + sizeB.width() < positionA.x();
		boolean rAOverRB = positionA.y() + sizeA.height() < positionB.y();
		boolean rBOverRA = positionB.y() + sizeB.height() < positionA.y();
		
		return
			!rALeftOfRB && !rBLeftOfRA &&
			!rAOverRB && !rBOverRA;
	}
	
}
