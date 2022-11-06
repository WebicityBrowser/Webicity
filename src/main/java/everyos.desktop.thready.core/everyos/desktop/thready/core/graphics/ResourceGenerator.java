package everyos.desktop.thready.core.graphics;

import everyos.desktop.thready.core.graphics.image.Image;
import everyos.desktop.thready.core.graphics.image.LoadedImage;
import everyos.desktop.thready.core.graphics.text.FontInfo;
import everyos.desktop.thready.core.graphics.text.LoadedFont;

public interface ResourceGenerator {

	LoadedFont loadFont(FontInfo fontInfo);
	
	LoadedImage loadImage(Image image);
	
}
