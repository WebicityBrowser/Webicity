package everyos.engine.ribbon.renderer.skijarenderer;

import java.util.Objects;

public class FontInfo {
	private final String name;
	private final int weight;
	private final int size;

	public FontInfo(String name, int weight, int size) {
		this.name = name;
		this.weight = weight;
		this.size = size;
	}
	
	public int getWeight() {
		return this.weight;
	}
	
	public String getName() {
		return this.name;
	}
	
	public int getSize() {
		return this.size;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(name, weight, size);
	}
	
	@Override
	public boolean equals(Object o) {
		if (!(o instanceof FontInfo)) {
			return false;
		}
		
		FontInfo other = (FontInfo) o;
		
		return
			other.getWeight() == this.weight &&
			other.getName().equals(this.name) &&
			other.getSize() == this.size;
	}
}
