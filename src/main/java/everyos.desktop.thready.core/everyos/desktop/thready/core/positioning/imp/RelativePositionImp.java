package everyos.desktop.thready.core.positioning.imp;

import everyos.desktop.thready.core.positioning.AbsolutePosition;
import everyos.desktop.thready.core.positioning.AbsoluteSize;
import everyos.desktop.thready.core.positioning.RelativeDimension;
import everyos.desktop.thready.core.positioning.RelativePosition;

public class RelativePositionImp implements RelativePosition {
	
	private final RelativeDimension relativeX;
	private final RelativeDimension relativeY;

	public RelativePositionImp(RelativeDimension relativeX, RelativeDimension relativeY) {
		this.relativeX = relativeX;
		this.relativeY = relativeY;
	}
	
	public RelativePositionImp(float xr, float xa, float yr, float ya) {
		this(
			new RelativeDimensionImp(xr, xa),
			new RelativeDimensionImp(yr, ya)
		);
	}

	@Override
	public RelativeDimension getRelativeX() {
		return this.relativeX;
	}

	@Override
	public RelativeDimension getRelativeY() {
		return this.relativeY;
	}

	@Override
	public AbsolutePosition resolveAbsolutePosition(AbsoluteSize parentSize) {
		return new AbsolutePositionImp(
			relativeX.resolveAbsoluteDimensions(parentSize.getWidth()),
			relativeY.resolveAbsoluteDimensions(parentSize.getHeight()));
	}

	public static RelativePosition convertFrom(AbsolutePosition position) {
		return new RelativePositionImp(0, position.getX(), 0, position.getY());
	}
	
}
