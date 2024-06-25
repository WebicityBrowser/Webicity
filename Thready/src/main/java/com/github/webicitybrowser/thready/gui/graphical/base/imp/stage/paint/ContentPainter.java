package com.github.webicitybrowser.thready.gui.graphical.base.imp.stage.paint;

import java.util.List;
import java.util.Stack;

import com.github.webicitybrowser.thready.dimensions.AbsolutePosition;
import com.github.webicitybrowser.thready.dimensions.AbsoluteSize;
import com.github.webicitybrowser.thready.dimensions.Rectangle;
import com.github.webicitybrowser.thready.dimensions.util.AbsoluteDimensionsMath;
import com.github.webicitybrowser.thready.drawing.core.Canvas2D;
import com.github.webicitybrowser.thready.drawing.core.ChildCanvasSettings;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.composite.CompositeLayer;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.paint.LocalPaintContext;
import com.github.webicitybrowser.thready.windowing.core.ScreenContent.ScreenContentRedrawContext;

public final class ContentPainter {
	
	private ContentPainter() {}

	public static void performPaintCycle(ScreenContentRedrawContext redrawContext, List<CompositeLayer> compositeLayers) {
		clearPaint(redrawContext);
		paintCompositeLayers(redrawContext, compositeLayers);
	}

	private static void clearPaint(ScreenContentRedrawContext redrawContext) {
		// TODO: What if we have a transparent window?
		// The default paint is white
		AbsoluteSize contentSize = redrawContext.contentSize();
		Canvas2D canvas = redrawContext.canvas();
		canvas.drawRect(0, 0, contentSize.width(), contentSize.height());
	}

	private static void paintCompositeLayers(ScreenContentRedrawContext redrawContext, List<CompositeLayer> compositeLayers) {
		Canvas2D rootCanvas = createRootCanvas(redrawContext);

		Stack<LayerData> layerDataStack = new Stack<>();
		layerDataStack.push(new LayerData(AbsolutePosition.ZERO_POSITION, AbsolutePosition.ZERO_POSITION, rootCanvas));

		for (CompositeLayer layer : compositeLayers) {
			pushLayerData(layerDataStack, layer);
			AbsolutePosition position = layerDataStack.peek().position();
			AbsolutePosition viewportTranslate = getViewportTranslate(layerDataStack, position, layer);
			Canvas2D layerCanvas = layerDataStack.peek().canvas();

			AbsoluteSize size = layer.getBounds().size();
			layer.paint(
				new PaintContextImp(redrawContext.invalidationScheduler()),
				new LocalPaintContext(layerCanvas, new Rectangle(position, size)),
				new Rectangle(viewportTranslate, size));
		}
	}

	private static Canvas2D createRootCanvas(ScreenContentRedrawContext redrawContext) {
		AbsolutePosition position = redrawContext.rootDocumentRect().position();
		AbsoluteSize size = redrawContext.contentSize();
		return redrawContext
			.canvas()
			.createClippedCanvas(
				position.x(), position.y(),
				size.width(), size.height(),
				new ChildCanvasSettings(false)
			);
	}

	private static void pushLayerData(Stack<LayerData> layerDataStack, CompositeLayer layer) {
		while (layerDataStack.size() > layer.getStackLevel() + 1) {
			layerDataStack.pop();
		}

		AbsolutePosition position = getLayerPosition(layerDataStack, layer);
		Canvas2D canvas = getLayerCanvas(layerDataStack, position, layer);

		layerDataStack.push(new LayerData(position, AbsolutePosition.ZERO_POSITION, canvas));
	}

	private static AbsolutePosition getLayerPosition(Stack<LayerData> layerDataStack, CompositeLayer layer) {
		AbsolutePosition offsetPosition = layer.getBounds().position();
		AbsolutePosition parentPosition = layerDataStack.peek().position();
		AbsolutePosition adjustedPosition = switch (layer.getParameters().reference()) {
			case PARENT -> AbsoluteDimensionsMath.sum(parentPosition, offsetPosition, AbsolutePosition::new);
			case VIEWPORT -> offsetPosition;
			case SCROLLPORT -> throw new UnsupportedOperationException("Not implemented yet");
		};
		
		return adjustedPosition;
	}

	private static Canvas2D getLayerCanvas(Stack<LayerData> layerDataStack, AbsolutePosition position, CompositeLayer layer) {
		Canvas2D rootCanvas = layerDataStack.firstElement().canvas();
		Canvas2D parentCanvas = layerDataStack.peek().canvas();
		AbsoluteSize size = layer.getBounds().size();
		Canvas2D childCanvas = switch (layer.getParameters().reference()) {
			case PARENT -> createChildCanvas(parentCanvas, position, size, true);
			case VIEWPORT -> createChildCanvas(rootCanvas, position, size, false);
			case SCROLLPORT -> throw new UnsupportedOperationException("Not implemented yet");
		};

		AbsolutePosition translation = layer.getParameters().translate().get();
		childCanvas = childCanvas.createTranslatedCanvas(translation.x(), translation.y());
		
		return childCanvas;
	}

	private static AbsolutePosition getViewportTranslate(Stack<LayerData> layerDataStack, AbsolutePosition position, CompositeLayer layer) {
		AbsolutePosition parentViewportTranslate = layerDataStack.peek().viewportTranslate();
		AbsolutePosition rawViewportTranslate = layer.getParameters().translate().get();
		AbsolutePosition adjustedViewportTranslate = switch (layer.getParameters().reference()) {
			case PARENT -> AbsoluteDimensionsMath.sum(parentViewportTranslate, rawViewportTranslate, AbsolutePosition::new);
			case VIEWPORT -> rawViewportTranslate;
			case SCROLLPORT -> throw new UnsupportedOperationException("Not implemented yet");
		};

		return adjustedViewportTranslate;
	}

	private static Canvas2D createChildCanvas(Canvas2D canvas, AbsolutePosition position, AbsoluteSize size, boolean clipped) {
		return canvas.createClippedCanvas(
			position.x(), position.y(),
			size.width(), size.height(),
			new ChildCanvasSettings(false)
		);
	}

	private static record LayerData(AbsolutePosition position, AbsolutePosition viewportTranslate, Canvas2D canvas) {} 

}
