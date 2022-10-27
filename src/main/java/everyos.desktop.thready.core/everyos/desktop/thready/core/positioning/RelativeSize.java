package everyos.desktop.thready.core.positioning;

public interface RelativeSize {
	
	// TODO: Comparison system for indeterminate and infinite sizes
	
	RelativeDimension getRelativeWidth();

	RelativeDimension getRelativeHeight();
	
	AbsoluteSize resolveAbsoluteSize(AbsoluteSize parentSize);
	
}
