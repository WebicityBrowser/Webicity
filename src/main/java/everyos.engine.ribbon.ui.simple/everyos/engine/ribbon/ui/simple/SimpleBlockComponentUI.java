package everyos.engine.ribbon.ui.simple;

import everyos.engine.ribbon.core.component.Component;
import everyos.engine.ribbon.core.directive.ExternalMouseListenerDirective;
import everyos.engine.ribbon.core.directive.MouseListenerDirective;
import everyos.engine.ribbon.core.directive.PositionDirective;
import everyos.engine.ribbon.core.directive.SizeDirective;
import everyos.engine.ribbon.core.event.MouseListener;
import everyos.engine.ribbon.core.rendering.Renderer;
import everyos.engine.ribbon.core.shape.Dimension;
import everyos.engine.ribbon.core.shape.Location;
import everyos.engine.ribbon.core.shape.Offset;
import everyos.engine.ribbon.core.shape.Position;
import everyos.engine.ribbon.core.shape.Rectangle;
import everyos.engine.ribbon.core.shape.SizePosGroup;
import everyos.engine.ribbon.core.ui.ComponentUI;
import everyos.engine.ribbon.core.ui.UIDirective;
import everyos.engine.ribbon.core.ui.UIManager;
import everyos.engine.ribbon.ui.simple.helper.RectangleBuilder;

public class SimpleBlockComponentUI extends SimpleComponentUI {
	private Rectangle bounds;
	private Location position;
	private Location size;
	private Offset offset;
	
	private MouseListener mouseListener;
	private MouseListener externalMouseListener;
	
	public SimpleBlockComponentUI(Component c, ComponentUI parent) {
		super(c, parent);
	}
	
	@Override
	public void render(Renderer r, SizePosGroup sizepos, UIManager uimgr) {
		//TODO: Offset bindings
		//TODO: Auto-wrap
		
		// Using directives, determine the size and position of this element
		// Oftentimes, this may be proportional to sizepos
		RectangleBuilder bounds = calculatePreferredBounds(sizepos);
		
		// Block components maintain their own size, which is used for positioning and the background size
		// If we don't know the width of an element ahead of time, we try to use as little screen space as possible.
		// Usually, the UI is bound to a display, so we know the maximum possible width of an element ahead of time
		// In the case of a scrolling pane, however, the usable width is potentially infinite
		SizePosGroup group = new SizePosGroup(bounds.getWidth(), bounds.getHeight(), 0, 0,
			bounds.getWidth()!=-1?
				bounds.getWidth():
				sizepos.getMaxSize().getWidth()==-1?
					-1:
					sizepos.getMaxSize().getWidth()-sizepos.getCurrentPointer().getX(),
			bounds.getHeight());
		
		// Use our current guess of the approximate bounds to help render the component
		this.bounds = bounds.build();
		
		// Render our component, as well as any child components
		renderUI(r, group, uimgr);
		
		// Now that we have determined the size, we can clip our bounds
		// Note that, even in a container with an infinite max width, we
		// use as little space as possible.
		//group.normalize();
		if (bounds.getWidth() == -1) {
			bounds.setWidth(group.getMaxSize().getWidth());
		}
		if (bounds.getHeight() == -1) {
			bounds.setHeight(group.getMaxSize().getHeight());
		}
		
		// Offset the element, if desired
		if (offset!=null) {
			bounds.setX(offset.applyX(bounds.getWidth()));
			bounds.setY(offset.applyY(bounds.getHeight()));
		}
		
		// Change the parent component's current pointer
		if (position==null) {
			sizepos.add(new Dimension(bounds.getWidth(), bounds.getHeight()));
		} else {
			// Components with a fixed position do not affect the pointer
			// However, we do still need to stretch the parent container to accommodate the component.
			sizepos.min(new Dimension(bounds.getX()+bounds.getWidth(), bounds.getY()+bounds.getHeight()));
		}
		
		// Cache the bounds for when we go and draw this component
		this.bounds = bounds.build();
	}

	protected void renderUI(Renderer r, SizePosGroup sizepos, UIManager uimgr) {
		renderChildren(r, sizepos, uimgr);
	}
	
	@Override
	public void paint(Renderer r) {
		Renderer r2 = r.getSubcontext(bounds.getX(), bounds.getY(), bounds.getWidth(), bounds.getHeight());
		if (getBackground()!=null) r2.setBackground(getBackground());
		if (getForeground()!=null) r2.setForeground(getForeground());
		paintUI(r2);
	}
	
	protected void paintUI(Renderer r) {
		paintMouse(r);
		r.useBackground();
		r.drawFilledRect(0, 0, bounds.getWidth(), bounds.getHeight());
		paintChildren(r);
	}
	
	protected void paintMouse(Renderer r) {
		r.paintMouseListener(this.getComponent(), 0, 0, bounds.getWidth(), bounds.getHeight(), e->{
			if (e.isExternal()&&externalMouseListener!=null) externalMouseListener.accept(e);
			if (!e.isExternal()&&mouseListener!=null) mouseListener.accept(e);
		});
	}
	
	protected Rectangle getBounds() {
		return bounds;
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
	
	private RectangleBuilder calculatePreferredBounds(SizePosGroup sizepos) {
		Position pos = sizepos.getCurrentPointer();
		RectangleBuilder builder = new RectangleBuilder(pos.getX(), pos.getY(), -1, -1);
	
		if (this.position!=null) {
			if (sizepos.getSize().getWidth()!=-1) {
				builder.setX(position.x.calculate(sizepos.getSize().getWidth()));
			}
			if (sizepos.getSize().getHeight()!=-1&&position.y.percent!=-1) {
				builder.setY(position.y.calculate(sizepos.getSize().getHeight()));
			}
		}
		if (this.size!=null) {
			if (size.x.percent!=-1) {
				builder.setWidth(size.x.calculate(sizepos.getSize().getWidth()));
			}
			if (size.y.percent!=-1) {
				builder.setHeight(size.y.calculate(sizepos.getSize().getHeight()));	
			}
		}
		
		return builder;
	}
}
