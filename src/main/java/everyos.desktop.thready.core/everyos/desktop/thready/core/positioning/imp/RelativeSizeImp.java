package everyos.desktop.thready.core.positioning.imp;

import everyos.desktop.thready.core.positioning.AbsoluteSize;
import everyos.desktop.thready.core.positioning.RelativeDimension;
import everyos.desktop.thready.core.positioning.RelativeSize;

public class RelativeSizeImp implements RelativeSize {

	private final RelativeDimension relativeWidth;
	private final RelativeDimension relativeHeight;

	public RelativeSizeImp(RelativeDimension relativeWidth, RelativeDimension relativeHeight) {
		this.relativeWidth = relativeWidth;
		this.relativeHeight = relativeHeight;
	}
	
	public RelativeSizeImp(float wr, float wa, float hr, float ha) {
		this(
			new RelativeDimensionImp(wr, wa),
			new RelativeDimensionImp(hr, ha)
		);
	}

	@Override
	public RelativeDimension getRelativeWidth() {
		return this.relativeWidth;
	}

	@Override
	public RelativeDimension getRelativeHeight() {
		return this.relativeHeight;
	}

	@Override
	public AbsoluteSize resolveAbsoluteSize(AbsoluteSize parentSize) {
		return new AbsoluteSizeImp(
			relativeWidth.resolveAbsoluteDimensions(parentSize.getWidth()),
			relativeHeight.resolveAbsoluteDimensions(parentSize.getHeight()));
	}

}
