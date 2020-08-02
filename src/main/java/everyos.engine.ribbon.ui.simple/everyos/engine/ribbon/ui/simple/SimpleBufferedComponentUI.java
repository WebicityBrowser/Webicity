	package everyos.engine.ribbon.ui.simple;

import everyos.engine.ribbon.core.component.Component;
import everyos.engine.ribbon.core.ui.ComponentUI;
import everyos.engine.ribbon.core.ui.UIManager;
import everyos.engine.ribbon.renderer.guirenderer.GUIComponentUI;
import everyos.engine.ribbon.renderer.guirenderer.GUIRenderer;
import everyos.engine.ribbon.renderer.guirenderer.shape.SizePosGroup;

public class SimpleBufferedComponentUI extends SimpleBlockComponentUI {
	public SimpleBufferedComponentUI() {}
	public SimpleBufferedComponentUI(Component c, GUIComponentUI parent) {
		super(c, parent);
	}
	public ComponentUI create(Component c, ComponentUI parent) {
		return new SimpleBufferedComponentUI(c, (GUIComponentUI) parent);
	};
	
	@Override public void render(GUIRenderer r, SizePosGroup sizepos, UIManager<GUIComponentUI> uimgr) {
		super.render(r.getBufferedSubcontext(0, 0, 1, 1), sizepos, uimgr);
	}
	
	@Override public void paint(GUIRenderer r) {
		GUIRenderer r2 = r.getBufferedSubcontext(bounds.x, bounds.y, bounds.width, bounds.height);
		//r2.setColor((Color) data.attributes.getOrDefault("bg-color", Color.WHITE));
		r2.drawFilledRect(0, 0, bounds.width, bounds.height);
		for (GUIComponentUI child: getChildren()) {
			child.paint(r2);
		}
		r2.draw();
	}
}
