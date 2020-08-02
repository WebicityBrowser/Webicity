package everyos.engine.ribbon.ui.simple;

import everyos.engine.ribbon.core.component.Component;
import everyos.engine.ribbon.core.ui.ComponentUI;
import everyos.engine.ribbon.core.ui.UIDirective;
import everyos.engine.ribbon.core.ui.UIManager;
import everyos.engine.ribbon.renderer.guirenderer.GUIComponentUI;
import everyos.engine.ribbon.renderer.guirenderer.GUIRenderer;
import everyos.engine.ribbon.renderer.guirenderer.directive.PositionDirective;
import everyos.engine.ribbon.renderer.guirenderer.directive.SizeDirective;
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
	protected boolean autofill = true;
	
	protected boolean forceCursor;
	
	public SimpleBlockComponentUI() {}
	public SimpleBlockComponentUI(Component c, GUIComponentUI parent) {
		super(c, parent);
	}
	@Override public ComponentUI create(Component c, ComponentUI parent) {
		return new SimpleBlockComponentUI(c, (GUIComponentUI) parent);
	};
	@Override public void render(GUIRenderer r, SizePosGroup sizepos, UIManager<GUIComponentUI> uimgr) {
		//TODO: Offset bindings
		//TODO: Auto-wrap
		Rectangle bounds = new Rectangle(sizepos.x, sizepos.y, -1, -1);
		if (this.position!=null) {
			if (sizepos.size.width!=-1) bounds.x = position.x.calculate(sizepos.size.width);
			if (sizepos.size.height!=-1&&position.y.percent!=-1) bounds.y = position.y.calculate(sizepos.size.height);
		}
		if (this.size!=null) {
			if (size.x.percent!=-1) bounds.width = size.x.calculate(sizepos.size.width);
			if (size.y.percent!=-1) bounds.height = size.y.calculate(sizepos.size.height);	
		}
		
		SizePosGroup group = new SizePosGroup(bounds.width, bounds.height, 0, 0,
				bounds.width==-1?sizepos.maxSize.width==-1?-1:sizepos.maxSize.width-sizepos.x:bounds.width, bounds.height);
		renderUI(r, group, uimgr);
		
		if (bounds.width==-1||bounds.height==-1) {
			if (bounds.width == -1) bounds.width = group.size.width;
			if (bounds.height == -1) bounds.height = group.size.height;
			
			//I dunno, I guess we do this twice, it ends up in exponential runtime though
			group = new SizePosGroup(bounds.width, bounds.height, 0, 0, bounds.width, bounds.height);
			renderUI(r, group, uimgr);
			//TODO: We can probably remove this with a fill-in-the-gap approach
		}
		
		this.bounds = bounds;
		if (position==null) {
			sizepos.add(new Dimension(bounds.width, bounds.height));
		} else {
			sizepos.min(new Dimension(bounds.x+bounds.width, bounds.y+bounds.height));
		}
		
		if (offset!=null) {
			bounds.x+=offset.applyX(bounds.width);
			bounds.y+=offset.applyY(bounds.height);
			if (position==null) {
				group.x+=offset.applyX(bounds.width);
				group.y+=offset.applyY(bounds.height);
			}
		}
		
		if (forceCursor) {
			sizepos.x = bounds.x+group.x;
			sizepos.y = bounds.y+group.y;
			sizepos.minIncrease(group.minIncrease());
			sizepos.normalize();
		}
	}
	protected void renderUI(GUIRenderer r, SizePosGroup sizepos, UIManager<GUIComponentUI> uimgr) {
		renderChildren(r, sizepos, uimgr);
	}
	
	@Override public void paint(GUIRenderer r) {
		if (bounds==null) bounds = new Rectangle(-1, -1, 1, 1);
		/*data.bindings.add(c.parent.boundBind(new MouseBinding(bounds, this.c)));
		Color color = (Color) data.attributes.get("bg-color");
		if (color!=null&&autofill) {
			r.setColor(color);
			r.drawFilledRect(bounds.x, bounds.y, bounds.width, bounds.height);
		}*/
		GUIRenderer r2 = r.getSubcontext(bounds.x, bounds.y, bounds.width, bounds.height);
		paintUI(r2);
	}
	
	protected void paintUI(GUIRenderer r) {
		paintChildren(r);
	}
	
	@Override public void directive(UIDirective directive) {
		super.directive(directive);
		if (directive instanceof PositionDirective) this.position = ((PositionDirective) directive).getPosition();
		if (directive instanceof SizeDirective) this.size = ((SizeDirective) directive).getSize();
	}
	
	
	/*@Override public MouseBinding boundBind(MouseBinding binding) {
		binding.bounds.x+=bounds.x;
		if (binding.bounds.x>bounds.x+bounds.width) {
			binding.bounds.x = -1;
			binding.bounds.width = -1;
		}
		if (binding.bounds.x+binding.bounds.width>bounds.x+bounds.width)
			binding.bounds.width=bounds.x+bounds.width-binding.bounds.x;
		
		binding.bounds.y+=bounds.y;
		if (binding.bounds.y>bounds.y+bounds.height) {
			binding.bounds.y = -1;
			binding.bounds.height = -1;
		}
		if (binding.bounds.y+binding.bounds.height>bounds.y+bounds.height)
			binding.bounds.height=bounds.height-binding.bounds.y;
		
		//TODO: Left bound cut
		
		return c.parent.boundBind(binding);
	}*/
}
