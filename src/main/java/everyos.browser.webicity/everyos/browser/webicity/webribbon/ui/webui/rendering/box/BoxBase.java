package everyos.browser.webicity.webribbon.ui.webui.rendering.box;

import everyos.browser.webicity.webribbon.gui.box.Box;
import everyos.engine.ribbon.core.shape.Dimension;
import everyos.engine.ribbon.core.shape.Position;

public abstract class BoxBase implements Box {

	private Position finalPos;
	private Dimension finalSize;
	
	//
	@Override
	public Position getFinalPos() {
		return this.finalPos;
	}
	
	@Override
	public void setFinalPos(Position finalPos) {
		this.finalPos = finalPos;
	}
	
	@Override
	public Dimension getFinalSize() {
		return finalSize;
	}

	@Override
	public void setFinalSize(Dimension finalSize) {
		this.finalSize = finalSize;
	}
	
}
