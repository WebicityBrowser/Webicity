package com.github.webicitybrowser.thready.gui.graphical.lookandfeel.simplelaf.display.wrapper;

import com.github.webicitybrowser.thready.color.format.ColorFormat;
import com.github.webicitybrowser.thready.dimensions.AbsoluteSize;
import com.github.webicitybrowser.thready.gui.graphical.directive.BackgroundColorDirective;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.UIDisplay;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.box.Box;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.unit.RenderedUnit;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.style.StyleReference;

public class SimpleWrapperUnit<V extends RenderedUnit> implements RenderedUnit {

	private final UIDisplay<?, ?, ?> display;
	private final AbsoluteSize fitSize;
	private final V childUnit;

	private final StyleReference<BackgroundColorDirective> backgroundColorReference;

	public SimpleWrapperUnit(UIDisplay<?, ?, ?> display, AbsoluteSize fitSize, V childUnit) {
		this.display = display;
		this.fitSize = fitSize;
		this.childUnit = childUnit;

		this.backgroundColorReference = childUnit.componentUI().getStyleReference(BackgroundColorDirective.class);
	}

	@Override
	public UIDisplay<?, ?, ?> display() {
		return display;
	}

	@Override
	public AbsoluteSize fitSize() {
		return fitSize;
	}

	@Override
	public Box box() {
		return childUnit.box();
	}

	public V childUnit() {
		return childUnit;
	}

    public ColorFormat backgroundColor() {
        return backgroundColorReference.get().getColor();
    }

}
