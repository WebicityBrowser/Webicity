package everyos.engine.ribbon.graphics.ui.simple;

import everyos.engine.ribbon.graphics.Color;
import everyos.engine.ribbon.graphics.GUIRenderer;
import everyos.engine.ribbon.graphics.component.Component;
import everyos.engine.ribbon.graphics.ui.ComponentUI;
import everyos.engine.ribbon.graphics.ui.DrawData;
import everyos.engine.ribbon.shape.SizePosGroup;

public class SimpleBufferedComponentUI extends SimpleBlockComponentUI {
	public SimpleBufferedComponentUI() {}
	public SimpleBufferedComponentUI(Component c) {
		super(c);
	}
	public ComponentUI create(Component c) {
		return new SimpleBufferedComponentUI(c);
	};
	
	@Override public void calcSize(GUIRenderer r, SizePosGroup sizepos, DrawData data) {
		super.calcSize(r.getBufferedSubcontext(0, 0, 1, 1), sizepos, data);
	}
	
	@Override public void draw(GUIRenderer r, DrawData data) {
		saveAndSetData(data);
		GUIRenderer r2 = r.getBufferedSubcontext(bounds.x, bounds.y, bounds.width, bounds.height);
		r2.setColor((Color) data.attributes.getOrDefault("bg-color", Color.WHITE));
		r2.drawFilledRect(0, 0, bounds.width, bounds.height);
		for (Component child: c.getChildren()) {
			child.draw(r2, data);
		}
		r2.draw();
		restoreData(data);
	}
}
