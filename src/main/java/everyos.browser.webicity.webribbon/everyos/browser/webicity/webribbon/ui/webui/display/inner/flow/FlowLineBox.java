package everyos.browser.webicity.webribbon.ui.webui.display.inner.flow;

import java.util.ArrayList;
import java.util.List;

import everyos.browser.webicity.webribbon.gui.WebRenderContext;
import everyos.browser.webicity.webribbon.gui.box.stage.MultiBox;
import everyos.browser.webicity.webribbon.ui.webui.helper.BoxUtil;
import everyos.engine.ribbon.core.rendering.RendererData;
import everyos.engine.ribbon.core.shape.Position;

public class FlowLineBox {

	private final int maxWidth;
	//TODO: We should ensure that these are also inline boxes
	private final List<MultiBox> boxes = new ArrayList<>();
	
	private int currentWidth = 0;
	private int heightOverBaseline = 0;
	private int heightUnderBaseline = 0;
	
	public FlowLineBox(int maxWidth) {
		this.maxWidth = maxWidth;
	}
	
	public MultiBox addBox(RendererData rd, MultiBox box, WebRenderContext context) {
		MultiBox keptBox = box;
		MultiBox rtnBox = null;
		
		BoxUtil.renderBox(box, rd, context);
		
		//TODO: Code should still work when I comment this out, but does not always
		if (maxWidth == -1) {
			addRenderedBox(box);
			return null;
		}
		
		int widthLeft = maxWidth - currentWidth;
		if (widthLeft < box.getContentSize().getWidth()) {
			MultiBox[] split = box.getContent().split(box, rd, widthLeft, context);
			keptBox = split[0];
			rtnBox = split[1];
			if (keptBox != null) {
				BoxUtil.renderBox(keptBox, rd, context);
			}
		}
		
		if (keptBox != null) {
			addRenderedBox(keptBox);
		}
		
		if (boxes.size() == 0 && rtnBox != null) {
			BoxUtil.renderBox(rtnBox, rd, context);
			addRenderedBox(rtnBox);
			return null;
		}
		
		return rtnBox;
	}

	private void addRenderedBox(MultiBox box) {
		int height = box.getContentSize().getHeight();
		box.setPosition(new Position(currentWidth, -height));
		currentWidth += box.getContentSize().getWidth();
		if (height > heightOverBaseline) {
			heightOverBaseline = height;
		}
		boxes.add(box);
	}

	public int getBaselineY() {
		return heightOverBaseline;
	}
	
	public int getHeight() {
		return heightUnderBaseline + heightOverBaseline;
	}
	
	public List<MultiBox> getBoxes() {
		return boxes;
	}
	
}
