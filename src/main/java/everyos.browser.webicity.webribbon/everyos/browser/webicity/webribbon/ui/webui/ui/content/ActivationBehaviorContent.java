package everyos.browser.webicity.webribbon.ui.webui.ui.content;

import everyos.browser.spec.javadom.intf.inf.ActivationBehavior;
import everyos.browser.webicity.webribbon.core.component.WebComponent;
import everyos.browser.webicity.webribbon.gui.Content;
import everyos.browser.webicity.webribbon.gui.WebPaintContext;
import everyos.browser.webicity.webribbon.gui.WebRenderContext;
import everyos.browser.webicity.webribbon.gui.box.Box;
import everyos.engine.ribbon.core.input.mouse.MouseEvent;
import everyos.engine.ribbon.core.rendering.RendererData;
import everyos.engine.ribbon.core.shape.Rectangle;
import everyos.engine.ribbon.core.shape.SizePosGroup;

public class ActivationBehaviorContent implements Content {

	private Content innerContent;
	private WebComponent component;

	public ActivationBehaviorContent(Content innerContent, WebComponent component) {
		this.innerContent = innerContent;
		this.component = component;
	}

	@Override
	public void render(Box box, RendererData rd, SizePosGroup sizepos, WebRenderContext context) {
		innerContent.render(box, rd, sizepos, context);
	}

	@Override
	public void paint(Box box, RendererData rd, Rectangle viewport, WebPaintContext context) {
		ActivationBehavior behavior = component.getNode().getActivationBehavior();
		if (behavior != null) {
			context.getRenderer().paintMouseListener(rd, component, 0, 0,
				box.getFinalSize().getWidth(), box.getFinalSize().getHeight(), e->{
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
	public Content split() {
		return new ActivationBehaviorContent(innerContent.split(), component);
	}

}
