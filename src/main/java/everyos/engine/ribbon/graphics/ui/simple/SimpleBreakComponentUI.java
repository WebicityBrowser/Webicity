package everyos.engine.ribbon.graphics.ui.simple;

import everyos.engine.ribbon.graphics.GUIRenderer;
import everyos.engine.ribbon.graphics.component.Component;
import everyos.engine.ribbon.graphics.ui.ComponentUI;
import everyos.engine.ribbon.graphics.ui.DrawData;
import everyos.engine.ribbon.shape.SizePosGroup;

public class SimpleBreakComponentUI extends SimpleComponentUI {
	public SimpleBreakComponentUI() {}
	public SimpleBreakComponentUI(Component c) {
		this.c = c;
	}
	@Override public ComponentUI create(Component c) {
		return new SimpleBreakComponentUI(c);
	};
	@Override public void calcSize(GUIRenderer r, SizePosGroup sizepos, DrawData data) {
		sizepos.nextLine();
		sizepos.normalize();
		super.calcSize(r, sizepos, data);
	}
}
