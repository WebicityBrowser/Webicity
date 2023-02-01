package everyos.desktop.thready.core.positioning.imp;

import java.util.Objects;

import everyos.desktop.thready.core.positioning.AbsoluteSize;

public class AbsoluteSizeImp implements AbsoluteSize {
	
	public static final AbsoluteSize ZERO_SIZE = new AbsoluteSizeImp(0, 0);
	public static final AbsoluteSize UNBOUNDED_SIZE = new AbsoluteSizeImp(-1, -1);
	
	private final float width;
	private final float height;

	public AbsoluteSizeImp(float width, float height) {
		this.width = width;
		this.height = height;
	}

	@Override
	public float getWidth() {
		return width;
	}

	@Override
	public float getHeight() {
		return height;
	}
	
	@Override
	public String toString() {
		return "AbsoluteSize [width=" + width + ", height=" + height +"]";
	}
	
	@Override
	public boolean equals(Object o) {
		return
			(o instanceof AbsoluteSize) &&
			((AbsoluteSize) o).getWidth() == width &&
			((AbsoluteSize) o).getHeight() == height;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(width, height);
	}

}
