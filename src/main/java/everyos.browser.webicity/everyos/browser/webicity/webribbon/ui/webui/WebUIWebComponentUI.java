package everyos.browser.webicity.webribbon.ui.webui;

import java.util.ArrayList;

import everyos.browser.webicity.webribbon.core.component.WebComponent;
import everyos.browser.webicity.webribbon.core.ui.WebComponentUI;
import everyos.browser.webicity.webribbon.core.ui.WebUIManager;
import everyos.browser.webicity.webribbon.gui.GUIWebComponentUI;
import everyos.browser.webicity.webribbon.gui.shape.SizePosGroup;
import everyos.engine.ribbon.renderer.guirenderer.GUIRenderer;
import everyos.engine.ribbon.renderer.guirenderer.graphics.GUIState;
import everyos.engine.ribbon.renderer.guirenderer.shape.Dimension;

public class WebUIWebComponentUI extends GUIWebComponentUI {
	private SizePosGroup bounds;
	private WebComponent component;
	private GUIWebComponentUI parent;
	private ArrayList<GUIWebComponentUI> children;

	public WebUIWebComponentUI() {}
	public WebUIWebComponentUI(WebComponent component, GUIWebComponentUI parent) {
		this.component = component;
		this.parent = parent;
	}
	
	@Override public WebComponentUI create(WebComponent component, WebComponentUI parent) {
		return new WebUIWebComponentUI(component, (GUIWebComponentUI) parent);
	}
	
	/*public void calculateCascade() {
		attributes = new HashMap<>();
		attributes.put(Attribute.FOREGROUND_COLOR, "inherit");
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
	}*/

	@Override public void invalidate() {
		
	}

	@Override public void render(GUIRenderer r, SizePosGroup sizepos, WebUIManager<GUIWebComponentUI> uimgr) {
		//Object display = resolveAttribute("display", "block"); //TODO: Check display supported
		String display = "block";
		
		renderBefore(r, sizepos, uimgr);
		
		GUIState state = r.getState();
		r.restoreState(state.clone());
		if (display.equals("none")) {
		} else if (display.equals("inline")) {
			renderUI(r, sizepos, uimgr);
		} else if (display.equals("block")) {
			//renderChildren(r, sizepos);
			
			if (sizepos.pointer.x!=0) sizepos.nextLine();
			SizePosGroup blockBounds = new SizePosGroup(sizepos, new Dimension(-1, -1));
			this.bounds = blockBounds;
			renderUI(r, blockBounds, uimgr);
			blockBounds.finished();
			sizepos.minIncrease(blockBounds.size.height);
			sizepos.pointer.x+=blockBounds.size.width;
			//System.out.println("H:"+blockBounds.size.height);
			//System.out.println("W:"+blockBounds.size.width);
			sizepos.nextLine();
		} else if (display.equals("inline-block")) {
			SizePosGroup blockBounds = new SizePosGroup(sizepos, new Dimension(sizepos.size.width-sizepos.pointer.x, -1));
			this.bounds = blockBounds;
			renderUI(r, blockBounds, uimgr);
			blockBounds.finished();
			if (sizepos.position.x!=0&&blockBounds.size.width+sizepos.pointer.x>sizepos.size.width) {
				sizepos.nextLine();
				blockBounds.position = sizepos.position();
			}
			sizepos.minIncrease(blockBounds.size.height);
		} else if (display.equals("contents")) {
			renderChildren(r, sizepos, uimgr);
		}
		/*if (this.sizepos!=null) {
			sizepos.pointer.x = this.sizepos.position.x + this.sizepos.size.width;
			sizepos.pointer.y = this.sizepos.position.y + this.sizepos.size.height;
			System.out.println(sizepos.preferredWidth);
			if (sizepos.position.x>sizepos.preferredWidth) sizepos.nextLine();
			System.out.println("X:"+sizepos.position.x);
		}*/
		r.restoreState(state);
		
		renderAfter(r, sizepos, uimgr);
	}
	
	protected void renderUI(GUIRenderer r, SizePosGroup sizepos, WebUIManager<GUIWebComponentUI> uimgr) {
		renderChildren(r, sizepos, uimgr);
	}
	
	protected void renderBefore(GUIRenderer r, SizePosGroup sizepos, WebUIManager<GUIWebComponentUI> uimgr) {
		
	}
	protected void renderAfter(GUIRenderer r, SizePosGroup sizepos, WebUIManager<GUIWebComponentUI> uimgr) {
		
	}
	protected void renderChildren(GUIRenderer r, SizePosGroup sizepos, WebUIManager<GUIWebComponentUI> uimgr) {
		for (GUIWebComponentUI c: calcChildren(uimgr)) c.render(r, sizepos, uimgr);
	}

	@Override public void paint(GUIRenderer r) {
		GUIState state = r.getState();
		r.restoreState(state.clone());
		paintUI(r); //TODO
		r.restoreState(state);
	}
	
	protected void paintUI(GUIRenderer r) {
		paintChildren(r);
	}
	
	protected void paintChildren(GUIRenderer r) {
		if (this.bounds!=null) {
			r = r.getSubcontext(
				bounds.position.x,
				bounds.position.y,
				bounds.size.width,
				bounds.size.height);
		}
		for (GUIWebComponentUI c: getChildren()) c.paint(r);
		//TODO: Sort by Z-index
	}

	@Override public GUIWebComponentUI getParent() {
		return parent;
	}

	@Override public void invalidateLocal() {
		
	}

	@Override public void validate() {
		
	}

	@Override public boolean getValidated() {
		return false;
	}

	@Override public void repaintLocal() {
		
	}
	
	
	///
	protected WebComponent getComponent() {
		return this.component;
	}
	
	protected GUIWebComponentUI[] calcChildren(WebUIManager<GUIWebComponentUI> uimgr) {
		this.children = new ArrayList<GUIWebComponentUI>();
		for (WebComponent child: component.getChildren()) {
			GUIWebComponentUI ui = uimgr.get(child, parent);
			child.bind(ui);
			children.add(ui);
		}
		return children.toArray(new GUIWebComponentUI[children.size()]);
	}
	protected GUIWebComponentUI[] getChildren() {
		return children.toArray(new GUIWebComponentUI[children.size()]);
	}
}
