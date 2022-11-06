package everyos.desktop.thready.core.gui.message.type;

import everyos.desktop.thready.core.gui.message.Message;
import everyos.desktop.thready.core.gui.message.MessageHandler;
import everyos.desktop.thready.core.positioning.Rectangle;

public interface ClickSpotRegistrationMessage extends Message {
	
	void registerClickSpot(ClickSpot clickSpot);
	
	public static interface ClickSpot {
		
		Rectangle getBounds();
		
		MessageHandler getHandler();
		
	}
	
}
