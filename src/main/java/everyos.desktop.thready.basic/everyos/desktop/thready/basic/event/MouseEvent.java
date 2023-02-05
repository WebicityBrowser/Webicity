package everyos.desktop.thready.basic.event;

import everyos.desktop.thready.core.gui.component.Component;
import everyos.desktop.thready.core.positioning.AbsolutePosition;

public interface MouseEvent {

	Component getComponent();
	
	int getAction();
	
	int getButton();
	
	boolean isSource();
	
	boolean isExternal();
	
	AbsolutePosition getViewportPosition();
	
	AbsolutePosition getScreenPosition();

}
