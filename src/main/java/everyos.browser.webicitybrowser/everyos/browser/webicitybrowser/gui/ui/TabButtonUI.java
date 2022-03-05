package everyos.browser.webicitybrowser.gui.ui;

import java.util.function.Supplier;

import com.github.anythingide.lace.basics.color.RGBA16ColorImp;
import com.github.anythingide.lace.basics.component.directive.BackgroundDirective;
import com.github.anythingide.lace.basics.pipeline.paint.canvas.shapes.EllipseShape;
import com.github.anythingide.lace.basics.pipeline.paint.canvas.shapes.RectShape;
import com.github.anythingide.lace.basics.pipeline.paint.canvas.shapes.TextShape;
import com.github.anythingide.lace.basics.pipeline.paint.paint.BasicPaint;
import com.github.anythingide.lace.core.backend.ResourceAllocator;
import com.github.anythingide.lace.core.backend.canvas.CanvasItem;
import com.github.anythingide.lace.core.backend.canvas.LaceCanvas;
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
import everyos.browser.webicitybrowser.gui.component.TabButton;

public class TabButtonUI extends SimpleComponentUI {

	public TabButtonUI(Component component, ComponentUI parentUI) {
		super(component, parentUI);
	}
	
	@Override
	protected Content createContent() {
		return new TabButtonContent(this, ()->getComponent().<TabButton>casted().getTitle());
	}

}

class TabButtonContent extends SimpleComponentContent {
	
	private static final float X_BUTTON_WIDTH = 16;
	
	private final Supplier<String> titleProvider;
	
	private Font font;
	private String title;

	public TabButtonContent(DirectiveTarget directiveProvider, Supplier<String> titleProvider) {
		super(directiveProvider);
		
		this.titleProvider = titleProvider;
	}
	
	@Override
	public Size render(RenderStageBox box, RenderStepContext stepContext) {
		this.font = createFont(stepContext);
		this.title = titleProvider.get();
		
		return super.render(box, stepContext);
	}

	@Override
	public void paint(PaintStageBox box, PaintStepContext paintStepContext) {
		paintBackground(paintStepContext.getCanvas(), box.getSize());
		paintForeground(paintStepContext.getCanvas(), box.getSize());
		paintXButton(paintStepContext.getCanvas(), box.getSize());
	}
	
	private Font createFont(RenderStepContext stepContext) {
		RenderStageContext globalContext = stepContext.getGlobalContext();
		ResourceAllocator resourceAllocator = globalContext.getResourceAllocator();
		Font font = resourceAllocator.getFontByName("Open Sans"); //TODO: Make this more flexible
		
		return font;
	}
	
	private void paintBackground(LaceCanvas canvas, Size size) {
		//TODO: Directives should probably return an Optional
		Color backgroundColor =
			getDirectives().getResolvedDirective(BackgroundDirective.class).map(d -> d.getColor())
			.orElse(RGBA16ColorImp.TRANSPARENT);
		
		float widthBeforeSpacing = size.getWidth() - Styling.ELEMENT_PADDING;
		
		LacePaint paint = new BasicPaint()
				.setColor(backgroundColor);
		
		CanvasItem shape = new RectShape()
				.setBounds(RectangleImp.of(Styling.BUTTON_WIDTH/2, 0, widthBeforeSpacing - Styling.BUTTON_WIDTH, size.getHeight()));
			canvas.draw(shape, paint);
		
		shape = new RectShape()
			.setBounds(RectangleImp.of(0, 0, widthBeforeSpacing, size.getHeight()/2));
		canvas.draw(shape, paint);
		
		shape = new EllipseShape()
			.setBounds(RectangleImp.of(0, 0, Styling.BUTTON_WIDTH, size.getHeight()));
		canvas.draw(shape, paint);
		
		shape = new EllipseShape()
			.setBounds(RectangleImp.of(widthBeforeSpacing - Styling.BUTTON_WIDTH, 0, Styling.BUTTON_WIDTH, size.getHeight()));
		canvas.draw(shape, paint);
	}
	
	private void paintForeground(LaceCanvas canvas, Size size) {
		LacePaint paint = new BasicPaint()
			.setColor(RGBA16ColorImp.WHITE);
		
		float xOffset = X_BUTTON_WIDTH - Styling.ELEMENT_PADDING*2;
		float widthBeforeSpacing = size.getWidth() - Styling.ELEMENT_PADDING;
		float usableWidth = widthBeforeSpacing - xOffset;
		
		float textPosY = size.getHeight()/2 - font.getFontMetrics().getAscent()/2;
		float textPosX = xOffset + usableWidth/2 - FontUtil.getTotalWidth(title, font)/2;
		
		CanvasItem shape = new TextShape()
			.setText(title)
			.setFont(font)
			.setPosition(new PositionImp(textPosX, textPosY));
		canvas.draw(shape, paint);
	}
	
	private void paintXButton(LaceCanvas canvas, Size size) {
		LacePaint paint = new BasicPaint()
			.setColor(RGBA16ColorImp.LIGHT_GRAY);
		
		CanvasItem shape = new EllipseShape()
			.setBounds(RectangleImp.of(Styling.ELEMENT_PADDING, size.getHeight()/2 - X_BUTTON_WIDTH/2,
				X_BUTTON_WIDTH, X_BUTTON_WIDTH));	
		canvas.draw(shape, paint);
	}
	
}