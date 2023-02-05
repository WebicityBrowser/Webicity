package everyos.desktop.thready.laf.simple.component.message;

import everyos.desktop.thready.core.gui.message.Message;
import everyos.desktop.thready.core.gui.message.MessageHandler;
import everyos.desktop.thready.core.gui.message.MessageResponse;

public class EmptyMessageHandler implements MessageHandler {

	@Override
	public MessageResponse onMessage(Message event) {
		return null;
	}

}
