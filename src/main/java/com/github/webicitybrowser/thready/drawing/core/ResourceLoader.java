package com.github.webicitybrowser.thready.drawing.core;

import com.github.webicitybrowser.thready.drawing.core.image.Image;
import com.github.webicitybrowser.thready.drawing.core.image.ImageSource;
import com.github.webicitybrowser.thready.drawing.core.text.Font;
import com.github.webicitybrowser.thready.drawing.core.text.FontSettings;
import com.github.webicitybrowser.thready.drawing.core.text.FontSource;

public interface ResourceLoader {

	Image loadResource(ImageSource source);
	
	Font loadFont(FontSource source, FontSettings settings);
	
}
