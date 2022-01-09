package everyos.browser.webicity.webribbon.ui.webui.helper;

import everyos.browser.webicity.webribbon.gui.Content;
import everyos.browser.webicity.webribbon.gui.WebRenderContext;
import everyos.browser.webicity.webribbon.gui.box.stage.MultiBox;
import everyos.browser.webicity.webribbon.ui.webui.display.inner.flow.layout.FlowLayout;
import everyos.engine.ribbon.core.rendering.RendererData;
import everyos.engine.ribbon.core.shape.Position;
import everyos.engine.ribbon.ui.simple.shape.SizePosGroup;

public final class BoxUtil {
	
	private BoxUtil() {}
	
	public static void renderBox(MultiBox box, RendererData rd, WebRenderContext context) {
		SizePosGroup spg = new SizePosGroup(0, 0, 0, 0, -1, -1);
		FlowLayout.render(box, rd, spg, context);
		box.setPosition(new Position(0, 0));
		box.setContentSize(spg.getSize());
	}
	
	public static MultiBox[] keepWholeSplitWithContent(MultiBox box, Content content) {
		MultiBox cloned = box.duplicate();
		cloned.setContent(content);
		
		return new MultiBox[] {
			null, cloned
		};
	}
	
}
