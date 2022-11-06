package everyos.desktop.thready.core.gui.message.type;

import everyos.desktop.thready.core.gui.message.Message;

public interface ActivationStateChangeMessage extends Message {

	boolean isActivated();
	
}
