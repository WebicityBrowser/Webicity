package everyos.desktop.thready.core.gui.message.result;

import everyos.desktop.thready.core.gui.message.MessageHandlerResult;

public interface SelectMessageResult extends MessageHandlerResult {

	boolean isSelectionStarted();
	
	boolean isSelectionFinished();
	
}
