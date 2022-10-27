package everyos.desktop.thready.core.positioning;

public interface RelativeDimension {

	float getAbsoluteComponent();
	
	float getRelativeComponent();
	
	float resolveAbsoluteDimentions(float parentDimension);
	
}
