package com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.context;

import java.util.Optional;

import com.github.webicitybrowser.thready.gui.directive.core.style.StyleGenerator;

public record LocalContextContext<T extends Context>(StyleGenerator styleGenerator, Optional<T> prevContext) {
	
}
