package everyos.browser.webicity.webribbon.ui.webui.rendering.box;

import everyos.browser.spec.jcss.cssom.ApplicablePropertyMap;
import everyos.browser.webicity.webribbon.gui.Content;
import everyos.browser.webicity.webribbon.gui.box.stage.MultiBox;
import everyos.engine.ribbon.core.shape.Dimension;
import everyos.engine.ribbon.core.shape.Position;

public abstract class MultiBoxBase implements MultiBox {
	
	private Position position;
	private Dimension contentSize;
	private ApplicablePropertyMap properties;
	private Content content;

	// BoxingStageBox
	@Override
	public void integrate(MultiBox[] boxes) {
		for (MultiBox box: boxes) {
			add(box);
		}
	}
	
	@Override
	public void setContent(Content content) {
		this.content = content;
	}


	// PostBoxingStageBox
	@Override
	public Content getContent() {
		return this.content;
	}
	
	// StyleStageBox
	@Override
	public void setProperties(ApplicablePropertyMap properties) {
		this.properties = properties;
	}

	// PostStyleStageBox
	@Override
	public ApplicablePropertyMap getProperties() {
		return this.properties;
	}
	
	// RenderStageBox
	@Override
	public void setPosition(Position position) {
		this.position = position;
	}

	@Override
	public void setContentSize(Dimension contentSize) {
		this.contentSize = contentSize;
	}
	
	// PaintStageBox
	@Override
	public Position getPosition() {
		return this.position;
	}

	@Override
	public Dimension getContentSize() {
		return this.contentSize;
	}
	
}
