package everyos.desktop.thready.laf.simple.component.ui.container.fluid;

import everyos.desktop.thready.core.gui.message.Message;
import everyos.desktop.thready.core.gui.message.MessageHandler;
import everyos.desktop.thready.core.gui.message.MessageResponse;
import everyos.desktop.thready.core.positioning.Rectangle;
import everyos.desktop.thready.laf.simple.component.message.DefaultMessageHandler;
import everyos.desktop.thready.laf.simple.component.message.sub.MouseMessageHandler;

public class HorizontalChildrenMessageHandler extends DefaultMessageHandler {

private final MessageHandler mouseMessageHandler;
	
	public HorizontalChildrenMessageHandler(Rectangle documentRect, MessageHandler... children) {
		this.mouseMessageHandler = new MouseMessageHandler(documentRect, null, children);
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
