package everyos.browser.webicity.webribbon.gui.box.stage;

import everyos.engine.ribbon.core.shape.Dimension;
import everyos.engine.ribbon.core.shape.Position;

public interface PaintStageBox extends PostStyleStageBox {

	Position getPosition();
	Dimension getContentSize();
	
	PaintStageBox[] getChildren();
	
}
