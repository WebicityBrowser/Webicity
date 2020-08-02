package everyos.engine.ribbon.ui.simple;

import everyos.engine.ribbon.core.component.Component;
import everyos.engine.ribbon.core.ui.ComponentUI;
import everyos.engine.ribbon.core.ui.UIManager;
import everyos.engine.ribbon.renderer.guirenderer.GUIComponentUI;
import everyos.engine.ribbon.renderer.guirenderer.GUIRenderer;
import everyos.engine.ribbon.renderer.guirenderer.shape.SizePosGroup;

public class SimpleBreakComponentUI extends SimpleComponentUI {
	public SimpleBreakComponentUI() {}
	public SimpleBreakComponentUI(Component c, GUIComponentUI parent) {
		super(c, parent);
	}
	@Override public ComponentUI create(Component c, ComponentUI parent) {
		return new SimpleBreakComponentUI(c, (GUIComponentUI) parent);
	};
	@Override protected void renderUI(GUIRenderer r, SizePosGroup sizepos, UIManager<GUIComponentUI> uimgr) {
		sizepos.nextLine();
		sizepos.normalize();
		super.renderUI(r, sizepos, uimgr);
	}
}
