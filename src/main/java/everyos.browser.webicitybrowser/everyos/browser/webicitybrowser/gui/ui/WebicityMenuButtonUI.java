package everyos.browser.webicitybrowser.gui.ui;

import com.github.anythingide.lace.basics.color.RGBA16ColorImp;
import com.github.anythingide.lace.basics.component.directive.BackgroundDirective;
import com.github.anythingide.lace.basics.pipeline.paint.canvas.shapes.EllipseShape;
import com.github.anythingide.lace.basics.pipeline.paint.canvas.shapes.RectShape;
import com.github.anythingide.lace.basics.pipeline.paint.canvas.shapes.TextShape;
import com.github.anythingide.lace.basics.pipeline.paint.paint.BasicPaint;
import com.github.anythingide.lace.core.backend.ResourceAllocator;
import com.github.anythingide.lace.core.backend.canvas.CanvasItem;
import com.github.anythingide.lace.core.backend.canvas.Canvas;
import com.github.anythingide.lace.core.color.Color;
import com.github.anythingide.lace.core.component.Component;
import com.github.anythingide.lace.core.component.directive.DirectiveTarget;
import com.github.anythingide.lace.core.laf.ComponentUI;
import com.github.anythingide.lace.core.laf.Content;
import com.github.anythingide.lace.core.pipeline.paint.LacePaint;
import com.github.anythingide.lace.core.pipeline.paint.PaintStageBox;
import com.github.anythingide.lace.core.pipeline.paint.PaintStepContext;
import com.github.anythingide.lace.core.pipeline.render.RenderStageBox;
import com.github.anythingide.lace.core.pipeline.render.RenderStageContext;
import com.github.anythingide.lace.core.pipeline.render.RenderStepContext;
import com.github.anythingide.lace.core.shape.Size;
import com.github.anythingide.lace.core.shape.font.Font;
import com.github.anythingide.lace.imputils.shape.PositionImp;
import com.github.anythingide.lace.imputils.shape.RectangleImp;
import com.github.anythingide.lace.imputils.shape.font.FontUtil;
import com.github.anythingide.lace.laf.simple.content.SimpleComponentContent;
import com.github.anythingide.lace.laf.simple.ui.SimpleComponentUI;

import everyos.browser.webicitybrowser.gui.Styling;

public class WebicityMenuButtonUI extends SimpleComponentUI {

	public WebicityMenuButtonUI(Component component, ComponentUI parentUI) {
		super(component, parentUI);
	}
	
	@Override
	protected Content createContent() {
		return new WebicityMenuButtonContent(this);
	}

}

class WebicityMenuButtonContent extends SimpleComponentContent {
	
	private Font font;

	public WebicityMenuButtonContent(DirectiveTarget directiveProvider) {
		super(directiveProvider);
	}
	
	@Override
	public Size render(RenderStageBox box, RenderStepContext stepContext) {
		this.font = createFont(stepContext);
		
		return super.render(box, stepContext);
	}

	@Override
	public void paint(PaintStageBox box, PaintStepContext paintStepContext) {
		paintBackground(paintStepContext.getCanvas(), box.getSize());
		paintForeground(paintStepContext.getCanvas(), box.getSize());
	}

	private Font createFont(RenderStepContext stepContext) {
		RenderStageContext globalContext = stepContext.getGlobalContext();
		ResourceAllocator resourceAllocator = globalContext.getResourceAllocator();
		Font font = resourceAllocator.getFontByName("Open Sans"); //TODO: Make this more flexible
		
		return font;
	}
	
	private void paintBackground(Canvas canvas, Size size) {
		//TODO: Directives should probably return an Optional
		Color backgroundColor =
			getDirectives().getResolvedDirective(BackgroundDirective.class).map(d -> d.getColor())
			.orElse(RGBA16ColorImp.TRANSPARENT);
		
		LacePaint paint = new BasicPaint()
			.setColor(backgroundColor);
		
		CanvasItem shape = new RectShape()
			.setBounds(RectangleImp.of(0, 0, size.getWidth() - Styling.BUTTON_WIDTH/2, size.getHeight()));
		canvas.draw(shape, paint);
		
		shape = new RectShape()
			.setBounds(RectangleImp.of(0, 0, size.getWidth(), size.getHeight()/2));
		canvas.draw(shape, paint);
		
		shape = new EllipseShape()
			.setBounds(RectangleImp.of(size.getWidth() - Styling.BUTTON_WIDTH, 0, Styling.BUTTON_WIDTH, size.getHeight()));
		canvas.draw(shape, paint);
	}
	
	private void paintForeground(Canvas canvas, Size size) {
		String buttonText = Styling.PRODUCT_NAME;
		
		LacePaint paint = new BasicPaint()
			.setColor(RGBA16ColorImp.WHITE);
		
		float textPosY = size.getHeight()/2 - font.getFontMetrics().getAscent()/2;
		float textPosX = size.getWidth()/2 - FontUtil.getTotalWidth(buttonText, font)/2;
		
		CanvasItem shape = new TextShape()
			.setText(buttonText)
			.setFont(font)
			.setPosition(new PositionImp(textPosX, textPosY));
		canvas.draw(shape, paint);
	}
	
}