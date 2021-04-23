package everyos.browser.webicity.webribbon.gui.shape;

public class Position {
	private int x;
	private int y;

	public Position() {
		this.x = 0;
		this.y = 0;
	}
	
	public Position(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public Position copy() {
		return new Position(x, y);
	}

	public int getX() {
		return this.x;
	}

	public int getY() {
		return this.y;
	}
}
