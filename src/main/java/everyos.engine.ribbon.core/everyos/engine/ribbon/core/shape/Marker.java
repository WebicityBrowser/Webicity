package everyos.engine.ribbon.core.shape;

public class Marker {
	private final Marker parent;
	
	private int
		lowX = Integer.MAX_VALUE,
		lowY = Integer.MAX_VALUE,
		highX = 0,
		highY = 0;

	public Marker(Marker parent) {
		this.parent = parent;
	}

	public Marker end() {
		if (parent != null) {
			parent.mergeX(lowX, highX);
			parent.mergeY(lowY, highY);
		}
		
		return parent;
	}

	public void mergeX(int lowX2, int highX2) {
		lowX = Math.min(lowX, lowX2);
		highX = Math.max(highX, highX2);
	}
	
	public void mergeY(int lowY2, int highY2) {
		lowY = Math.min(lowY, lowY2);
		highY = Math.max(highY, highY2);
	}

	public Rectangle getHitbox() {
		if (highX < lowX || highY < lowY) {
			return new Rectangle(0, 0, 0, 0);
		}
		return new Rectangle(lowX, lowY, highX-lowX, highY-lowY);
	}
}
