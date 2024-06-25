package com.github.webicitybrowser.thready.windowing.skija.imp;

import com.github.webicitybrowser.thready.dimensions.AbsolutePosition;
import com.github.webicitybrowser.thready.dimensions.Rectangle;

public record SkijaCanvasSettings(AbsolutePosition offset, Rectangle clipBounds, AbsolutePosition translation) {

}
