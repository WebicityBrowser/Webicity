package everyos.engine.ribbon.ui.simple;

import everyos.engine.ribbon.core.component.Component;
import everyos.engine.ribbon.core.rendering.Renderer;
import everyos.engine.ribbon.core.shape.SizePosGroup;
import everyos.engine.ribbon.core.ui.ComponentUI;
import everyos.engine.ribbon.core.ui.UIManager;

public class SimpleBreakComponentUI extends SimpleComponentUI {
	public SimpleBreakComponentUI(Component c, ComponentUI parent) {
		super(c, parent);
	}
	
	@Override
	protected void renderUI(Renderer r, SizePosGroup sizepos, UIManager uimgr) {
		sizepos.nextLine();
		super.renderUI(r, sizepos, uimgr);
	}
}
