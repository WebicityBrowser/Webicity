package com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.pipeline.imp;

import java.util.List;

import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.UIDisplay;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.pipeline.BoundBox;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.pipeline.BoundRenderedUnitGenerator;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.box.Box;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.box.WrapperBox;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.GlobalRenderContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.LocalRenderContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.unit.RenderedUnit;

public class BoundBoxImp<U extends Box, V extends RenderedUnit> implements BoundBox<U, V> {

	private final U box;
	private final UIDisplay<?, U, V> display;

	public BoundBoxImp(U box, UIDisplay<?, U, V> display) {
		this.box = box;
		this.display = display;
	}
	
	@Override
	public Box getRaw() {
		return this.box;
	}

	@Override
	public BoundRenderedUnitGenerator<?> render(GlobalRenderContext globalRenderContext, LocalRenderContext childRenderContext) {
		return BoundRenderedUnitGenerator.create(display.renderBox(box, globalRenderContext, childRenderContext), display);
	}

	@Override
	public List<BoundBox<?, ?>> getAdjustedBoxTree() {
		return box.getAdjustedBoxTree(this);
	}

	@Override
	public BoundBox<U, V> offshoot(U box) {
		return new BoundBoxImp<>(box, display);
	}

}
