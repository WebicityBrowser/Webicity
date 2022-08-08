package everyos.browser.webicity.lacewebextensions.basics.layout.flow.imp.inline;

import com.github.webicity.lace.core.pipeline.composite.CompositeStageBox;
import com.github.webicity.lace.core.pipeline.paint.PaintStepContext;
import com.github.webicity.lace.core.pipeline.render.RenderStageBox;
import com.github.webicity.lace.core.pipeline.render.RenderStepContext;
import com.github.webicity.lace.core.shape.Size;
import com.github.webicity.lace.imputils.shape.SizeImp;

import everyos.browser.webicity.lacewebextensions.basics.layout.flow.imp.FlowLayoutContext;

public class InlineContext implements FlowLayoutContext {

	private final InlineContextRenderStage renderStage = new InlineContextRenderStage();
	
	@Override
	public Size render(RenderStageBox[] children, RenderStepContext stepContext) {
		return renderStage.render(children, stepContext);
	}
	
	@Override
	public void composite(CompositeStageBox box) {
		// TODO Auto-generated method stub

	}

	@Override
	public void paint(PaintStepContext stepContext) {
		// TODO Auto-generated method stub

	}

}
