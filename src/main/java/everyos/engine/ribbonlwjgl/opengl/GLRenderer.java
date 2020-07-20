package everyos.engine.ribbonlwjgl.opengl;

import everyos.engine.ribbon.graphics.Color;
import everyos.engine.ribbon.graphics.FontStyle;
import everyos.engine.ribbon.graphics.Renderer;

public abstract class GLRenderer implements Renderer {

	@Override
	public Renderer getSubcontext(int x, int y, int l, int h) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Renderer getBufferedSubcontext(int x, int y, int width, int height) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void drawFilledRect(int x, int y, int width, int height) {
		// TODO Auto-generated method stub

	}

	@Override
	public void drawEllipse(int x, int y, int l, int h) {
		// TODO Auto-generated method stub

	}

	@Override
	public void drawLine(int x, int y, int l, int h) {
		// TODO Auto-generated method stub

	}

	@Override
	public void drawText(int x, int y, String text) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setColor(Color color) {
		// TODO Auto-generated method stub

	}

	@Override
	public void draw() {
		// TODO Auto-generated method stub

	}

	@Override
	public int getFontHeight() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getFontPaddingHeight() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int charWidth(int ch) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setFont(String name, FontStyle style, int size) {
		// TODO Auto-generated method stub

	}

}
