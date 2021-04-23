package everyos.browser.webicity.webribbon.ui.webui;

import everyos.browser.webicity.webribbon.core.component.WebComponent;
import everyos.browser.webicity.webribbon.core.ui.WebComponentUI;
import everyos.browser.webicity.webribbon.gui.UIBox;
import everyos.browser.webicity.webribbon.gui.UIContext;
import everyos.browser.webicity.webribbon.gui.shape.SizePosGroup;
import everyos.engine.ribbon.core.event.UIEvent;
import everyos.engine.ribbon.core.graphics.GUIState;
import everyos.engine.ribbon.core.rendering.Renderer;
import everyos.engine.ribbon.core.shape.Rectangle;
import everyos.engine.ribbon.ui.simple.helper.RectangleBuilder;

public class WebUIWebComponentUI implements WebComponentUI {
	private Rectangle bounds;
	private final WebComponent component;
	private final WebComponentUI parent;
	private WebComponentUI[] children;
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
		//TODO: Avoid re-running this step, if possible
		
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
			
			if (sizepos.getCurrentPointer().getX()!=0) sizepos.nextLine();
			//SizePosGroup blockBounds = new SizePosGroup(sizepos, new Dimension(-1, -1));
			SizePosGroup blockBounds = new SizePosGroup(
				sizepos.getSize().getWidth(), 0,
				0, 0,
				-1, -1);
			renderUI(r, blockBounds, context);
			
			this.bounds = new Rectangle(
				sizepos.getCurrentPointer().getX(),
				sizepos.getCurrentPointer().getY(),
				blockBounds.getSize().getWidth(),
				blockBounds.getSize().getHeight());
			
			sizepos.setMinLineHeight(blockBounds.getSize().getHeight());
			sizepos.move(blockBounds.getSize().getWidth(), true);
			sizepos.nextLine();
		} else if (display.equals("inline-block")) {
			//SizePosGroup blockBounds = new SizePosGroup(sizepos, new Dimension(sizepos.size.getWidth()-sizepos.pointer.x, -1));
			SizePosGroup blockBounds = new SizePosGroup(
				//sizepos.getCurrentPointer().x, sizepos.getCurrentPointer().y,
				0, 0,
				-1, -1);
			renderUI(r, blockBounds, context);
			/*if (sizepos.getCurrentPointer().x!=0&&blockBounds.getSize().getWidth()+sizepos.getCurrentPointer().x>sizepos.getSize().getWidth()) {
				sizepos.nextLine();
				blockBounds.position = sizepos.position();
			}*/
			this.bounds = new Rectangle(
					sizepos.getCurrentPointer().getX(),
					sizepos.getCurrentPointer().getY(),
					blockBounds.getSize().getWidth(),
					blockBounds.getSize().getHeight());
			sizepos.setMinLineHeight(blockBounds.getSize().getHeight());
		} else if (display.equals("contents")) {
			renderChildren(r, sizepos, context);
		}
		
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
	
	protected void paintChildren(Renderer r_, Rectangle viewport) {
		//Shuks, the culling code is still not working properly?
		Renderer r = r_;
		RectangleBuilder vpBuilder = new RectangleBuilder(viewport);
		if (this.bounds!=null) {
			r = r.getSubcontext(
				bounds.getX(),
				bounds.getY(),
				bounds.getWidth(),
				bounds.getHeight());
			
			//AABB based culling
			vpBuilder = new RectangleBuilder( //The viewport is the area in which subcomponents *could* be drawn
				0, 0, // Child components actually use the parent's location as the origin
				bounds.getWidth(), bounds.getHeight());
			
			// The new viewport should fit within the old viewport
			if (bounds.getX()+vpBuilder.getWidth()>viewport.getWidth()) {
				vpBuilder.setWidth(viewport.getWidth() - bounds.getX());
			}
			if (bounds.getY()+vpBuilder.getHeight()>viewport.getHeight()) {
				vpBuilder.setHeight(viewport.getHeight() - bounds.getY());
			}
		}
		Rectangle vp = vpBuilder.build();
		for (WebComponentUI c: getChildren()) {
			r.useBackground();
			if (c.getUIBox().intersectsWith(vp)) {
				c.paint(r, vp);
			}
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
		if (children!=null) return children;
		WebComponent[] childrenComponents = component.getChildren();
		this.children = new WebComponentUI[childrenComponents.length];
		for (int i=0; i<childrenComponents.length; i++) {
			children[i] = context.getManager().get(childrenComponents[i], this);
		}
		return children;
	}
	protected WebComponentUI[] getChildren() {
		return children;
	}
}
