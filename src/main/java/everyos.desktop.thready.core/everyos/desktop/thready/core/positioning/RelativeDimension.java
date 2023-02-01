package everyos.desktop.thready.core.positioning;

public interface RelativeDimension {
	
	public static float UNBOUNDED = -1;
	
	float getRelativeComponent();
	
	float getAbsoluteComponent();
	
	float resolveAbsoluteDimensions(float parentDimension);
	
}
