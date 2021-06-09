package everyos.browser.webicity.webribbon.ui.webui;

import everyos.browser.webicity.webribbon.core.component.WebComponent;
import everyos.browser.webicity.webribbon.core.ui.WebComponentUI;
import everyos.browser.webicity.webribbon.gui.UIBox;
import everyos.browser.webicity.webribbon.gui.UIContext;
import everyos.browser.webicity.webribbon.gui.shape.SizePosGroup;
import everyos.browser.webicitybrowser.gui.Styling;
import everyos.engine.ribbon.core.event.MouseEvent;
import everyos.engine.ribbon.core.event.UIEvent;
import everyos.engine.ribbon.core.graphics.Color;
import everyos.engine.ribbon.core.graphics.GUIState;
import everyos.engine.ribbon.core.rendering.Renderer;
import everyos.engine.ribbon.core.shape.Dimension;
import everyos.engine.ribbon.core.shape.Rectangle;
import everyos.engine.ribbon.ui.simple.helper.RectangleBuilder;

public class WebUIWebComponentUI implements WebComponentUI {
	private Rectangle bounds;
	private final WebComponent component;
	private final WebComponentUI parent;
	private WebComponentUI[] children;
	private UIBox uibox = vp->true;
	
	// Scrollbar stuff to be moved to another class
	private int maxScrollY = 0;
	private int curScrollY = 0;
	private Color scrollColor = Styling.BACKGROUND_SECONDARY;
	private int startScrollY;
	private int startPosY;
	private Dimension blockBounds;

	public WebUIWebComponentUI(WebComponent component, WebComponentUI parent) {
		this.component = component;
		this.parent = parent;
	}

	@Override
	public void render(Renderer r, SizePosGroup sizepos, UIContext context) {
		//TODO: Avoid re-running this step, if possible
		
		//Object display = resolveAttribute("display", "block"); //TODO: Check display supported
		String display = "block";
		
		renderBefore(r, sizepos, context);
		
		GUIState state = r.getState();
		r.restoreState(state.clone());
		switch(display) {
			case "none":
				break;
			case "inline":
				renderUI(r, sizepos, context);
				break;
			case "block":
				if (sizepos.getCurrentPointer().getX()!=0) sizepos.nextLine();
				renderInlineBlock(r, sizepos, context);
				if (sizepos.getCurrentPointer().getX()!=0) sizepos.nextLine();
				break;
			case "inline-block":
				renderInlineBlock(r, sizepos, context);
				break;
			case "contents":
				renderChildren(r, sizepos, context);
				break;
		}
		
		r.restoreState(state);
		
		renderAfter(r, sizepos, context);
		
		setUIBox(viewport->bounds.intersects(viewport)); //TODO
	}

	@Override
	public void paint(Renderer r, Rectangle viewport) {
		GUIState state = r.getState();
		r.restoreState(state.clone());
		paintUI(r, viewport); //TODO
		r.restoreState(state);
	}

	@Override
	public WebComponentUI getParent() {
		return parent;
	}
	
