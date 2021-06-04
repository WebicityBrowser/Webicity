package everyos.engine.ribbon.core.shape;

public class SizePosGroup {
	private MutableDimension size;
	private Dimension maxSize;
	private int x;
	private int y;
	private int ny;

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
		this(new Dimension(width, height), x, y, -1, -1);
	}

	public void setMinLineHeight(int ny) {
		if (ny>this.ny) this.ny = ny;
		normalizeY();
	}
	
	public boolean move(int x, boolean forceInline) {
		if (!forceInline && maxSize.getWidth()!=-1 && this.x+x>maxSize.getWidth()) {
			//Refuse
			return false;
		}
		
		this.x += x;
		normalizeX();
		
		return true;
	}
	
	private void normalizeX() {	
		if (x>size.getWidth()) {
			size.setWidth((x<maxSize.getWidth()||maxSize.getWidth()==-1)?x:maxSize.getWidth());
		}
		if (x>maxSize.getWidth()&&maxSize.getWidth()!=-1) nextLine();
	}
	
	private void normalizeY() {
		if (this.y+this.ny>size.getHeight()) {
			size.setHeight(this.y+this.ny);
			if (maxSize.getHeight()!=-1&&size.getHeight()>maxSize.getHeight()) {
				size.setHeight(maxSize.getHeight());
			}
		}
	}

	public void nextLine() {
		y = y + ny;
		x = 0;
		ny = 0;
	}

	public void add(Dimension s) {
		this.x+=s.getWidth();
		this.setMinLineHeight(s.getHeight());
		normalizeX();
		normalizeY();
	}

	public void min(Dimension s) {
		this.size.setWidth(this.size.getWidth()<s.getWidth()?s.getWidth():this.size.getWidth());
		this.size.setHeight(this.size.getHeight()<s.getHeight()?s.getHeight():this.size.getHeight());
		normalizeX();
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
}
