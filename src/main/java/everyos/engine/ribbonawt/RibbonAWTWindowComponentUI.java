package everyos.engine.ribbonawt;

import java.awt.Dimension;

import everyos.engine.ribbon.graphics.Renderer;
import everyos.engine.ribbon.graphics.component.Component;
import everyos.engine.ribbon.graphics.ui.ComponentUI;
import everyos.engine.ribbon.graphics.ui.DrawData;
import everyos.engine.ribbon.graphics.ui.simple.SimpleComponentUI;
import everyos.engine.ribbon.shape.SizePosGroup;

public class RibbonAWTWindowComponentUI extends SimpleComponentUI {
	public RibbonAWTWindowComponentUI() {}
	public RibbonAWTWindowComponentUI(Component c) {
		this.c = c;
	}
	@Override public ComponentUI create(Component c) {
		return new RibbonAWTWindowComponentUI(c);
	};
	@Override public void calcSize(Renderer r, SizePosGroup _0, DrawData data) {
		Dimension size = ((RibbonAWTWindowComponent) c).panel.getSize();
		SizePosGroup sizepos = new SizePosGroup(size.width, size.height, 0, 0);
		super.calcSize(r, sizepos, data);
	}
	@Override public void draw(Renderer r, DrawData data) {
		for (Component child: c.getChildren()) {
			child.draw(r, data);
		}
	}
}
