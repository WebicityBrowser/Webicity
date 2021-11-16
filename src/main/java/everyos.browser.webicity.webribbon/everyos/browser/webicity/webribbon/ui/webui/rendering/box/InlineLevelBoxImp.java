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
import everyos.browser.webicity.webribbon.gui.box.MutableBox;
import everyos.engine.ribbon.core.rendering.RendererData;
import everyos.engine.ribbon.core.shape.Dimension;
import everyos.engine.ribbon.core.shape.Position;
import everyos.engine.ribbon.core.shape.Rectangle;
import everyos.engine.ribbon.core.shape.SizePosGroup;


//TODO: Inline graphics are very broken
public class InlineLevelBoxImp extends MutableBoxBase implements InlineLevelBox {
	
	private final Content content;
	private final MutableBox parent;
	private final List<Box> children;
	
	private boolean containsBlockElements = false;

	public InlineLevelBoxImp(MutableBox parent, Content content) {
		this(parent, content, new ArrayList<Box>(1));
	}
	
	private InlineLevelBoxImp(MutableBox parent, Content content, List<Box> children) {
		this.content = content;
		this.parent = parent;
		this.children = children;
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
		if (parent == null) {
			return;
		}
		
		if (containsBlockElements) {
			List<Box> integrated = new ArrayList<>();
			MutableBox blockBox = null;
			for (int i = 0; i < children.size(); i++) {
				Box child = children.get(i);
				if (child instanceof InlineLevelBox) {
					if (blockBox == null) {
						blockBox = new BlockLevelBoxImp(this, content.split());
						blockBox.setProperties(getProperties());
						integrated.add(blockBox);
					}
					blockBox.add(child);
				} else {
					blockBox = null;
					integrated.add(child);
				}
			}
			parent.integrate(integrated.toArray(new Box[integrated.size()]));
		} else {
			parent.add(this);
		}
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
		content.paint(this, rd, viewport, context);
	}
	
	@Override
	public InlineLevelBox[] split(RendererData rd, int width, WebRenderContext context, boolean first) {
		//TODO: Why is some text not appearing
		int totalWidth = 0;
		int totalHeight = 0;
		
		List<Box> preSplit = new ArrayList<>();
		List<Box> postSplit = new ArrayList<>();
		
		int i = 0;
		while (i < children.size()) {
			InlineLevelBox[] split = ((InlineLevelBox) children.get(i))
				.split(rd, width - totalWidth, context, first);
			i++;
			
			InlineLevelBox firstLine = split[0];
			
			if (first) {
				first = false;
			}
			
			if (firstLine != null) {
				Dimension firstLineSize = firstLine.getFinalSize();
				totalWidth += firstLineSize.getWidth();
				totalHeight = totalHeight < firstLineSize.getHeight() ?
					firstLineSize.getHeight() :
					totalHeight;
				
				//TODO: ProxyBoxes *really* aren't the best (or most reliable) solution.
				preSplit.add(new ProxyBox(firstLine));
			}
			
			if (split[1] != null) {
				postSplit.add(split[1]);
				break;
			}
			
			if (firstLine == null) {
				break;
			}
		}
		
		for (int j = i; j < children.size(); j++) {
			//TODO: Avoid this copy-op
			postSplit.add(children.get(j));
		}
		
		InlineLevelBox firstLine = createBoxFor(preSplit);
		if (firstLine != null) {
			firstLine.setFinalPos(new Position(0, 0));
			firstLine.setFinalSize(new Dimension(totalWidth, totalHeight));
			firstLine.render(rd, new SizePosGroup(width, 0, totalWidth, 0, width, -1), context);
		}
		
		return new InlineLevelBox[] {
			firstLine,
			createBoxFor(postSplit)
		};
	}
	
	@Override
	public CullingFilter getPaintCullingFilter() {
		// TODO
		return vp -> true;
	}

	private InlineLevelBox createBoxFor(List<Box> boxes) {
		if (boxes.size() == 0) {
			return null;
		}
		
		//TODO: Split the content?
		InlineLevelBox box = new InlineLevelBoxImp(parent, content.split(), boxes);
		box.setProperties(getProperties());
		return box;
	}

}
