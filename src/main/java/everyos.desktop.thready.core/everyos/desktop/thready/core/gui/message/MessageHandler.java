package everyos.desktop.thready.core.gui.message;

public interface MessageHandler {

	MessageHandlerResult onEvent(Message event);
	
}
