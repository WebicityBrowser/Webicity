package everyos.browser.webicity.webribbon.gui.box.stage;

import everyos.engine.ribbon.core.shape.Dimension;
import everyos.engine.ribbon.core.shape.Position;

public interface RenderStageBox extends PostStyleStageBox {

	void setPosition(Position position);
	void setContentSize(Dimension contentSize);
	
	//TODO: This probably should not exist, but it is currently used by a few classes to check info about child boxes
	Dimension getContentSize();
	
	RenderStageBox[] getChildren();
	
}
