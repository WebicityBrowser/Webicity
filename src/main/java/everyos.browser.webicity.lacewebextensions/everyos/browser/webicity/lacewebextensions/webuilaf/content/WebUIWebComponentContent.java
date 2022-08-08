package everyos.browser.webicity.lacewebextensions.webuilaf.content;

import com.github.webicity.lace.core.laf.Content;
import com.github.webicity.lace.core.layout.LayoutManagerUI;
import com.github.webicity.lace.core.pipeline.composite.CompositeStageBox;
import com.github.webicity.lace.core.pipeline.event.Event;
import com.github.webicity.lace.core.pipeline.paint.PaintStageBox;
import com.github.webicity.lace.core.pipeline.paint.PaintStepContext;
import com.github.webicity.lace.core.pipeline.render.RenderStageBox;
import com.github.webicity.lace.core.pipeline.render.RenderStepContext;
import com.github.webicity.lace.core.shape.Size;

public class WebUIWebComponentContent implements Content {

	private final LayoutManagerUI layoutManagerUI;
	
	public WebUIWebComponentContent(LayoutManagerUI layoutManagerUI) {
		this.layoutManagerUI = layoutManagerUI;
	}
	
	@Override
	public Size render(RenderStageBox box, RenderStepContext stepContext) {
		return layoutManagerUI.render(box.getChildren(), stepContext);
	}

	@Override
	public void composite(CompositeStageBox box) {
		
	}

	@Override
	public void paint(PaintStageBox box, PaintStepContext stepContext) {
		layoutManagerUI.paint(stepContext);
	}

	@Override
	public boolean handleEvent(Event event) {
		return false;
	}

}
