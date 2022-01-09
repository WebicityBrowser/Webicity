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
import everyos.browser.webicity.webribbon.gui.box.stage.MultiBox;
import everyos.browser.webicity.webribbon.gui.box.stage.PaintStageBox;
import everyos.browser.webicity.webribbon.gui.box.stage.RenderStageBox;
import everyos.browser.webicity.webribbon.ui.webui.display.inner.flow.layout.FlowLayout;
import everyos.browser.webicity.webribbon.ui.webui.helper.BoxUtil;
import everyos.engine.ribbon.core.graphics.paintfill.Color;
import everyos.engine.ribbon.core.rendering.RendererData;
import everyos.engine.ribbon.core.shape.Position;
import everyos.engine.ribbon.core.shape.Rectangle;
import everyos.engine.ribbon.ui.simple.shape.SizePosGroup;

public class FlowInlineContext implements FlowContext {
	
	private List<FlowLineBox> lineBoxes;
	
	@Override
	public void render(RenderStageBox box, RendererData rd, SizePosGroup sizepos, WebRenderContext context) {
		lineBoxes = createLineBoxes(box, rd, sizepos, context);
		adjustLineBoxDimensions(lineBoxes, sizepos);
	}

	@Override
	public void paint(PaintStageBox box, RendererData rd, Rectangle viewport, WebPaintContext context) {	
		ApplicablePropertyMap properties = box.getProperties();
		Color backgroundColor = ((BackgroundColorProperty) properties.getPropertyByName(PropertyName.BACKGROUND_COLOR)).getComputedColor();
		Color foregroundColor = ((ColorProperty) properties.getPropertyByName(PropertyName.COLOR)).getComputedColor();
		
		for (FlowLineBox lineBox: lineBoxes) {
			for (MultiBox child: lineBox.getBoxes()) {			
				rd.getState().setBackground(backgroundColor);
				rd.useBackground();
				context.getRenderer().drawFilledRect(rd,
					child.getPosition().getX(), child.getPosition().getY(),
					child.getContentSize().getWidth(), child.getContentSize().getHeight());
				
				RendererData rd2 = rd.getSubcontext(
					child.getPosition().getX(), child.getPosition().getY(),
					child.getContentSize().getWidth(), child.getContentSize().getHeight());
				
				rd.getState().setForeground(foregroundColor);
				
				FlowLayout.paint(child, rd2, viewport, context);
			}
		}
	}
	


	@Override
	public MultiBox[] split(MultiBox box, RendererData rd, int width, WebRenderContext context) {
		MultiBox[] children = box.getChildren();
		if (children.length == 0) {
			return BoxUtil.keepWholeSplitWithContent(box, this);
		}
		
		List<MultiBox> line = new ArrayList<>();
		List<MultiBox> afterLine = new ArrayList<>(List.of(children));
		
		int widthLeft = width;
		
		for (MultiBox child: children) {
			if (widthLeft == 0) {
				break;
			}
			renderBox(child, rd, context);
			int widthLeftAfterChild = widthLeft - child.getContentSize().getWidth();
			if (widthLeftAfterChild < 0) {
				afterLine.remove(child);
				MultiBox[] split = child.getContent().split(child, rd, widthLeft, context);
				if (split[0] != null) {
					renderBox(split[0], rd, context);
					line.add(split[0]);
				}
				if (split[1] != null) {
					afterLine.add(0, split[1]);
				}
				break;
			} else {
				line.add(child);
				afterLine.remove(child);
				widthLeft = widthLeftAfterChild;
			}
		}
		
		// Add remaining boxes to not line
		// Convert line and not line to boxes, and return them
		if (line.size() == 0) {
			renderBox(afterLine.get(0), rd, context);
			line.add(afterLine.get(0));
			afterLine.remove(0);
		}
		
		return new MultiBox[] {
			createBoxFor(line, box),
			createBoxFor(afterLine, box)
		};
	}
	
	private MultiBox createBoxFor(List<MultiBox> children, MultiBox original) {
		if (children.isEmpty()) {
			return null;
		}
		
		MultiBox box = original.duplicate();
		box.setChildren(children);
		box.setContent(this);
		
		return box;
	}

	private void renderBox(MultiBox box, RendererData rd, WebRenderContext context) {
		SizePosGroup spg = new SizePosGroup(0, 0, 0, 0, -1, -1);
		FlowLayout.render(box, rd, spg, context);
		box.setPosition(new Position(0, 0));
		box.setContentSize(spg.getSize());
	}
	
	private List<FlowLineBox> createLineBoxes(RenderStageBox box, RendererData rd, SizePosGroup sizepos, WebRenderContext context) {
		List<FlowLineBox> lineBoxes = new ArrayList<>();
		
		for (Box child: box.getChildren()) {
			MultiBox next = (MultiBox) child;
			while (next != null) {
				FlowLineBox currentFLBox = new FlowLineBox(sizepos.getMaxSize().getWidth());
				lineBoxes.add(currentFLBox);
				next = currentFLBox.addBox(rd, next, context);
			}
		}
		
		return lineBoxes;
	}
	
	private void adjustLineBoxDimensions(List<FlowLineBox> lineBoxes, SizePosGroup sizepos) {
		int rowY = sizepos.getCurrentPointer().getY();
		
		for (int i = 0; i < lineBoxes.size(); i++) {
			if (i != 0) {
				rowY += lineBoxes.get(i - 1).getHeight();
			}
			
			FlowLineBox currentFLBox = lineBoxes.get(i);
			for (MultiBox next: currentFLBox.getBoxes()) {
				Position prePos = next.getPosition();
				next.setPosition(new Position(prePos.getX(), rowY + currentFLBox.getBaselineY() + prePos.getY()));
				sizepos.move(next.getContentSize().getWidth(), true);
			}
			sizepos.setMinLineHeight(currentFLBox.getHeight());
			sizepos.nextLine();
		}
	}

}
