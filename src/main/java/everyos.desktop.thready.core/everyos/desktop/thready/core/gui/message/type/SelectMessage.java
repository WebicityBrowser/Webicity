package everyos.desktop.thready.core.gui.message.type;

import javax.swing.text.Position;

import everyos.desktop.thready.core.gui.clipboard.Copiable;
import everyos.desktop.thready.core.gui.message.Message;

public interface SelectMessage extends Message {

	// Note: Selections should be traversed so that
	//  overlapping items are properly selected.
	//  Copiables should be added in reverse order
	
	Position getSelectionStartPos();
	
	Position getSelectionEndPos();
	
	boolean isSelectionStarted();
	
	boolean isSelectionEnded();
	
	void addCopiable(Copiable copiable);
	
}
