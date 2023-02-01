package everyos.desktop.thready.core.gui.stage.box;

import everyos.desktop.thready.core.gui.directive.DirectivePool;

public interface Box {
	
	void addChild(Box child);
	
	Box[] getAdjustedBoxTree();

	DirectivePool getDirectivePool();
	
}
