package everyos.browser.webicity.lacewebextensions.basics.layout.flow;

import com.github.webicity.lace.core.laf.ComponentUI;
import com.github.webicity.lace.core.layout.LayoutManagerUI;
import com.github.webicity.lace.core.pipeline.box.BoxStageBox;
import com.github.webicity.lace.core.pipeline.box.BoxStageContext;
import com.github.webicity.lace.core.pipeline.composite.CompositeStageBox;
import com.github.webicity.lace.core.pipeline.paint.PaintStepContext;
import com.github.webicity.lace.core.pipeline.render.RenderStageBox;
import com.github.webicity.lace.core.pipeline.render.RenderStepContext;
import com.github.webicity.lace.core.shape.Size;

import everyos.browser.webicity.lacewebextensions.basics.layout.flow.imp.FlowLayoutBoxStage;
import everyos.browser.webicity.lacewebextensions.basics.layout.flow.imp.FlowLayoutContext;
import everyos.browser.webicity.lacewebextensions.basics.layout.flow.imp.block.BlockContext;
import everyos.browser.webicity.lacewebextensions.basics.layout.flow.imp.inline.InlineContext;
import everyos.browser.webicity.lacewebextensions.core.pipeline.box.BlockLevelIdentifier;
import everyos.browser.webicity.lacewebextensions.core.pipeline.box.InlineLevelIdentifier;

public class FlowLayoutManagerUI implements LayoutManagerUI {
	
	private final FlowLayoutBoxStage boxStage;
	
	private FlowLayoutContext context;

	public FlowLayoutManagerUI(ComponentUI parentComponentUI) {
		this.boxStage = new FlowLayoutBoxStage(parentComponentUI);
	}

	@Override
	public void box(BoxStageBox parent, BoxStageContext context) {
		boxStage.box(parent, context);
	}

	@Override
	public Size render(RenderStageBox[] children, RenderStepContext stepContext) {
		this.context = computeContext(children);
		return context.render(children, stepContext);
	}
	
	@Override
	public void composite(CompositeStageBox box) {
		context.composite(box);
	}

	@Override
	public void paint(PaintStepContext stepContext) {
		context.paint(stepContext);
	}
	
	private FlowLayoutContext computeContext(RenderStageBox[] children) {
		if (children.length == 0) {
			return new InlineContext();
		} else if (children[0].getIdentifier() instanceof BlockLevelIdentifier) {
			return new BlockContext();
		} else if (children[0].getIdentifier() instanceof InlineLevelIdentifier) {
			return new InlineContext();
		} else {
			return new InlineContext();
		}
	}

}
