package everyos.browser.webicity.webribbon.gui.shape;

import everyos.engine.ribbon.core.shape.Dimension;
import everyos.engine.ribbon.core.shape.MutableDimension;

public class SizePosGroup {
	private MutableDimension size;
	private Dimension maxSize;
	private int x;
	private int y;
	private int ny;
	private Marker marker = new Marker(null);

	public SizePosGroup(Dimension size, int x, int y, int mw, int mh) {
		this.size = size.toMutable();
		this.x = x;
		this.y = y;
		this.maxSize = new Dimension(mw, mh);
	}
	
	public SizePosGroup(int width, int height, int x, int y, int mw, int mh) {
		this(new Dimension(width, height), x, y, mw, mh);
	}
	
	public SizePosGroup(int width, int height, int x, int y) {
		this(new Dimension(width, height), x, y, width, height);
	}

	public void setMinLineHeight(int ny) {
		if (ny>this.ny) {
			this.ny = ny;
		}
		normalizeY();
	}
	
	public int getMinLineHeight() {
		return this.ny;
	}
	
	public boolean move(int x, boolean forceInline) {
		if (!forceInline && ((maxSize.getWidth()!=-1 && this.x+x>maxSize.getWidth()) || (size.getWidth()!=-1 && this.x+x>size.getWidth()))) {
			//Refuse
			return false;
		}
		
		this.x += x;
		
		normalizeX(forceInline);
		
		return true;
	}
	
	public void moveY(int y) {
		this.y += y;
		this.ny -= y;
		normalizeY();
	}
	
	public void nextLine() {
		y = y + ny;
		x = 0;
		ny = 0;
		
		marker.mergeX(x, x);
		marker.mergeY(y, y);
	}

	public void min(Dimension s) {
		this.size.setWidth(this.size.getWidth()<s.getWidth()?s.getWidth():this.size.getWidth());
		this.size.setHeight(this.size.getHeight()<s.getHeight()?s.getHeight():this.size.getHeight());
		marker.mergeX(0, size.getWidth());
		marker.mergeY(0, size.getHeight());
		normalizeX(true);
		normalizeY();
	}
	
	public Dimension getSize() {
		return size.freeze();
	}
	
	public Dimension getMaxSize() {
		return maxSize;
	}

	public Position getCurrentPointer() {
		return new Position(x, y);
	}
	
	private void normalizeX(boolean forceInline) {	
		marker.mergeX(x, x);
		if (x>size.getWidth() || (maxSize.getWidth()!=-1 && x>maxSize.getWidth())) {
			size.setWidth((x<maxSize.getWidth()||maxSize.getWidth()==-1)?x:maxSize.getWidth());
		}
		if (((x>size.getWidth()&&size.getWidth()!=-1) || (x>maxSize.getWidth()&&maxSize.getWidth()!=-1)) && !forceInline) {
			nextLine();
		}
	}
	
	private void normalizeY() {
		marker.mergeY(y, y+this.ny);
		
		if (this.y+this.ny>size.getHeight()) {
			size.setHeight(this.y+this.ny);
			if (maxSize.getHeight()!=-1&&size.getHeight()>maxSize.getHeight()) {
				size.setHeight(maxSize.getHeight());
			}
		}
	}

	public void setMarker(Marker marker) {
		this.marker = marker;
		marker.mergeX(x, x);
		marker.mergeY(y, y);
	}

	public Marker getMarker() {
		return this.marker;
	}
}
