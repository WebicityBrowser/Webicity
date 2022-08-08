package everyos.browser.webicity.lacewebextensions.basics.layout.common;

import com.github.webicity.lace.core.laf.Content;
import com.github.webicity.lace.core.pipeline.composite.CompositeStageBox;
import com.github.webicity.lace.core.pipeline.event.Event;
import com.github.webicity.lace.core.pipeline.paint.PaintStageBox;
import com.github.webicity.lace.core.pipeline.paint.PaintStepContext;
import com.github.webicity.lace.core.pipeline.render.RenderStageBox;
import com.github.webicity.lace.core.pipeline.render.RenderStepContext;
import com.github.webicity.lace.core.shape.Size;

public final class ContentActionHandler {

	private ContentActionHandler() {}
	
	public static Size render(Content content, RenderStageBox box, RenderStepContext stepContext) {
		return content.render(box, stepContext);
	};
	
	public static void composite(Content content, CompositeStageBox box) {
		content.composite(box);
	};
	
	public static void paint(Content content, PaintStageBox box, PaintStepContext stepContext) {
		content.paint(box, stepContext);
	};
	
	public static boolean handleEvent(Content content, Event event) {
		return content.handleEvent(event);
	};
	
}
