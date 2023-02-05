package everyos.desktop.thready.renderer.skija;

import everyos.desktop.thready.core.graphics.image.LoadedImage;
import io.github.humbleui.skija.Image;

public class SkijaLoadedImage implements LoadedImage {

	private final Image image;

	public SkijaLoadedImage(Image image) {
		this.image = image;
	}

	@Override
	public float getNaturalWidth() {
		return image.getWidth();
	}

	@Override
	public float getNaturalHeight() {
		return image.getHeight();
	}

	public Image getRawImage() {
		return this.image;
	}

}
