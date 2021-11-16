package everyos.browser.webicity.webribbon.ui.webui.rendering.box;

import java.util.ArrayList;
import java.util.List;

import everyos.browser.webicity.webribbon.gui.Content;
import everyos.browser.webicity.webribbon.gui.WebPaintContext;
import everyos.browser.webicity.webribbon.gui.WebRenderContext;
import everyos.browser.webicity.webribbon.gui.box.BlockLevelBox;
import everyos.browser.webicity.webribbon.gui.box.Box;
import everyos.browser.webicity.webribbon.gui.box.CullingFilter;
import everyos.browser.webicity.webribbon.gui.box.InlineLevelBox;
import everyos.browser.webicity.webribbon.gui.box.MutableBlockLevelBox;
import everyos.browser.webicity.webribbon.gui.box.MutableBox;
import everyos.browser.webicity.webribbon.ui.webui.display.inner.flow.FlowContent;
import everyos.engine.ribbon.core.rendering.RendererData;
import everyos.engine.ribbon.core.shape.Dimension;
import everyos.engine.ribbon.core.shape.Position;
import everyos.engine.ribbon.core.shape.Rectangle;
import everyos.engine.ribbon.core.shape.SizePosGroup;
import everyos.engine.ribbon.ui.simple.helper.RectangleBuilder;

public class BlockLevelBoxImp extends MutableBoxBase implements MutableBlockLevelBox {

	private final Content content;
	private final MutableBox parent;
	private final List<Box> children;
	
	private boolean containsBlockElements = false;

	public BlockLevelBoxImp(MutableBox parent, Content content) {
		this(parent, content, new ArrayList<Box>(1));
	}
	
	public BlockLevelBoxImp(MutableBox parent, Content content, List<Box> backeningList) {
		this.content = content;
		this.parent = parent;
		this.children = backeningList;
	}
	
	@Override
	public void add(Box box) {
		children.add(box);
		if (box instanceof BlockLevelBox) {
			containsBlockElements = true;
		}
	}

	@Override
	public void finish() {
		if (containsBlockElements) {
			blockInlineElements();
		}
		
		if (parent == null) {
			return;
		}
		
		parent.add(this);
	}
	
	@Override
	public Box[] getChildren() {
		return children.toArray(new Box[children.size()]);
	}
	
	@Override
	public void render(RendererData rd, SizePosGroup childSize, WebRenderContext context) {
		content.render(this, rd, childSize, context);
	}
	
	@Override
	public void paint(RendererData rd, Rectangle viewport, WebPaintContext context) {
		content.paint(this, rd, intersect(viewport), context);
	}
	
	private void blockInlineElements() {
		int listSize = children.size();
		for (int i = 0; i < listSize; i++) {
			Box child = children.get(i);
			if (children.get(i) instanceof InlineLevelBox) {
				BlockLevelBox blockBox = new BlockLevelBoxImp(this, new FlowContent(), List.of(child));
				blockBox.setProperties(getProperties());
				children.set(i, blockBox);
			}
		}
	}

	@Override
	public CullingFilter getPaintCullingFilter() {
		// TODO
		Position pos = getFinalPos();
		Dimension size = getFinalSize();
		return vp -> vp.intersects(new Rectangle(
			pos.getX(), pos.getY(),
			size.getWidth(), size.getHeight()));
	}


	private Rectangle intersect(Rectangle viewport) {
		Position pos = getFinalPos();
		Dimension size = getFinalSize();
		
		//AABB based culling
		RectangleBuilder vpBuilder = new RectangleBuilder(
			pos.getX(), pos.getY(),
			size.getWidth(), size.getHeight());
		
		// Perform an intersect
		Rectangle intersected = viewport; //vpBuilder.build().intersect(viewport);
		vpBuilder.setWidth(intersected.getWidth());
		vpBuilder.setHeight(intersected.getHeight());
				
		// Origin should be 0, 0
		vpBuilder.setX(intersected.getX() - pos.getX());
		vpBuilder.setY(intersected.getY() - pos.getY());
		
		// And scroll
		vpBuilder.setY(vpBuilder.getY());
		
		return vpBuilder.build();
	}
}
