package everyos.desktop.thready.core.positioning.imp;

import everyos.desktop.thready.core.positioning.AbsolutePosition;
import everyos.desktop.thready.core.positioning.AbsoluteSize;
import everyos.desktop.thready.core.positioning.Rectangle;

public class RectangleImp implements Rectangle {
	
	private final AbsolutePosition position;
	private final AbsoluteSize size;

	public RectangleImp(AbsolutePosition position, AbsoluteSize size) {
		this.position = position;
		this.size = size;
	}

	@Override
	public AbsolutePosition getPosition() {
		return this.position;
	}

	@Override
	public AbsoluteSize getSize() {
		return this.size;
	}

	@Override
	public String toString() {
		return "Rectangle [position=" + position + ", size=" + size + "]";
	}

}
