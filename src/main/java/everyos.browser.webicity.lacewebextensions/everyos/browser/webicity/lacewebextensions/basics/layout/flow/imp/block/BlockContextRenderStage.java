package everyos.browser.webicity.lacewebextensions.basics.layout.flow.imp.block;

import com.github.webicity.lace.basics.layout.RenderedBoxData;
import com.github.webicity.lace.core.laf.Content;
import com.github.webicity.lace.core.pipeline.render.RenderStageBox;
import com.github.webicity.lace.core.pipeline.render.RenderStageContext;
import com.github.webicity.lace.core.pipeline.render.RenderStepContext;
import com.github.webicity.lace.core.shape.Position;
import com.github.webicity.lace.core.shape.RelativeDimension;
import com.github.webicity.lace.core.shape.Size;
import com.github.webicity.lace.imputils.shape.PositionImp;
import com.github.webicity.lace.imputils.shape.SizeImp;

import everyos.browser.webicity.lacewebextensions.basics.layout.common.LayoutUtil;

public class BlockContextRenderStage {
	
	private static final float INDETERMINATE = RelativeDimension.INDETERMINATE;
	
	private RenderedBoxData[] renderedBoxes;

	public Size render(RenderStageBox[] children, RenderStepContext stepContext) {
		this.renderedBoxes = renderChildren(children, stepContext);
		
		//TODO: Return actual size if size not pre-computed
		return stepContext.getPrecomputedSize();
	}
	
	public RenderedBoxData[] getRenderedBoxData() {
		return renderedBoxes;
	}

	private RenderedBoxData[] renderChildren(RenderStageBox[] children, RenderStepContext stepContext) {
		RenderedBoxData[] renderedChildren = new RenderedBoxData[children.length];
		
		int yStart = 0;
		for (int i = 0; i < children.length; i++) {
			RenderedBoxData renderedChild = renderChildUIBox(children[i], stepContext, yStart);
			renderedChildren[i] = renderedChild;
			
			if (LayoutUtil.checkBoxIsInFlow(children[i])) {
				yStart += renderedChild.getComputedSize().getHeight();
			}
		}
		
		return renderedChildren;
	}

	private RenderedBoxData renderChildUIBox(RenderStageBox child, RenderStepContext stepContext, int yPos) {
		Position precomputedChildPosition = new PositionImp(0, yPos);
		Size precomputedChildSize = new SizeImp(INDETERMINATE, INDETERMINATE);
		
		Content childContent = child.getContent();
		RenderStepContext childStepContext = createChildRenderStepContext(child, stepContext, precomputedChildSize);
		Size childRenderedSize = childContent.render(child, childStepContext);
		
		Size finalChildSize = childRenderedSize;
		Position finalChildPosition = precomputedChildPosition;
		
		RenderedBoxData renderedBoxData = new RenderedBoxData(
			childContent, finalChildPosition, finalChildSize, child.getIdentifier());
			
			return renderedBoxData;
	}

	private RenderStepContext createChildRenderStepContext(RenderStageBox child, RenderStepContext parentStepContext, Size precomputedSize) {
		return new RenderStepContext() {
			@Override
			public Size getPrecomputedSize() {
				return precomputedSize;
			}

			@Override
			public Size getParentSize() {
				return parentStepContext.getPrecomputedSize();
			}

			@Override
			public RenderStageContext getGlobalContext() {
				return parentStepContext.getGlobalContext();
			}
		};
	}
	
}
