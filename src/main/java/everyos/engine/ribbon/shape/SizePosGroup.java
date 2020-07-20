package everyos.engine.ribbon.shape;

import everyos.engine.ribbon.graphics.ui.Dimension;

public class SizePosGroup {
	public Dimension size;
	public int x;
	public int y;
	private int ny;
	public Dimension maxSize;

	public SizePosGroup(Dimension size, int x, int y, int mw, int mh) {
		this.size = size;
		this.x = x;
		this.y = y;
		this.maxSize = new Dimension(mw, mh);
	}
	
	public SizePosGroup(int width, int height, int x, int y, int mw, int mh) {
		this(new Dimension(width, height), x, y, mw, mh);
	}
	
	public SizePosGroup(int width, int height, int x, int y) {
		this(new Dimension(width, height), x, y, -1, -1);
	}

	public void minIncrease(int ny) {
		if (ny>this.ny) this.ny = ny;
	}
	
	public void normalize() {
		if (x>size.width) {
			size.width=x<maxSize.width||maxSize.width==-1?x:maxSize.width;
		}
		if (x>maxSize.width&&maxSize.width!=-1) nextLine();
		if (this.y+this.ny>size.height) {
			size.height=this.y+this.ny;
			if (maxSize.height!=-1&&size.height>maxSize.height) {
				size.height = maxSize.height;
			}
		}
	}

	public void nextLine() {
		y = y + ny;
		x = 0;
		ny = 0;
	}

	public boolean canSpan(int width) {
		return ((x+width)<=maxSize.width)||(maxSize.width==-1);
	}

	public void add(Dimension s) {
		this.x+=s.width;
		this.minIncrease(s.height);
		normalize();
	}

	public void min(Dimension s) {
		this.size.width=this.size.width<s.width?s.width:this.size.width;
		this.size.height=this.size.height<s.height?s.height:this.size.height;
		normalize();
	}

	public int minIncrease() {
		return ny;
	}
}
