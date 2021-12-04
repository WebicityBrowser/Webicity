package everyos.browser.webicity.webribbon.ui.webui.display.inner.flow;

import java.util.ArrayList;
import java.util.List;

import everyos.browser.spec.jcss.cssom.ApplicablePropertyMap;
import everyos.browser.spec.jcss.cssom.property.PropertyName;
import everyos.browser.spec.jcss.cssom.property.backgroundcolor.BackgroundColorProperty;
import everyos.browser.spec.jcss.cssom.property.color.ColorProperty;
import everyos.browser.webicity.webribbon.gui.WebPaintContext;
import everyos.browser.webicity.webribbon.gui.WebRenderContext;
import everyos.browser.webicity.webribbon.gui.box.Box;
import everyos.browser.webicity.webribbon.gui.box.InlineLevelBox;
import everyos.engine.ribbon.core.graphics.paintfill.Color;
import everyos.engine.ribbon.core.rendering.RendererData;
import everyos.engine.ribbon.core.shape.Position;
import everyos.engine.ribbon.core.shape.Rectangle;
import everyos.engine.ribbon.ui.simple.shape.SizePosGroup;

public class FlowInlineContext implements FlowContext {
	
	private List<FlowLineBox> lineBoxes;
	
	@Override
	public void render(Box box, RendererData rd, SizePosGroup sizepos, WebRenderContext context) {
		lineBoxes = createLineBoxes(box, rd, sizepos, context);
		adjustLineBoxDimensions(lineBoxes, sizepos);
	}

	//TODO: Inline content sometimes being displayed twice?
	@Override
	public void paint(Box box, RendererData rd, Rectangle viewport, WebPaintContext context) {
		
		ApplicablePropertyMap properties = box.getProperties();
		Color backgroundColor = ((BackgroundColorProperty) properties.getPropertyByName(PropertyName.BACKGROUND_COLOR)).getComputedColor();
		Color foregroundColor = ((ColorProperty) properties.getPropertyByName(PropertyName.COLOR)).getComputedColor();
		
		for (FlowLineBox lineBox: lineBoxes) {
			for (Box child: lineBox.getBoxes()) {			
				rd.getState().setBackground(backgroundColor);
				rd.useBackground();
				context.getRenderer().drawFilledRect(rd,
					child.getFinalPos().getX(), child.getFinalPos().getY(),
					child.getFinalSize().getWidth(), child.getFinalSize().getHeight());
				
				RendererData rd2 = rd.getSubcontext(
					child.getFinalPos().getX(), child.getFinalPos().getY(),
					child.getFinalSize().getWidth(), child.getFinalSize().getHeight());
				
				rd.getState().setForeground(foregroundColor);
				
				child.paint(rd2, viewport, context);
			}
		}
	}
	
	private List<FlowLineBox> createLineBoxes(Box box, RendererData rd, SizePosGroup sizepos, WebRenderContext context) {
		List<FlowLineBox> lineBoxes = new ArrayList<>();
		
		FlowLineBox currentFLBox = new FlowLineBox(sizepos.getMaxSize().getWidth());
		lineBoxes.add(currentFLBox);
		for (Box child: box.getChildren()) {
			InlineLevelBox next = (InlineLevelBox) child;
			while (next != null) {
				next = currentFLBox.addBox(rd, next, context);
				if (next != null) {
					currentFLBox = new FlowLineBox(sizepos.getMaxSize().getWidth());
					lineBoxes.add(currentFLBox);
				}
			}
		}
		
		return lineBoxes;
	}
	
	private void adjustLineBoxDimensions(List<FlowLineBox> lineBoxes, SizePosGroup sizepos) {
		int rowY = sizepos.getCurrentPointer().getY();
		
		for (int i = 0; i < lineBoxes.size(); i++) {
			if (i != 0) {
				rowY += lineBoxes.get(i-1).getHeight();
			}
			
			FlowLineBox currentFLBox = lineBoxes.get(i);
			for (InlineLevelBox next: currentFLBox.getBoxes()) {
				Position prePos = next.getFinalPos();
				next.setFinalPos(new Position(prePos.getX(), rowY + currentFLBox.getBaselineY() + prePos.getY()));
				sizepos.move(next.getFinalSize().getWidth(), true);
			}
			sizepos.setMinLineHeight(currentFLBox.getHeight());
			sizepos.nextLine();
		}
	}

}
