package com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.box;

import java.util.ArrayList;
import java.util.List;

import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.pipeline.BoundBox;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.PrerenderMessage;

public interface WrapperBox extends Box {
    
    BoundBox<?, ?> innerBox();

    WrapperBox rewrap(BoundBox<?, ?> newInnerBox);

    default boolean isFluid() {
        return innerBox().getRaw().isFluid();
    };

    default BoundBox<?, ?> innerMostBox() {
        BoundBox<?, ?> innerBox = innerBox();
        if (innerBox.getRaw() instanceof WrapperBox) {
            return ((WrapperBox) innerBox.getRaw()).innerMostBox();
        } else {
            return innerBox;
        }
    };

	@Override
	default void message(PrerenderMessage message) {
		innerBox().getRaw().message(message);
	}

    @Override
    @SuppressWarnings("unchecked")
    default List<BoundBox<?, ?>> getAdjustedBoxTree(BoundBox<?, ?> self) {
        List<BoundBox<?, ?>> innerBoxes = innerBox().getAdjustedBoxTree();
        List<BoundBox<?, ?>> wrappedBoxes = new ArrayList<>(innerBoxes.size());
        for (BoundBox<?, ?> innerBox : innerBoxes) {
            wrappedBoxes.add(((BoundBox<WrapperBox, ?>) self).offshoot(rewrap(innerBox)));
        }

        return wrappedBoxes;
    };

}
