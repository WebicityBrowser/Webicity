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

	public static boolean containsPoint(Rectangle rectangle, AbsolutePosition point) {
		float rectX = rectangle.position().x();
		float rectWidth = rectangle.size().width();
		
		float rectY = rectangle.position().y();
		float rectHeight = rectangle.size().height();
		
		return
			between(rectX, point.x(), rectX + rectWidth - 1) &&
			between(rectY, point.y(), rectY + rectHeight - 1);
	}

	private static boolean between(float lower, float target, float higher) {
		return
			lower <= target &&
			higher >= target;
	}
	
}
