package everyos.desktop.thready.renderer.skija.canvas;

import everyos.desktop.thready.core.graphics.canvas.Canvas2D;
import everyos.desktop.thready.core.graphics.canvas.Canvas2DSettings;
import everyos.desktop.thready.core.graphics.canvas.Paint2D;
import everyos.desktop.thready.core.graphics.image.Texture;
import io.github.humbleui.skija.Canvas;
import io.github.humbleui.skija.DirectContext;
import io.github.humbleui.skija.Paint;
import io.github.humbleui.types.Rect;

public class SkijaCanvas2D implements Canvas2D {
	
	private final Canvas canvas;
	private final DirectContext directContext;
	
	private Paint paint;

	public SkijaCanvas2D(Canvas canvas, DirectContext directContext, Paint2D paint) {
		this.canvas = canvas;
		this.directContext = directContext;
		this.paint = createPaint(paint);
	}

	@Override
	public void drawRect(float x, float y, float l, float h) {
		canvas.drawRect(new Rect(x, y, x + l, y + h), paint);
	}

	@Override
	public void drawEllipse(float x, float y, float l, float h) {
		canvas.drawArc(x, y, x + l, y + h, 0, 2 * (float) Math.PI, false, paint);
	}

	@Override
	public void drawLine(float x, float y, float l, float h) {
		canvas.drawLine(x, y, x + l, y + h, paint);
	}

	@Override
	public void drawTriangle(float p1x, float p1y, float p2ox, float p2oy, float p3ox, float p3oy) {
		// TODO Auto-generated method stub
	}
	
	@Override
	public void drawTexture(float x, float y, Texture image) {
		drawTexture(x, y, image.getNaturalWidth(), image.getNaturalHeight(), image);
	}

	@Override
	public void drawTexture(float x, float y, float l, float h, Texture image) {
		// TODO Auto-generated method stub
	}

	@Override
	public void drawText(float x, float y, String text) {
		drawCharacters(x, y, text.toCharArray());
	}

	@Override
	public void drawCharacters(float x, float y, char[] chars) {
		// TODO Auto-generated method stub
	}

	@Override
	public Canvas2D createChildCanvas(float x, float y, float l, float h, Canvas2DSettings settings) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Canvas2D withPaint(Paint2D paint) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setPaint(Paint2D paint) {
		// TODO Auto-generated method stub
	}
	
	@Override
	public Paint2D getPaint() {
		// TODO Auto-generated method stub
		return null;
	}
	
	private Paint createPaint(Paint2D paint2) {
		// TODO Auto-generated method stub
		return null;
	}

}
