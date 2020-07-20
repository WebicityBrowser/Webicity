package everyos.engine.ribbon.graphics.ui;

import java.util.ArrayList;
import java.util.HashMap;

import everyos.engine.ribbon.input.MouseBinding;

public class DrawData {
	public ArrayList<MouseBinding> bindings;
	public HashMap<String, Object> attributes;
	public DrawData() {
		bindings = new ArrayList<MouseBinding>();
		attributes = new HashMap<String, Object>();
	}
}
