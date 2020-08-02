package everyos.browser.webicity.webribbon.shape;

public class Position {
	public int x;
	public int y;

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
}
