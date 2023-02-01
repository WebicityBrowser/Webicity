package everyos.desktop.thready.core.positioning.imp;

import everyos.desktop.thready.core.positioning.RelativeDimension;

public class RelativeDimensionImp implements RelativeDimension {

	private final float relativeComponent;
	private final float absoluteComponent;

	public RelativeDimensionImp(float relativeComponent, float absoluteComponent) {
		this.relativeComponent = relativeComponent;
		this.absoluteComponent = absoluteComponent;
	}

	@Override
	public float getRelativeComponent() {
		return this.relativeComponent;
	}
	
	@Override
	public float getAbsoluteComponent() {
		return this.absoluteComponent;
	}

	@Override
	public float resolveAbsoluteDimensions(float parentDimension) {
		if (relativeComponent == RelativeDimension.UNBOUNDED || absoluteComponent == RelativeDimension.UNBOUNDED) {
			return RelativeDimension.UNBOUNDED;
		}
		
		return relativeComponent * parentDimension + absoluteComponent;
	}

}
