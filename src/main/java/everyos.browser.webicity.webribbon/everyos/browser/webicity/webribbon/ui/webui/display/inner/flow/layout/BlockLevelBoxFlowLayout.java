package everyos.browser.webicity.webribbon.ui.webui.display.inner.flow.layout;

import everyos.browser.spec.jcss.cssom.ApplicablePropertyMap;
import everyos.browser.spec.jcss.cssom.property.PropertyName;
import everyos.browser.spec.jcss.cssom.property.backgroundcolor.BackgroundColorProperty;
import everyos.browser.spec.jcss.cssom.property.height.HeightProperty;
import everyos.browser.spec.jcss.cssom.property.width.WidthProperty;
import everyos.browser.spec.jcss.cssvalue.common.Sizing;
import everyos.browser.webicity.webribbon.gui.WebPaintContext;
import everyos.browser.webicity.webribbon.gui.WebRenderContext;
import everyos.browser.webicity.webribbon.gui.box.stage.PaintStageBox;
import everyos.browser.webicity.webribbon.gui.box.stage.RenderStageBox;
import everyos.engine.ribbon.core.graphics.paintfill.Color;
import everyos.engine.ribbon.core.rendering.RendererData;
import everyos.engine.ribbon.core.shape.Dimension;
import everyos.engine.ribbon.core.shape.Position;
import everyos.engine.ribbon.core.shape.Rectangle;
import everyos.engine.ribbon.ui.simple.helper.RectangleBuilder;
import everyos.engine.ribbon.ui.simple.shape.SizePosGroup;

public final class BlockLevelBoxFlowLayout {

	private BlockLevelBoxFlowLayout() {}

	public static void render(RenderStageBox box, RendererData rd, SizePosGroup sizepos, WebRenderContext context) {
		box.getContent().render(box, rd, sizepos, context);
		calculateSize(box, sizepos);
	}

	public static void paint(PaintStageBox box, RendererData rd, Rectangle viewport, WebPaintContext context) {
		applyBackgroundColorProperty(box, rd, viewport, context);
		box.getContent().paint(box, rd, intersect(box, viewport), context);
	}
	
	private static void calculateSize(RenderStageBox box, SizePosGroup sizepos) {
		ApplicablePropertyMap properties = box.getProperties();
		
		Sizing widthSizing = ((WidthProperty) properties.getPropertyByName(PropertyName.WIDTH)).getComputedWidthSizing();
		int width = widthSizing.calculateForParent(sizepos.getSize().getWidth());
		if (width == Sizing.AUTO_SIZING) {
			width = sizepos.getSize().getWidth();
		}
		
		//TODO: Height percentages should use the actual parent height...
		Sizing heightSizing = ((HeightProperty) properties.getPropertyByName(PropertyName.HEIGHT)).getComputedHeightSizing();
		int height = heightSizing.calculateForParent(sizepos.getSize().getHeight());
		if (height == Sizing.AUTO_SIZING) {
			height = sizepos.getSize().getHeight();
		}
		
		box.setContentSize(new Dimension(width, height));
		//box.setContentSize(sizepos.getSize());
	}
	
	private static Rectangle intersect(PaintStageBox box, Rectangle viewport) {
		Position pos = box.getPosition();
		Dimension size = box.getContentSize();
		
		//AABB based culling
		RectangleBuilder vpBuilder = new RectangleBuilder(
			pos.getX(), pos.getY(),
			size.getWidth(), size.getHeight());
		
		// Perform an intersect
		Rectangle intersected = viewport; //vpBuilder.build().intersect(viewport);
		vpBuilder.setWidth(intersected.getWidth());
		vpBuilder.setHeight(intersected.getHeight());
				
		// Origin should be 0, 0
		vpBuilder.setX(intersected.getX() - pos.getX());
		vpBuilder.setY(intersected.getY() - pos.getY());
		
		// And scroll
		vpBuilder.setY(vpBuilder.getY());
		
		return vpBuilder.build();
	}
	
	private static void applyBackgroundColorProperty(PaintStageBox box, RendererData rd, Rectangle viewport, WebPaintContext context) {
		Color backgroundColor = ((BackgroundColorProperty) box.getProperties().getPropertyByName(PropertyName.BACKGROUND_COLOR)).getComputedColor();
		
		rd.getState().setBackground(backgroundColor);
		rd.useBackground();
		context.getRenderer().drawFilledRect(rd,
			0, 0, 
			box.getContentSize().getWidth(), box.getContentSize().getHeight());
	}
	
}
