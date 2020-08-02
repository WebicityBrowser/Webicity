package everyos.browser.webicity.webribbon.component;

import java.util.ArrayList;
import java.util.HashMap;

import everyos.browser.webicity.renderer.html.dom.Node;
import everyos.browser.webicity.renderer.html.dom.NodeList;
import everyos.browser.webicity.webribbon.misc.DrawData;
import everyos.browser.webicity.webribbon.shape.SizePosGroup;
import everyos.engine.ribbon.renderer.guirenderer.GUIRenderer;
import everyos.engine.ribbon.renderer.guirenderer.shape.Dimension;

public class WebComponent { //TODO: Code will be moved to WebUI
	protected HashMap<String, Object> attributes;
	protected Node node;
	protected SizePosGroup bounds;
	protected ArrayList<WebComponent> children;
	protected WebComponent parent;
	
	public WebComponent(Node node) {
		this.node = node;
	}
	
	public void calculateCascade() {
		attributes = new HashMap<>();
		attributes.put("color", "inherit");
	};
	
	public Object resolveAttribute(String name, Object defaultValue) {
		WebComponent e = this;
		while (e!=null) {
			//if (e.node instanceof Element) System.out.println(((Element) e.node).tagName);
			Object attr = e.attributes.getOrDefault(name, defaultValue);
			if (attr==null||!attr.equals("inherit")) return attr;
			WebComponent oe = e;
			e = e.parent;
			if (oe==e) return defaultValue; //TODO: Fix
		}
		return defaultValue;
	}
	
	public void render(GUIRenderer r, SizePosGroup sizepos) {
		calculateCascade();
		
		Object display = resolveAttribute("display", "block"); //TODO: Check display supported
		
		renderBefore(r, sizepos);
		if (display.equals("none")) {
		} else if (display.equals("inline")) {
			renderInBounds(r, sizepos);
		} else if (display.equals("block")) {
			//renderChildren(r, sizepos);
			
			if (sizepos.pointer.x!=0) sizepos.nextLine();
			SizePosGroup blockBounds = new SizePosGroup(sizepos, new Dimension(-1, -1));
			this.bounds = blockBounds;
			renderInBounds(r, blockBounds);
			blockBounds.finished();
			sizepos.minIncrease(blockBounds.size.height);
			sizepos.pointer.x+=blockBounds.size.width;
			//System.out.println("H:"+blockBounds.size.height);
			//System.out.println("W:"+blockBounds.size.width);
			sizepos.nextLine();
		} else if (display.equals("inline-block")) {
			SizePosGroup blockBounds = new SizePosGroup(sizepos, new Dimension(sizepos.size.width-sizepos.pointer.x, -1));
			this.bounds = blockBounds;
			renderInBounds(r, blockBounds);
			blockBounds.finished();
			if (sizepos.position.x!=0&&blockBounds.size.width+sizepos.pointer.x>sizepos.size.width) {
				sizepos.nextLine();
				blockBounds.position = sizepos.position();
			}
			sizepos.minIncrease(blockBounds.size.height);
		} else if (display.equals("contents")) {
			renderChildren(r, sizepos);
		}
		/*if (this.sizepos!=null) {
			sizepos.pointer.x = this.sizepos.position.x + this.sizepos.size.width;
			sizepos.pointer.y = this.sizepos.position.y + this.sizepos.size.height;
			System.out.println(sizepos.preferredWidth);
			if (sizepos.position.x>sizepos.preferredWidth) sizepos.nextLine();
			System.out.println("X:"+sizepos.position.x);
		}*/
		renderAfter(r, sizepos);
	}

	public void renderInBounds(GUIRenderer r, SizePosGroup sizepos) {
		renderChildren(r, sizepos);
	}
	public void renderChildren(GUIRenderer r, SizePosGroup sizepos) {
		for (WebComponent c: children()) c.render(r, sizepos);
	}
	
	public void paint(GUIRenderer r, DrawData d) {
		if (this.bounds==null) {
			paintChildren(r, d);
		} else {
			paintChildren(r, d);
		}
		//paintChildren(r);
	}
	public void paintChildren(GUIRenderer r, DrawData d) {
		if (this.bounds!=null) {
			r = r.getSubcontext(
				bounds.position.x,
				bounds.position.y,
				bounds.size.width,
				bounds.size.height);
		}
		for (WebComponent c: children()) c.paint(r, d);
		//TODO: Sort by Z-index
	}
	
	public WebComponent[] children() {
		if (this.children == null) {
			NodeList nchildren = node.getChildNodes();
			int len = (int) nchildren.getLength();
			this.children = new ArrayList<>(len);
			for (int i=0; i<len; i++) {
				WebComponent c = WebComponentFactory.createComponentFromNode(nchildren.item(i));
				c.setParent(this);
				if (c!=null) children.add(c);
			}
		}
		return this.children.toArray(new WebComponent[this.children.size()]);
	}
	private void setParent(WebComponent parent) {
		this.parent = parent;
	}

	/*public WebComponent[] parent() {
		return null;
	}*/
	
	protected void renderBefore(GUIRenderer r, SizePosGroup sizepos) {
		
	}
	protected void renderAfter(GUIRenderer r, SizePosGroup sizepos) {
		
	}
}
