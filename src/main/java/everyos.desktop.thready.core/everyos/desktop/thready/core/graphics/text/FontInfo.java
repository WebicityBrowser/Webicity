package everyos.desktop.thready.core.graphics.text;

import java.util.Arrays;
import java.util.Objects;

public record FontInfo(Font font, int fontSize, int fontWeight, FontDecoration[] fontDecorations) {
	
	@Override
	public int hashCode() {
		return Objects.hash(font, fontSize, fontWeight);
	}
	
	@Override
	public boolean equals(Object o) {
		if (!(o instanceof FontInfo)) {
			return false;
		}
		
		FontInfo other = (FontInfo) o;
		return
			other.font().equals(font) &&
			other.fontSize() == fontSize &&
			other.fontWeight() == fontWeight &&
			Arrays.compare(other.fontDecorations(), fontDecorations) == 0;
	}
	
}
