package everyos.engine.ribbon.graphics.ui;

import java.util.HashMap;

import everyos.engine.ribbon.graphics.Renderer;
import everyos.engine.ribbon.graphics.component.Component;
import everyos.engine.ribbon.input.MouseBinding;
import everyos.engine.ribbon.shape.SizePosGroup;

public class ComponentUI {
	protected HashMap<String, Object> attributes = new HashMap<String, Object>();
	public ComponentUI() {}
	public ComponentUI(Component c) {}
	public ComponentUI create(Component c) {
		return new ComponentUI(c);
	};
	public void draw(Renderer r, DrawData data) {}
	public void calcSize(Renderer r, SizePosGroup sizepos, DrawData data) {}
	public void attribute(String name, Object attr) {
		if (attr==null) {
			attributes.remove(name);
			return;
		}
		attributes.put(name, attr);
	}
	protected void saveAndSetData(DrawData data) {}
	protected void restoreData(DrawData data) {}
	public MouseBinding boundBind(MouseBinding binding) { return binding; };
}
