package everyos.desktop.thready.laf.simple.component.message;

import everyos.desktop.thready.core.gui.message.Message;
import everyos.desktop.thready.core.gui.message.MessageHandler;
import everyos.desktop.thready.core.gui.message.MessageHandlerResult;

public class EmptyMessageHandler implements MessageHandler {

	@Override
	public MessageHandlerResult onEvent(Message event) {
		return null;
	}

}
