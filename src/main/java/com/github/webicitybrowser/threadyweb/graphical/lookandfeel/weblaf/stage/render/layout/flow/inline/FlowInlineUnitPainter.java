package com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.stage.render.layout.flow.inline;

import com.github.webicitybrowser.thready.dimensions.AbsolutePosition;
import com.github.webicitybrowser.thready.dimensions.Rectangle;
import com.github.webicitybrowser.thready.dimensions.util.AbsolutePositionMath;
import com.github.webicitybrowser.thready.dimensions.util.RectangleUtil;
import com.github.webicitybrowser.thready.drawing.core.Canvas2D;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.box.Box;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.paint.PaintContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.paint.Painter;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.unit.Unit;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.stage.paint.BackgroundPainter;

public class FlowInlineUnitPainter implements Painter {

	private final Rectangle documentRect;
	private final RenderedInlineUnit[] children;
	private final BackgroundPainter backgroundPainter;

	public FlowInlineUnitPainter(Box box, Rectangle documentRect, RenderedInlineUnit[] children) {
		this.documentRect = documentRect;
		this.children = children;
		this.backgroundPainter = new BackgroundPainter(box, documentRect);
	}

	@Override
	public void paint(PaintContext context, Canvas2D canvas, Rectangle viewport) {
		backgroundPainter.paint(context, canvas, viewport);
		paintChildren(context, canvas, viewport);
	}

	private void paintChildren(PaintContext context, Canvas2D canvas, Rectangle viewport) {
		for (RenderedInlineUnit child: children) {
			if (childInViewport(child, viewport)) {
				paintChild(child, context, canvas, viewport);
			}
		}
	}

	private void paintChild(RenderedInlineUnit child, PaintContext context, Canvas2D canvas, Rectangle viewport) {
		Unit childUnit = child.unit();
		AbsolutePosition childPosition = AbsolutePositionMath.sum(documentRect.position(), child.position());
		Rectangle childDocumentRect = new Rectangle(childPosition, child.size());
		childUnit.getPainter(childDocumentRect).paint(context, canvas, viewport);
	}
	
	private boolean childInViewport(RenderedInlineUnit child, Rectangle viewport) {
		AbsolutePosition childPosition = AbsolutePositionMath.sum(documentRect.position(), child.position());
		Rectangle childRectangle = new Rectangle(childPosition, child.unit().getMinimumSize());
		return RectangleUtil.intersects(childRectangle, viewport);
	}

}
