package everyos.browser.webicity.webribbon.shape;

import everyos.engine.ribbon.renderer.guirenderer.shape.Dimension;

public class SizePosGroup {
	public Position position;
	public Position pointer;
	public Dimension size;
	public int preferredWidth;
	private int largeSize = 0;
	private int minIncrease = 0;

	public SizePosGroup(Position position, Dimension size, int parentPreferred, Position pointer) {
		this.position = position;
		this.size = size;
		this.pointer = pointer;
		this.largeSize = 0;
		this.preferredWidth = size.width!=-1?size.width:(parentPreferred-position.x);
	}
	
	public SizePosGroup(Position position, Dimension size, int parentPreferred) {
		this(position, size, parentPreferred, new Position(0, 0));
	}

	public SizePosGroup(SizePosGroup sizepos, Dimension size) {
		this(sizepos.pointer(), size, sizepos.preferredWidth);
	}

	public void finished() {
		if (size.width==-1) size.width = pointer.x>largeSize?pointer.x:largeSize;
		if (size.height==-1) size.height = pointer.y+minIncrease;
	}

	public void minIncrease(int height) {
		minIncrease = minIncrease>height?minIncrease:height;
	}
	public void nextLine() {
		largeSize=pointer.x>largeSize?pointer.x:largeSize;
		pointer.x = 0;
		pointer.y += minIncrease;
		minIncrease = 0;
	}

	public Position position() {
		return position.copy();
	}
	public Position pointer() {
		return pointer.copy();
	}
}