	@Override
	public void processEvent(UIEvent event) {
		if (event instanceof MouseEvent) {
			MouseEvent ev = (MouseEvent) event;
			if (ev.getAction()==MouseEvent.PRESS && ev.getButton()==MouseEvent.LEFT_BUTTON && !ev.isExternal()) {
				scrollColor = Styling.BACKGROUND_SECONDARY_SELECTED;
				this.startScrollY = this.curScrollY;
				this.startPosY = ev.getAbsoluteY();
			} else if (ev.getAction()==MouseEvent.RELEASE && ev.getButton()==MouseEvent.LEFT_BUTTON) {
				scrollColor = Styling.BACKGROUND_SECONDARY;
			} else if (ev.getAction()==MouseEvent.DRAG && scrollColor == Styling.BACKGROUND_SECONDARY_SELECTED) {
				this.curScrollY = this.startScrollY + (int) (
					(float) (ev.getAbsoluteY()-this.startPosY)/
					(float) (blockBounds.getHeight())*
					bounds.getHeight()
					);
				if (this.curScrollY<0) {
					this.curScrollY = 0;
				}
				if (this.curScrollY>this.maxScrollY) {
					this.curScrollY = this.maxScrollY;
				}
			}
			invalidate();
		}
		
		if (parent!=null) {
			parent.processEvent(event);
		}
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
	public void invalidate() {
		
	}
	
	
	///
	protected void setUIBox(UIBox uibox) {
		this.uibox = uibox;
	}
	
	public UIBox getUIBox() {
		return uibox;
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

	protected void renderUI(Renderer r, SizePosGroup sizepos, UIContext context) {
		renderChildren(r, sizepos, context);
	}
	
	protected void renderBefore(Renderer r, SizePosGroup sizepos, UIContext context) {
		
	}
	protected void renderAfter(Renderer r, SizePosGroup sizepos, UIContext context) {
		
	}
	protected void renderChildren(Renderer r, SizePosGroup sizepos, UIContext context) {
		for (WebComponentUI c: calcChildren(context)) {
			c.render(r, sizepos, context);
		}
	}
	
	protected WebComponent getComponent() {
		return this.component;
	}
	
	private void renderInlineBlock(Renderer r, SizePosGroup sizepos, UIContext context) {
		SizePosGroup blockBounds = new SizePosGroup(
			0, 0, //TODO: Full-width for block?
			0, 0,
			-1, -1);
		renderInnerInlineBlock(r, sizepos, blockBounds, context);
		
		if (sizepos.getMaxSize().getHeight()<blockBounds.getSize().getHeight() && sizepos.getMaxSize().getHeight()!=-1) {
			this.maxScrollY = blockBounds.getSize().getHeight()-sizepos.getMaxSize().getHeight();
			
			if (sizepos.getMaxSize().getWidth()!=-1) {
				blockBounds = new SizePosGroup(
					0, 0, //TODO: Full-width for block?
					0, 0,
					sizepos.getMaxSize().getWidth()-10, -1);
			}
			renderInnerInlineBlock(r, sizepos, blockBounds, context);
			
			this.maxScrollY = blockBounds.getSize().getHeight()-sizepos.getMaxSize().getHeight();
			
			//this.curScrollY = this.maxScrollY;
		}
		
		this.blockBounds = sizepos.getMaxSize();
		
		sizepos.setMinLineHeight(blockBounds.getSize().getHeight());
		sizepos.move(blockBounds.getSize().getWidth(), true);
	}
	
	private void renderInnerInlineBlock(Renderer r, SizePosGroup sizepos, SizePosGroup blockBounds, UIContext context) {
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
	}
	
	protected void paintUI(Renderer r, Rectangle viewport) {
		//TODO: Move the code for the scrollbar to it's own class, especially since it's a psuedo-element
		if (this.maxScrollY>0) {
			GUIState state = r.getState();
			r.restoreState(state.clone());
			
			r.setForeground(scrollColor);
			r.useForeground();
			
			int width = 8;
			int height = (int) (((double)viewport.getHeight()/(double)bounds.getHeight())*viewport.getHeight()-width);
			int posY = (int) (((double)this.curScrollY/(double)bounds.getHeight())*(viewport.getHeight()-width));
			
			r.drawEllipse(
				bounds.getX()+bounds.getWidth()+1, posY,
				width, width);
			r.drawFilledRect(
				bounds.getX()+bounds.getWidth()+1, posY+width/2,
				width, height+width/2);
			r.drawEllipse(
				bounds.getX()+bounds.getWidth()+1, posY+width/2+height,
				width, width);
			
			r.restoreState(state);
		}
		
		r.paintMouseListener(getComponent(), bounds.getX(), bounds.getY(), bounds.getWidth()+10, bounds.getHeight(), e->{
			if (e.isExternal()) return;
			processEvent(e);
		});
		
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
			
			r.setScrollY(curScrollY);
			
			//AABB based culling
			vpBuilder = new RectangleBuilder( //The viewport is the area in which subcomponents *could* be drawn
				0, curScrollY, // Child components actually use the parent's location as the origin
				bounds.getWidth(), bounds.getHeight()-curScrollY);
			
			// The new viewport should fit within the old viewport			
			if (bounds.getX()+vpBuilder.getWidth()>viewport.getWidth()) {
				vpBuilder.setWidth(viewport.getWidth() - bounds.getX());
			}
			//TODO: Cut things outside top of bounds
			if ((bounds.getY()-viewport.getY())+vpBuilder.getHeight()>viewport.getHeight()) {
				// TODO: This logic can be confusing, so I should make it easier to understand.
				vpBuilder.setHeight(viewport.getHeight() - (bounds.getY()-viewport.getY()));
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
}
