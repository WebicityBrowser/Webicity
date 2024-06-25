package com.github.webicitybrowser.codec.image;

import java.util.Optional;

public record PossibleImage(Optional<ImageData> imageData, Optional<Exception> exception) {
	
}
