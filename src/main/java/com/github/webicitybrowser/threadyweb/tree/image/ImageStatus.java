package com.github.webicitybrowser.threadyweb.tree.image;

import com.github.webicitybrowser.webicity.core.image.ImageData;

public record ImageStatus(boolean canImageBeShown, ImageData imageData, String imageAltText) {

}
