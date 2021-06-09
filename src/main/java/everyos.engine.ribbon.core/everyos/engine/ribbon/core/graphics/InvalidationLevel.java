package everyos.engine.ribbon.core.graphics;

public enum InvalidationLevel {
	IGNORE(0), RENDER(1), PAINT(2), COMPOSITE(3);

	private int level;

	private InvalidationLevel(int level) {
		this.level = level;
	}
	
	public int getLevel() {
		return level;
	}

	public boolean lessThan(InvalidationLevel invalidated) {
		return (this.level == 0 && invalidated.getLevel() != 0) || this.level > invalidated.getLevel();
	}
}
