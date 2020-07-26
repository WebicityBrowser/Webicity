package everyos.engine.ribbon.graphics.ui.simple;

import everyos.engine.ribbon.graphics.Color;
import everyos.engine.ribbon.graphics.GUIRenderer;
import everyos.engine.ribbon.graphics.component.Component;
import everyos.engine.ribbon.graphics.ui.ComponentUI;
import everyos.engine.ribbon.graphics.ui.Dimension;
import everyos.engine.ribbon.graphics.ui.DrawData;
import everyos.engine.ribbon.graphics.ui.Rectangle;
import everyos.engine.ribbon.input.MouseBinding;
import everyos.engine.ribbon.shape.Location;
import everyos.engine.ribbon.shape.Offset;
import everyos.engine.ribbon.shape.SizePosGroup;

public class SimpleBlockComponentUI extends SimpleComponentUI {
	protected Rectangle bounds;
	protected Location location;
	protected Location size;
	protected Offset offset;
	protected boolean autofill = true;
	
	protected boolean forceCursor;
	
	public SimpleBlockComponentUI() {}
	public SimpleBlockComponentUI(Component c) {
		super(c);
	}
	@Override public ComponentUI create(Component c) {
		return new SimpleBlockComponentUI(c);
	};
	@Override public void calcSize(GUIRenderer r, SizePosGroup sizepos, DrawData data) {
		//TODO: Offset bindings
		//TODO: Auto-wrap
		saveAndSetData(data);
		Rectangle bounds = new Rectangle(sizepos.x, sizepos.y, -1, -1);
		if (this.location!=null) {
			if (sizepos.size.width!=-1) bounds.x = location.x.calculate(sizepos.size.width);
			if (sizepos.size.height!=-1&&location.y.percent!=-1) bounds.y = location.y.calculate(sizepos.size.height);
		}
		if (this.size!=null) {
			if (size.x.percent!=-1) bounds.width = size.x.calculate(sizepos.size.width);
			if (size.y.percent!=-1) bounds.height = size.y.calculate(sizepos.size.height);	
		}
		
		SizePosGroup group = new SizePosGroup(bounds.width, bounds.height, 0, 0,
				bounds.width==-1?sizepos.maxSize.width==-1?-1:sizepos.maxSize.width-sizepos.x:bounds.width, bounds.height);
		calcInternalSize(r, group, data);
		
		if (bounds.width==-1||bounds.height==-1) {
			if (bounds.width == -1) bounds.width = group.size.width;
			if (bounds.height == -1) bounds.height = group.size.height;
			
			//I dunno, I guess we do this twice, it ends up in exponential runtime though
			group = new SizePosGroup(bounds.width, bounds.height, 0, 0, bounds.width, bounds.height);
			calcInternalSize(r, group, data);
			//TODO: We can probably remove this with a fill-in-the-gap approach
		}
		
		this.bounds = bounds;
		if (location==null) {
			sizepos.add(new Dimension(bounds.width, bounds.height));
		} else {
			sizepos.min(new Dimension(bounds.x+bounds.width, bounds.y+bounds.height));
		}
		
		if (offset!=null) {
			bounds.x+=offset.applyX(bounds.width);
			bounds.y+=offset.applyY(bounds.height);
			if (location==null) {
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
		restoreData(data);
	}
	protected void calcInternalSize(GUIRenderer r, SizePosGroup sizepos, DrawData data) {
		calcChildren(r, sizepos, data);
	}
	
	@Override public void draw(GUIRenderer r, DrawData data) {
		saveAndSetData(data);
		if (bounds==null) bounds = new Rectangle(-1, -1, 1, 1);
		data.bindings.add(c.parent.boundBind(new MouseBinding(bounds, this.c)));
		Color color = (Color) data.attributes.get("bg-color");
		if (color!=null&&autofill) {
			r.setColor(color);
			r.drawFilledRect(bounds.x, bounds.y, bounds.width, bounds.height);
		}
		GUIRenderer r2 = r.getSubcontext(bounds.x, bounds.y, bounds.width, bounds.height);
		drawInternal(r2, data);
		restoreData(data);
	}
	
	protected void drawInternal(GUIRenderer r, DrawData data) {
		drawChildren(r, data);
	}
	
	@Override public void attribute(String name, Object attr) {
		super.attribute(name, attr);
		if (name=="position") this.location = (Location) attr;
		if (name=="size") this.size = (Location) attr;
		if (name=="offset") this.offset = (Offset) attr;
		if (name=="setcursor") this.forceCursor = (boolean) attr;
		if (name=="outline") {} //TODO
	}
	
	@Override public MouseBinding boundBind(MouseBinding binding) {
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
	}
}
