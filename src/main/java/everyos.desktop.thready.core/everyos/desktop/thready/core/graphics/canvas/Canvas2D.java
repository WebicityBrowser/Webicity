package everyos.desktop.thready.core.graphics.canvas;

import everyos.desktop.thready.core.graphics.image.LoadedImage;

public interface Canvas2D {

	void drawRect(float x, float y, float l, float h);
	
	void drawEllipse(float x, float y, float l, float h);
	
	void drawLine(float x, float y, float l, float h);
	
	void drawTriangle(float p1x, float p1y, float p2ox, float p2oy, float p3ox, float p3oy);
	
	void drawTexture(float x, float y, LoadedImage image);
	
	void drawTexture(float x, float y, float l, float h, LoadedImage image);
	
	void drawText(float x, float y, String text);
	
	void drawCharacters(float x, float y, char[] chars);
	
	Canvas2D createChildCanvas(float x, float y, float l, float h, Canvas2DSettings settings);
	
	Canvas2D withPaint(Paint2D paint);
	
	void setPaint(Paint2D paint);
	
	Paint2D getPaint();
	
}
