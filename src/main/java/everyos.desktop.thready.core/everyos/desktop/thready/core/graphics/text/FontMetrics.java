package everyos.desktop.thready.core.graphics.text;

public interface FontMetrics {

	float getCharacterWidth(int codePoint);
	
	float getHeight();

	float getLeading();

	float getStringWidth(String productName);

	float getDescent();

	float getAscent();

	float getCapHeight();

}
