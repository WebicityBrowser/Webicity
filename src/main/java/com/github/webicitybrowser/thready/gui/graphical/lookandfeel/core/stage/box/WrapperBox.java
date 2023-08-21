package com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.box;

import java.util.ArrayList;
import java.util.List;

import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.PrerenderMessage;

public interface WrapperBox extends Box {
	
	Box innerBox();

	WrapperBox rewrap(Box newInnerBox);

	default boolean isFluid() {
		return innerBox().isFluid();
	};

	default Box innerMostBox() {
		Box innerBox = innerBox();
		if (innerBox instanceof WrapperBox innerBoxWrapper) {
			return innerBoxWrapper.innerMostBox();
		} else {
			return innerBox;
		}
	};

	@Override
	default void message(PrerenderMessage message) {
		innerBox().message(message);
	}

	@Override
	default List<Box> getAdjustedBoxTree() {
		List<Box> innerBoxes = innerBox().getAdjustedBoxTree();
		List<Box> wrappedBoxes = new ArrayList<>(innerBoxes.size());
		for (Box innerBox : innerBoxes) {
			wrappedBoxes.add(rewrap(innerBox));
		}

		return wrappedBoxes;
	};

}
