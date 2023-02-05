package everyos.desktop.thready.core.gui.message.result;

import everyos.desktop.thready.core.gui.message.MessageResponse;

public interface TabMessageResult extends MessageResponse {

	boolean hasFoundCurrentComponent();
	
	boolean hasFoundNextComponent();
	
}
