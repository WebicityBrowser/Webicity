package everyos.browser.webicity.webribbon.ui.webui;

import java.util.ArrayList;

import everyos.browser.webicity.webribbon.core.component.WebComponent;
import everyos.browser.webicity.webribbon.core.ui.WebComponentUI;
import everyos.browser.webicity.webribbon.gui.UIBox;
import everyos.browser.webicity.webribbon.gui.UIContext;
import everyos.browser.webicity.webribbon.gui.shape.SizePosGroup;
import everyos.engine.ribbon.core.rendering.Renderer;
import everyos.engine.ribbon.renderer.guirenderer.event.UIEvent;
import everyos.engine.ribbon.renderer.guirenderer.graphics.GUIState;
import everyos.engine.ribbon.renderer.guirenderer.shape.Dimension;
import everyos.engine.ribbon.renderer.guirenderer.shape.Rectangle;

public class WebUIWebComponentUI implements WebComponentUI {
	private SizePosGroup bounds;
	private WebComponent component;
	private WebComponentUI parent;
	private ArrayList<WebComponentUI> children;
	private UIBox uibox = vp->true;

	public WebUIWebComponentUI(WebComponent component, WebComponentUI parent) {
		this.component = component;
		this.parent = parent;
	}

	@Override
	public void invalidate() {
		
	}

	@Override
	public void render(Renderer r, SizePosGroup sizepos, UIContext context) {
		//Object display = resolveAttribute("display", "block"); //TODO: Check display supported
		String display = "block";
		
		renderBefore(r, sizepos, context);
		
		GUIState state = r.getState();
		r.restoreState(state.clone());
		if (display.equals("none")) {
			
		} else if (display.equals("inline")) {
			renderUI(r, sizepos, context);
		} else if (display.equals("block")) {
			//renderChildren(r, sizepos);
			
			if (sizepos.pointer.x!=0) sizepos.nextLine();
			SizePosGroup blockBounds = new SizePosGroup(sizepos, new Dimension(-1, -1));
			this.bounds = blockBounds;
			renderUI(r, blockBounds, context);
			blockBounds.finished();
			sizepos.minIncrease(blockBounds.size.height);
			sizepos.pointer.x+=blockBounds.size.width;
			//System.out.println("H:"+blockBounds.size.height);
			//System.out.println("W:"+blockBounds.size.width);
			sizepos.nextLine();
		} else if (display.equals("inline-block")) {
			SizePosGroup blockBounds = new SizePosGroup(sizepos, new Dimension(sizepos.size.width-sizepos.pointer.x, -1));
			this.bounds = blockBounds;
			renderUI(r, blockBounds, context);
			blockBounds.finished();
			if (sizepos.position.x!=0&&blockBounds.size.width+sizepos.pointer.x>sizepos.size.width) {
				sizepos.nextLine();
				blockBounds.position = sizepos.position();
			}
			sizepos.minIncrease(blockBounds.size.height);
		} else if (display.equals("contents")) {
			renderChildren(r, sizepos, context);
		}
		/*if (this.sizepos!=null) {
			sizepos.pointer.x = this.sizepos.position.x + this.sizepos.size.width;
			sizepos.pointer.y = this.sizepos.position.y + this.sizepos.size.height;
			System.out.println(sizepos.preferredWidth);
			if (sizepos.position.x>sizepos.preferredWidth) sizepos.nextLine();
			System.out.println("X:"+sizepos.position.x);
		}*/
		r.restoreState(state);
		
		renderAfter(r, sizepos, context);
		
		setUIBox(viewport->true); //TODO
	}
	
	protected void setUIBox(UIBox uibox) {
		this.uibox = uibox;
	}
	
	public UIBox getUIBox() {
		return uibox;
	}

	protected void renderUI(Renderer r, SizePosGroup sizepos, UIContext context) {
		renderChildren(r, sizepos, context);
	}
	
	protected void renderBefore(Renderer r, SizePosGroup sizepos, UIContext context) {
		
	}
	protected void renderAfter(Renderer r, SizePosGroup sizepos, UIContext context) {
		
	}
	protected void renderChildren(Renderer r, SizePosGroup sizepos, UIContext context) {
		for (WebComponentUI c: calcChildren(context)) c.render(r, sizepos, context);
	}

	@Override
	public void paint(Renderer r, Rectangle viewport) {
		GUIState state = r.getState();
		r.restoreState(state.clone());
		paintUI(r, viewport); //TODO
		r.restoreState(state);
	}
	
	protected void paintUI(Renderer r, Rectangle viewport) {
		paintChildren(r, viewport);
	}
	
	protected void paintChildren(Renderer r, Rectangle viewport) {
		Rectangle vp = viewport.clone();
		if (this.bounds!=null) {
			r = r.getSubcontext(
				bounds.position.x,
				bounds.position.y,
				bounds.size.width,
				bounds.size.height);
			vp = new Rectangle(
				0, 0, 
				bounds.size.width, bounds.size.height);
			if (bounds.position.x+vp.width>viewport.width) {
				vp.width = viewport.width - bounds.position.x;
			}
			if (bounds.position.y+vp.height>viewport.height) {
				//Adding 1 fixed a bug. Don't ask me why, I don't know
				vp.height = viewport.height - bounds.position.y;
			}
		}
		for (WebComponentUI c: getChildren()) {
			//if (c.getUIBox().intersectsWith(vp)) {
				c.paint(r, vp);
			//}
		}
		//TODO: Sort by Z-index
	}

	@Override
	public WebComponentUI getParent() {
		return parent;
	}

	@Override
	public void invalidateLocal() {
		
	}

	@Override
	public void validate() {
		
	}

	@Override
	public boolean getValidated() {
		return false;
	}

	@Override
	public void repaintLocal() {
		
	}
	
	@Override
	public void processEvent(UIEvent ev) {
		if (parent!=null) {
			parent.processEvent(ev);
		}
	}
	
	
	///
	protected WebComponent getComponent() {
		return this.component;
	}
	
	protected WebComponentUI[] calcChildren(UIContext context) {
		this.children = new ArrayList<WebComponentUI>();
		for (WebComponent child: component.getChildren()) {
			WebComponentUI ui = context.getManager().get(child, this);
			children.add(ui);
		}
		return children.toArray(new WebComponentUI[children.size()]);
	}
	protected WebComponentUI[] getChildren() {
		return children.toArray(new WebComponentUI[children.size()]);
	}
}
