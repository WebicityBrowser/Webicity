package everyos.desktop.thready.core.gui.message.result;

import everyos.desktop.thready.core.gui.message.MessageHandlerResult;

public interface TabMessageResult extends MessageHandlerResult {

	boolean hasFoundCurrentComponent();
	
	boolean hasFoundNextComponent();
	
}
