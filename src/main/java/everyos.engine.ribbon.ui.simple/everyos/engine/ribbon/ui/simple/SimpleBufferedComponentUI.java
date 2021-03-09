	package everyos.engine.ribbon.ui.simple;

import everyos.engine.ribbon.core.component.Component;
import everyos.engine.ribbon.core.rendering.Renderer;
import everyos.engine.ribbon.core.ui.ComponentUI;
import everyos.engine.ribbon.core.ui.UIManager;
import everyos.engine.ribbon.renderer.guirenderer.shape.SizePosGroup;

public class SimpleBufferedComponentUI extends SimpleBlockComponentUI {
	public SimpleBufferedComponentUI(Component c, ComponentUI parent) {
		super(c, parent);
	}
	public ComponentUI create(Component c, ComponentUI parent) {
		return new SimpleBufferedComponentUI(c, parent);
	};
	
	@Override public void render(Renderer r, SizePosGroup sizepos, UIManager uimgr) {
		super.render(r.getBufferedSubcontext(0, 0, 1, 1), sizepos, uimgr);
	}
	
	@Override public void paint(Renderer r) {
		Renderer r2 = r.getBufferedSubcontext(bounds.x, bounds.y, bounds.width, bounds.height);
		//r2.setColor((Color) data.attributes.getOrDefault("bg-color", Color.WHITE));
		r2.drawFilledRect(0, 0, bounds.width, bounds.height);
		for (ComponentUI child: getChildren()) {
			child.paint(r2);
		}
		r2.draw();
	}
}
