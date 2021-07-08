package everyos.engine.ribbon.ui.simple.layout;

import everyos.engine.ribbon.core.component.Component;
import everyos.engine.ribbon.core.directive.ExternalMouseListenerDirective;
import everyos.engine.ribbon.core.directive.MouseListenerDirective;
import everyos.engine.ribbon.core.directive.PositionDirective;
import everyos.engine.ribbon.core.directive.SizeDirective;
import everyos.engine.ribbon.core.event.MouseEvent;
import everyos.engine.ribbon.core.event.MouseListener;
import everyos.engine.ribbon.core.event.UIEvent;
import everyos.engine.ribbon.core.graphics.GUIState;
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
import everyos.engine.ribbon.ui.simple.appearence.Appearence;
import everyos.engine.ribbon.ui.simple.helper.ComputedChildrenHelper;
import everyos.engine.ribbon.ui.simple.helper.RectangleBuilder;

public class InlineBlockLayout implements Layout {
	private Component component;
	private ComponentUI ui;
	private ComputedChildrenHelper computedChildrenHelper;
	
	private Rectangle bounds;
	private Location position;
	private Location size;
	private Offset offset;
	private MouseListener mouseListener;
	private MouseListener externalMouseListener;
	private boolean autoManageChildren = true;

	public InlineBlockLayout(Component component, ComponentUI ui) {
		this.component = component;
		this.ui = ui;
	}

	public void render(Renderer r, SizePosGroup sizepos, UIManager uimgr, Appearence appearence) {
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
		renderInnerPart(r, group, uimgr, appearence);
		
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
	
	@Override
	public void paint(Renderer r, Appearence appearence) {
		Renderer r2 = r.getSubcontext(bounds.getX(), bounds.getY(), bounds.getWidth(), bounds.getHeight());
		paintMouse(r2, appearence);
		paintInnerPart(r2, appearence);
	}
	
	@Override
	public void directive(UIDirective directive) {
		//TODO: Optimize: Don't unnecessarily redraw redundant data
		if (directive instanceof PositionDirective) this.position = ((PositionDirective) directive).getPosition();
		if (directive instanceof SizeDirective) this.size = ((SizeDirective) directive).getSize();
		if (directive instanceof MouseListenerDirective) this.mouseListener = ((MouseListenerDirective) directive).getListener();
		if (directive instanceof ExternalMouseListenerDirective) this.externalMouseListener = ((ExternalMouseListenerDirective) directive).getListener();
	}
	
	@Override
	public void processEvent(UIEvent event) {
		if (event instanceof MouseEvent) {
			MouseEvent e = (MouseEvent) event;
			
			if (e.isExternal()&&externalMouseListener!=null) externalMouseListener.accept(e);
			if (!e.isExternal()&&mouseListener!=null) mouseListener.accept(e);
		}
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
	
	private void renderInnerPart(Renderer r, SizePosGroup sizepos, UIManager uimgr, Appearence appearence) {
		appearence.render(r, sizepos, uimgr);

		if (autoManageChildren)
			renderChildren(r, sizepos, uimgr);
	}

	public void renderChildren(Renderer r, SizePosGroup sizepos, UIManager uimgr) {
		this.computedChildrenHelper = new ComputedChildrenHelper(this.component, c -> uimgr.get(c, ui));

		GUIState state = r.getState().clone();
		for (ComponentUI c: computedChildrenHelper.getChildren()) {
			c.render(r, sizepos, uimgr);
			r.restoreState(state);
		}
	}
	
	private void paintInnerPart(Renderer r, Appearence appearence) {
		GUIState state = r.getState().clone();
		
		r.useBackground();
		
		appearence.paint(r);

		if (autoManageChildren)
			paintChildren(r);

		r.restoreState(state);
	}
	
	public void paintChildren(Renderer r) {
		r.useBackground();
		GUIState state = r.getState();
		for (ComponentUI c: computedChildrenHelper.getChildren()) {
			r.restoreState(state.clone());
			c.paint(r);
			r.restoreState(state);
		}
	}
	
	private void paintMouse(Renderer r, Appearence appearence) {
		r.paintMouseListener(component, 0, 0, bounds.getWidth(), bounds.getHeight(), e->{
			processEvent(e);
			appearence.processEvent(e);
		});
	}

	public void setAutoManageChildren(boolean considerChildren) {
		this.autoManageChildren = considerChildren;
	}
}
