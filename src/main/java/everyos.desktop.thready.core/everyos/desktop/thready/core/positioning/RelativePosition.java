package everyos.desktop.thready.core.positioning;

public interface RelativePosition {
	
	RelativeDimension getRelativeX();
	
	RelativeDimension getRelativeY();
	
	AbsolutePosition resolveAbsolutePosition(AbsoluteSize parentSize);
	
}
