package everyos.parser.portalhtml;

public class BufferPositionTracker {

	private int position;
	private int column;
	private int row;
	private int character;
	
	public BufferPosition getCurrentPosition() {
		return new BufferPosition(position, column, row, character);
	}

	public void recordCharacter(int ch) {
		position++;
		character = ch;
		if (ch == '\n' && column >= 0) {
			column = 0;
			row++;
		} else {
			column++;
		}
	}

	public void backtrack() {
		position--;
		column--;
	}
	
}
