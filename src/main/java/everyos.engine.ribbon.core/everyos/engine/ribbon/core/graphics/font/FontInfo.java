package everyos.engine.ribbon.core.graphics.font;

import java.io.InputStream;

public interface FontInfo {

	String getName();
	InputStream getData();
	int getWeight();
	int getSize();

	public static FontInfo getByName(String name, int size, int weight) {
		return new FontInfoImp(name, size, weight);
	}
	
}
