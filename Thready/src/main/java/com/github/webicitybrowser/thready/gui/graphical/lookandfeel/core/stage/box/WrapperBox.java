package com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.box;

import java.util.ArrayList;
import java.util.List;

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
	default List<Box> getAdjustedBoxTree() {
		List<Box> innerBoxes = innerBox().getAdjustedBoxTree();
		List<Box> wrappedBoxes = new ArrayList<>(innerBoxes.size());
		for (Box innerBox : innerBoxes) {
			// TODO: Not very elegant, very error prone
			// This is a temporary way to handle moved boxes that should not have additional wrappers
			// It is built on some assumptions that will not hold in the future
			if (innerBox instanceof WrapperBox) {
				wrappedBoxes.add(innerBox); 
			} else {
				wrappedBoxes.add(rewrap(innerBox));
			}
		}

		return wrappedBoxes;
	};

}
