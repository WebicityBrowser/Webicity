package everyos.browser.webicity.lacewebextensions.basics.layout.flow.imp;

import com.github.webicity.lace.core.pipeline.composite.CompositeStageBox;
import com.github.webicity.lace.core.pipeline.paint.PaintStepContext;
import com.github.webicity.lace.core.pipeline.render.RenderStageBox;
import com.github.webicity.lace.core.pipeline.render.RenderStepContext;
import com.github.webicity.lace.core.shape.Size;

public interface FlowLayoutContext {

	Size render(RenderStageBox[] children, RenderStepContext stepContext);

	void composite(CompositeStageBox box);
	
	void paint(PaintStepContext stepContext);

}
