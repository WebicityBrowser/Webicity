package everyos.browser.webicity.lacewebextensions.basics.layout.flow.imp.block;

import com.github.webicity.lace.core.pipeline.composite.CompositeStageBox;
import com.github.webicity.lace.core.pipeline.paint.PaintStepContext;
import com.github.webicity.lace.core.pipeline.render.RenderStageBox;
import com.github.webicity.lace.core.pipeline.render.RenderStepContext;
import com.github.webicity.lace.core.shape.Size;

import everyos.browser.webicity.lacewebextensions.basics.layout.flow.imp.FlowLayoutContext;

public class BlockContext implements FlowLayoutContext {

	private final BlockContextRenderStage renderStage;
	
	public BlockContext() {
		this.renderStage = new BlockContextRenderStage();
	}

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
