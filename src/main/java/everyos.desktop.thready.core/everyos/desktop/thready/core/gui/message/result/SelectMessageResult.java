package everyos.desktop.thready.core.gui.message.result;

import everyos.desktop.thready.core.gui.message.MessageResponse;

public interface SelectMessageResult extends MessageResponse {

	boolean isSelectionStarted();
	
	boolean isSelectionFinished();
	
}
