package everyos.browser.webicity.webribbon.ui.webui.ui.content;

import everyos.browser.spec.javadom.intf.inf.ActivationBehavior;
import everyos.browser.webicity.webribbon.core.component.WebComponent;
import everyos.browser.webicity.webribbon.gui.Content;
import everyos.browser.webicity.webribbon.gui.WebPaintContext;
import everyos.browser.webicity.webribbon.gui.WebRenderContext;
import everyos.browser.webicity.webribbon.gui.box.stage.MultiBox;
import everyos.browser.webicity.webribbon.gui.box.stage.PaintStageBox;
import everyos.browser.webicity.webribbon.gui.box.stage.RenderStageBox;
import everyos.engine.ribbon.core.input.mouse.MouseEvent;
import everyos.engine.ribbon.core.rendering.RendererData;
import everyos.engine.ribbon.core.shape.Rectangle;
import everyos.engine.ribbon.ui.simple.shape.SizePosGroup;

//TODO: Should this class be integrated into something else?
public class ActivationBehaviorContent implements Content {

	private Content innerContent;
	private WebComponent component;

	public ActivationBehaviorContent(Content innerContent, WebComponent component) {
		this.innerContent = innerContent;
		this.component = component;
	}

	@Override
	public void render(RenderStageBox box, RendererData rd, SizePosGroup sizepos, WebRenderContext context) {
		innerContent.render(box, rd, sizepos, context);
	}

	@Override
	public void paint(PaintStageBox box, RendererData rd, Rectangle viewport, WebPaintContext context) {
		ActivationBehavior behavior = component.getNode().getActivationBehavior();
		if (behavior != null) {
			context.getRenderer().paintMouseListener(rd, component, 0, 0,
				box.getContentSize().getWidth(), box.getContentSize().getHeight(), e->{
					if (
						e.getAction() == MouseEvent.PRESS &&
						e.getButton() == MouseEvent.LEFT_BUTTON &&
						!e.isExternal()) {
						
						behavior.execute(context.getActivationBehaviorContext());
					}
				});
		}
		
		innerContent.paint(box, rd, viewport, context);
	}

	@Override
	public MultiBox[] split(MultiBox box, RendererData rd, int width, WebRenderContext context) {
		MultiBox[] split = innerContent.split(box, rd, width, context);
		return new MultiBox[] {
			createContentWrapper(split[0], component),
			createContentWrapper(split[1], component)
		};
	}

	private MultiBox createContentWrapper(MultiBox box, WebComponent component) {
		if (box == null) {
			return null;
		}
		
		MultiBox cloned = box.duplicate();
		cloned.setContent(new ActivationBehaviorContent(box.getContent(), component));

		return cloned;
	}
	
}
