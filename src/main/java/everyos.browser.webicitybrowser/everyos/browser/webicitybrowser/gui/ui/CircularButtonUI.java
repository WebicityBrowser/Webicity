package everyos.browser.webicitybrowser.gui.ui;

import com.github.webicity.lace.basics.color.RGBA16ColorImp;
import com.github.webicity.lace.basics.component.directive.BackgroundDirective;
import com.github.webicity.lace.basics.pipeline.paint.canvas.shapes.EllipseShape;
import com.github.webicity.lace.basics.pipeline.paint.canvas.shapes.ImageBuffer;
import com.github.webicity.lace.basics.pipeline.paint.canvas.shapes.ImageShape;
import com.github.webicity.lace.basics.pipeline.paint.paint.BasicPaint;
import com.github.webicity.lace.core.backend.canvas.Canvas;
import com.github.webicity.lace.core.backend.canvas.CanvasItem;
import com.github.webicity.lace.core.color.Color;
import com.github.webicity.lace.core.component.Component;
import com.github.webicity.lace.core.component.directive.DirectiveTarget;
import com.github.webicity.lace.core.laf.ComponentUI;
import com.github.webicity.lace.core.laf.Content;
import com.github.webicity.lace.core.pipeline.paint.LacePaint;
import com.github.webicity.lace.core.pipeline.paint.PaintStageBox;
import com.github.webicity.lace.core.pipeline.paint.PaintStepContext;
import com.github.webicity.lace.core.shape.Size;
import com.github.webicity.lace.imputils.shape.RectangleImp;
import com.github.webicity.lace.laf.simple.content.SimpleComponentContent;
import com.github.webicity.lace.laf.simple.ui.SimpleComponentUI;

import everyos.browser.webicitybrowser.gui.component.CircularButton;

public class CircularButtonUI extends SimpleComponentUI {

	public CircularButtonUI(Component component, ComponentUI parentUI) {
		super(component, parentUI);
	}
	
	@Override
	protected Content createContent() {
		ImageBuffer imageBuffer = getComponent().<CircularButton>casted()
			.getImageBuffer();
		return new CircularButtonContent(this, imageBuffer);
	}

}

class CircularButtonContent extends SimpleComponentContent {
	
	private final ImageBuffer imageBuffer;

	public CircularButtonContent(DirectiveTarget directiveProvider, ImageBuffer imageBuffer) {
		super(directiveProvider);
		this.imageBuffer = imageBuffer;
	}

	@Override
	public void paint(PaintStageBox box, PaintStepContext paintStepContext) {
		paintBackground(paintStepContext.getCanvas(), box.getSize());
		paintImage(paintStepContext.getCanvas(), box.getSize(), imageBuffer);
	}

	private void paintBackground(Canvas canvas, Size size) {
		//TODO: Directives should probably return an Optional
		Color backgroundColor =
			getDirectives().getResolvedDirective(BackgroundDirective.class).map(d -> d.getColor())
			.orElse(RGBA16ColorImp.TRANSPARENT);
		
		LacePaint paint = new BasicPaint()
				.setColor(backgroundColor);
		
		CanvasItem shape = new EllipseShape()
			.setBounds(RectangleImp.of(0, 0, size.getWidth(), size.getHeight()));
		canvas.draw(shape, paint);
	}
	
	private void paintImage(Canvas canvas, Size size, ImageBuffer buffer) {
		CanvasItem shape = new ImageShape(buffer)
			.setBounds(RectangleImp.of(0, 0, size.getWidth(), size.getHeight()));
		canvas.draw(shape, new BasicPaint());
	}
	
}