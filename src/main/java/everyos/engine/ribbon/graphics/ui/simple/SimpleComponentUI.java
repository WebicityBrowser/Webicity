package everyos.engine.ribbon.graphics.ui.simple;

import java.util.HashMap;

import everyos.engine.ribbon.graphics.Renderer;
import everyos.engine.ribbon.graphics.component.Component;
import everyos.engine.ribbon.graphics.ui.ComponentUI;
import everyos.engine.ribbon.graphics.ui.DrawData;
import everyos.engine.ribbon.shape.SizePosGroup;

public class SimpleComponentUI extends ComponentUI {
	protected Component c;
	
	private HashMap<String, Object> backup;
	private boolean isBackedUp = false;
	
	public SimpleComponentUI() {}
	public SimpleComponentUI(Component c) {
		this.c = c;
	}
	@Override public ComponentUI create(Component c) {
		return new SimpleComponentUI(c);
	};
	@Override public void calcSize(Renderer r, SizePosGroup sizepos, DrawData data) {
		saveAndSetData(data);
		calcChildren(r, sizepos, data);
		restoreData(data);
	}
	protected void calcChildren(Renderer r, SizePosGroup sizepos, DrawData data) {
		for (Component child: c.getChildren()) child.calcSize(r, sizepos, data);
	}
	
	@Override public void draw(Renderer r, DrawData data) {
		saveAndSetData(data);
		drawChildren(r, data);
		restoreData(data);
	}
	protected void drawChildren(Renderer r, DrawData data) {
		for (Component child: c.getChildren()) child.draw(r, data);
	}
	
	@Override public void attribute(String name, Object attr) {
		super.attribute(name, attr);
	};
	
	protected void backupSet(DrawData data, String name, String org) {
		backup.put(name, data.attributes.get(name));
		if (attributes.get(org)!=null) data.attributes.put(name, attributes.get(org));
	}
	protected void backupSet(DrawData data, String name) {
		backupSet(data, name, name);
	}
	
	protected void saveAndSetData(DrawData data) {
		if (isBackedUp) return;
		isBackedUp = true;
		backup = new HashMap<String, Object>();
		backupSet(data, "font");
		backupSet(data, "bg-color");
		backupSet(data, "fg-color");
		backupSet(data, "fill-color");
		backupSet(data, "font-size");
	}
	protected void restoreData(DrawData data) {
		backup.forEach((n, o)->{
			if (o==null) {
				data.attributes.remove(n);
				return;
			}
			data.attributes.put(n, o);
		});
		isBackedUp = false;
	}
}
