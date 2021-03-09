package everyos.engine.ribbon.ui.simple;

import everyos.engine.ribbon.core.component.Component;
import everyos.engine.ribbon.core.rendering.Renderer;
import everyos.engine.ribbon.core.ui.ComponentUI;
import everyos.engine.ribbon.core.ui.UIDirective;
import everyos.engine.ribbon.core.ui.UIManager;
import everyos.engine.ribbon.renderer.guirenderer.directive.ExternalMouseListenerDirective;
import everyos.engine.ribbon.renderer.guirenderer.directive.MouseListenerDirective;
import everyos.engine.ribbon.renderer.guirenderer.directive.PositionDirective;
import everyos.engine.ribbon.renderer.guirenderer.directive.SizeDirective;
import everyos.engine.ribbon.renderer.guirenderer.event.MouseListener;
import everyos.engine.ribbon.renderer.guirenderer.shape.Dimension;
import everyos.engine.ribbon.renderer.guirenderer.shape.Location;
import everyos.engine.ribbon.renderer.guirenderer.shape.Offset;
import everyos.engine.ribbon.renderer.guirenderer.shape.Rectangle;
import everyos.engine.ribbon.renderer.guirenderer.shape.SizePosGroup;

public class SimpleBlockComponentUI extends SimpleComponentUI {
	protected Rectangle bounds;
	protected Location position;
	protected Location size;
	protected Offset offset;
	
	//protected boolean forceCursor;
	protected MouseListener mouseListener;
	protected MouseListener externalMouseListener;
	
	public SimpleBlockComponentUI(Component c, ComponentUI parent) {
		super(c, parent);
	}
	
	@Override
	public void render(Renderer r, SizePosGroup sizepos, UIManager uimgr) {
		//TODO: Offset bindings
		//TODO: Auto-wrap
		
		// Using directives, determine the size and position of this element
		// Oftentimes, this may be proportional to sizepos
		Rectangle bounds = calculatePreferredBounds(sizepos);
		
		// Block components maintain their own size, which is used for positioning and the background size
		// If we don't know the width of an element ahead of time, we try to use as little screen space as possible.
		// Usually, the UI is bound to a display, so we know the maximum possible width of an element ahead of time
		// In the case of a scrolling pane, however, the usable width is potentially infinite
		SizePosGroup group = new SizePosGroup(bounds.width, bounds.height, 0, 0,
			bounds.width!=-1?
				bounds.width:
				sizepos.maxSize.width==-1?
					-1:
					sizepos.maxSize.width-sizepos.x,
			bounds.height);
		
		// Render our component, as well as any child components
		renderUI(r, group, uimgr);
		
		// Now that we have determined the size, we can clip our bounds
		// Note that, even in a container with an infinite max width, we
		// use as little space as possible.
		group.normalize();
		if (bounds.width == -1) {
			bounds.width = group.maxSize.width;
		}
		if (bounds.height == -1) {
			bounds.height = group.maxSize.height;
		}
		
		// Cache the bounds for when we go and draw this component
		this.bounds = bounds;
		
		// Offset the element, if desired
		if (offset!=null) {
			bounds.x+=offset.applyX(bounds.width);
			bounds.y+=offset.applyY(bounds.height);
		}
		
		// Change the parent component's current pointer
		if (position==null) {
			sizepos.add(new Dimension(bounds.width, bounds.height));
		} else {
			// Components with a fixed position do not affect the pointer
			// However, we do still need to stretch the parent container to accomadate the component.
			sizepos.min(new Dimension(bounds.x+bounds.width, bounds.y+bounds.height));
		}
	}

	protected void renderUI(Renderer r, SizePosGroup sizepos, UIManager uimgr) {
		renderChildren(r, sizepos, uimgr);
	}
	
	@Override
	public void paint(Renderer r) {
		Renderer r2 = r.getSubcontext(bounds.x, bounds.y, bounds.width, bounds.height);
		if (background!=null) r2.setBackground(background);
		if (foreground!=null) r2.setForeground(foreground);
		paintUI(r2);
	}
	
	protected void paintUI(Renderer r) {
		paintMouse(r);
		r.useBackground();
		r.drawFilledRect(0, 0, bounds.width, bounds.height);
		paintChildren(r);
	}
	
	protected void paintMouse(Renderer r) {
		r.paintMouseListener(this.getComponent(), 0, 0, bounds.width, bounds.height, e->{
			if (e.isExternal()&&externalMouseListener!=null) externalMouseListener.accept(e);
			if (!e.isExternal()&&mouseListener!=null) mouseListener.accept(e);
		});
	}
	
	@Override
	public void directive(UIDirective directive) {
		super.directive(directive);
		//TODO: Optimize: Don't unnecessarily redraw redundant data
		if (directive instanceof PositionDirective) this.position = ((PositionDirective) directive).getPosition();
		if (directive instanceof SizeDirective) this.size = ((SizeDirective) directive).getSize();
		if (directive instanceof MouseListenerDirective) this.mouseListener = ((MouseListenerDirective) directive).getListener();
		if (directive instanceof ExternalMouseListenerDirective) this.externalMouseListener = ((ExternalMouseListenerDirective) directive).getListener();
	}
	
	private Rectangle calculatePreferredBounds(SizePosGroup sizepos) {
		Rectangle bounds = new Rectangle(sizepos.x, sizepos.y, -1, -1);
		if (this.position!=null) {
			if (sizepos.size.width!=-1) bounds.x = position.x.calculate(sizepos.size.width);
			if (sizepos.size.height!=-1&&position.y.percent!=-1) bounds.y = position.y.calculate(sizepos.size.height);
		}
		if (this.size!=null) {
			if (size.x.percent!=-1) bounds.width = size.x.calculate(sizepos.size.width);
			if (size.y.percent!=-1) bounds.height = size.y.calculate(sizepos.size.height);	
		}
		
		return bounds;
	}
}
