package everyos.engine.ribbon.core.graphics.font;

import java.io.InputStream;
import java.util.Objects;

public class FontInfoImp implements FontInfo {
	
	private final String name;
	private final int size;
	private final int weight;

	public FontInfoImp(String name, int size, int weight) {
		this.name = name;
		this.size = size;
		this.weight = weight;
	}
	
	@Override
	public String getName() {
		return this.name;
	}
	
	@Override
	public InputStream getData() {
		return null;
	}
	
	@Override
	public int getSize() {
		return this.size;
	}
	
	@Override
	public int getWeight() {
		return this.weight;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(name, size, weight);
	}
	
	@Override
	public boolean equals(Object o) {
		if (!(o instanceof FontInfo)) {
			return false;
		}
		
		FontInfo other = (FontInfo) o;
		
		return
			other.getName().equals(this.name) &&
			other.getSize() == this.size &&
			other.getWeight() == this.weight;
	}
	
}
