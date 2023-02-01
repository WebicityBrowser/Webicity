package everyos.desktop.thready.core.positioning.imp;

import java.util.Objects;

import everyos.desktop.thready.core.positioning.AbsolutePosition;

public class AbsolutePositionImp implements AbsolutePosition {

	private final float x;
	private final float y;

	public AbsolutePositionImp(float x, float y) {
		this.x = x;
		this.y = y;
	}
	
	@Override
	public float getX() {
		return this.x;
	}

	@Override
	public float getY() {
		return this.y;
	}
	
	@Override
	public String toString() {
		return "AbsolutePositionImp [x=" + x + ", y=" + y + "]";
	}
	
	@Override
	public boolean equals(Object o) {
		return
			(o instanceof AbsolutePosition) &&
			((AbsolutePosition) o).getX() == x &&
			((AbsolutePosition) o).getY() == y;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(x, y);
	}
	
}
