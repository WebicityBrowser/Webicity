package everyos.engine.ribbon.core.shape;

public class SizePosGroup {
	private final MutableDimension size;
	private final Dimension maxSize;
	
	private Marker marker;
	private int x;
	private int y;
	private int currentLineHeight;

	public SizePosGroup(Dimension size, int x, int y, int mw, int mh) {
		this.size = size.toMutable();
		this.x = x;
		this.y = y;
		this.maxSize = new Dimension(mw, mh);
		
		this.marker = new Marker(null);
	}
	
	public SizePosGroup(int width, int height, int x, int y, int mw, int mh) {
		this(new Dimension(width, height), x, y, mw, mh);
	}
	
	public SizePosGroup(int width, int height, int x, int y) {
		this(new Dimension(width, height), x, y, width, height);
	}

	public void setMinLineHeight(int minLineHeight) {
		if (minLineHeight > this.currentLineHeight) {
			this.currentLineHeight = minLineHeight;
			normalizeY();
		}
	}
	
	public int getMinLineHeight() {
		return this.currentLineHeight;
	}
	
	public boolean move(int x, boolean forceInline) {
		boolean willExtendCurrentSizeWidth = size.getWidth()!=-1 && this.x+x > size.getWidth();
		boolean willExceedMaxSizeWidth = maxSize.getWidth() != -1 && this.x+x > maxSize.getWidth();
		if (!forceInline && (willExtendCurrentSizeWidth || willExceedMaxSizeWidth)) {
			//Refuse
			return false;
		}
		
		this.x += x;
		
		normalizeX(forceInline);
		
		return true;
	}
	
	public void moveY(int y) {
		this.y += y;
		this.currentLineHeight -= y;
		
		normalizeY();
	}
	
	public void nextLine() {
		y = y + currentLineHeight;
		x = 0;
		currentLineHeight = 0;
		
		marker.mergeX(x, x);
		marker.mergeY(y, y);
	}
	
	public void add(Dimension s) {
		this.x+=s.getWidth();
		this.setMinLineHeight(s.getHeight());
		normalizeX(true);
		normalizeY();
	}

	public void min(Dimension s) {
		size.setWidth(Math.max(s.getWidth(), size.getWidth()));
		size.setHeight(Math.max(s.getHeight(), size.getHeight()));
		
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
		
		if (x > size.getWidth()) {
			size.setWidth(
				(x<maxSize.getWidth() || maxSize.getWidth()==-1) ?
					x :
					maxSize.getWidth());
		}
		
		boolean posXExceedsMaxWidth = maxSize.getWidth() != -1 && x > maxSize.getWidth();
		if (posXExceedsMaxWidth && !forceInline) {
			nextLine();
		}
	}
	
	private void normalizeY() {
		marker.mergeY(y, y+this.currentLineHeight);
		
		if (this.y+this.currentLineHeight > size.getHeight()) {
			size.setHeight(this.y+this.currentLineHeight);
			boolean currentHeightExceedsMaxHeight = maxSize.getHeight() != -1 && size.getHeight() > maxSize.getHeight();
			if (currentHeightExceedsMaxHeight) {
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