package everyos.desktop.thready.laf.simple.component.message;

import everyos.desktop.thready.core.gui.message.Message;
import everyos.desktop.thready.core.gui.message.MessageHandler;
import everyos.desktop.thready.core.gui.message.MessageResponse;
import everyos.desktop.thready.core.gui.stage.box.Box;
import everyos.desktop.thready.core.positioning.Rectangle;
import everyos.desktop.thready.laf.simple.component.message.sub.MouseMessageHandler;

public class DefaultContentMessageHandler extends DefaultMessageHandler {
	
	private final MessageHandler mouseMessageHandler;
	
	public DefaultContentMessageHandler(Rectangle documentRect, Box box, MessageHandler... children) {
		this.mouseMessageHandler = new MouseMessageHandler(documentRect, box, children);
	}
	
	@Override
	public MessageResponse onMessage(Message message) {
		MessageResponse mouseMessageResponse = mouseMessageHandler.onMessage(message);
		if (mouseMessageResponse != null) {
			return mouseMessageResponse;
		}
		
		return super.onMessage(message);
	}

}
