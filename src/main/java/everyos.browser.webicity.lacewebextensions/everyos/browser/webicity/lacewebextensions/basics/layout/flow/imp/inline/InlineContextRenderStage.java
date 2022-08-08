package everyos.browser.webicity.lacewebextensions.basics.layout.flow.imp.inline;

import com.github.webicity.lace.core.pipeline.render.RenderStageBox;
import com.github.webicity.lace.core.pipeline.render.RenderStepContext;
import com.github.webicity.lace.core.shape.Size;

public class InlineContextRenderStage {

	public Size render(RenderStageBox[] children, RenderStepContext stepContext) {
		return stepContext.getPrecomputedSize();
	}

}
