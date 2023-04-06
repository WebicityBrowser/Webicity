package com.github.webicitybrowser.thready.drawing.core;

public interface Canvas2D {

	void drawRect(float x, float y, float l, float h);

	void drawText(float x, float y, String string);

	void setPaint(Paint2D paint);

}
