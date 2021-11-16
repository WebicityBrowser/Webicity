package everyos.browser.webicity.webribbon.ui.webui.display.inner.flow;

import java.util.ArrayList;
import java.util.List;

import everyos.browser.spec.jcss.cssom.ApplicablePropertyMap;
import everyos.browser.webicity.webribbon.gui.WebRenderContext;
import everyos.browser.webicity.webribbon.gui.box.InlineLevelBox;
import everyos.engine.ribbon.core.rendering.RendererData;
import everyos.engine.ribbon.core.shape.Dimension;
import everyos.engine.ribbon.core.shape.Position;

public class FlowLineBox {

	private final int maxWidth;
	private final List<InlineLevelBox> boxes = new ArrayList<>();
	
	private int currentWidth = 0;
	private int heightOverBaseline = 0;
	private int heightUnderBaseline = 0;
	
	public FlowLineBox(int maxWidth) {
		this.maxWidth = maxWidth;
	}
	
	public InlineLevelBox addBox(RendererData rd, InlineLevelBox box, WebRenderContext context) {
		ApplicablePropertyMap properties = box.getProperties();
		InlineLevelBox[] split = box.split(rd, maxWidth - currentWidth, context, currentWidth == 0);
		
		InlineLevelBox firstLine = split[0];
		
		if (firstLine == null) {
			return split[1];
		}
		
		firstLine.setProperties(properties);
		boxes.add(firstLine);
		
		//TODO: Align
		Dimension firstLineSize = firstLine.getFinalSize();
		if (firstLineSize.getHeight() > heightOverBaseline) {
			heightOverBaseline = firstLineSize.getHeight();
		}
		split[0].setFinalPos(new Position(currentWidth, -firstLineSize.getHeight()));
		currentWidth += firstLineSize.getWidth();
		
		if (split[1] != null) {
			split[1].setProperties(properties);
		}
		
		return split[1];
	}
	
	public int getBaselineY() {
		return heightOverBaseline;
	}
	
	public int getHeight() {
		return heightUnderBaseline + heightOverBaseline;
	}
	
	public List<InlineLevelBox> getBoxes() {
		return boxes;
	}
	
}
