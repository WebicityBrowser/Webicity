package everyos.desktop.thready.core.gui.stage.message;

import everyos.desktop.thready.core.gui.message.Message;
import everyos.desktop.thready.core.positioning.AbsolutePosition;

public interface MouseMessage extends Message {
	
	int getButton();
	
	int getAction();
	
	boolean isExternal();
	
	AbsolutePosition getViewportPosition();
	
	AbsolutePosition getScreenPosition();
	
}
