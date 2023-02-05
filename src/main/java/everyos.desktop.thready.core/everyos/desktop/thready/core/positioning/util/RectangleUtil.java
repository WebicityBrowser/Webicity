package everyos.desktop.thready.core.positioning.util;

import everyos.desktop.thready.core.positioning.AbsolutePosition;
import everyos.desktop.thready.core.positioning.Rectangle;

public final class RectangleUtil {

	private RectangleUtil() {}
	
	public static boolean containsPoint(Rectangle rectangle, AbsolutePosition point) {
		float rectX = rectangle.getPosition().getX();
		float rectWidth = rectangle.getSize().getWidth();
		
		float rectY = rectangle.getPosition().getY();
		float rectHeight = rectangle.getSize().getHeight();
		
		return
			between(rectX, point.getX(), rectX + rectWidth - 1) &&
			between(rectY, point.getY(), rectY + rectHeight - 1);
	}

	private static boolean between(float lower, float target, float higher) {
		return
			lower <= target &&
			higher >= target;
	}
	
}
