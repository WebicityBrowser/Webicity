package everyos.browser.webicity.webribbon.ui.webui.rendering.box;

import everyos.browser.spec.jcss.cssom.ApplicablePropertyMap;
import everyos.browser.webicity.webribbon.gui.box.Box;
import everyos.engine.ribbon.core.shape.Dimension;
import everyos.engine.ribbon.core.shape.Position;

public abstract class BoxBase implements Box {

	private Position finalPos;
	private Dimension finalSize;
	private ApplicablePropertyMap properties;
	
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
	
	@Override
	public void setProperties(ApplicablePropertyMap properties) {
		this.properties = properties;
	}
	
	@Override
	public ApplicablePropertyMap getProperties() {
		return this.properties;
	}
	
}
