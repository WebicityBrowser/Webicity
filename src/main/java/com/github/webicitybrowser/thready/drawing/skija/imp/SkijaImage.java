package com.github.webicitybrowser.thready.drawing.skija.imp;

import com.github.webicitybrowser.thready.drawing.core.image.Image;

public class SkijaImage implements Image {

	private final io.github.humbleui.skija.Image rawImage;

	public SkijaImage(io.github.humbleui.skija.Image rawImage) {
		this.rawImage = rawImage;
	}

	public io.github.humbleui.skija.Image getRawImage() {
		return this.rawImage;
	}

}
