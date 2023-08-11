package com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.ui.element.contents;

import java.util.List;

import com.github.webicitybrowser.thready.dimensions.AbsoluteSize;
import com.github.webicitybrowser.thready.dimensions.Rectangle;
import com.github.webicitybrowser.thready.gui.directive.core.pool.DirectivePool;
import com.github.webicitybrowser.thready.gui.directive.core.style.StyleGenerator;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.ComponentUI;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.UIDisplay;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.pipeline.BoundBox;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.pipeline.BoundRenderedUnit;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.pipeline.BoundRenderedUnitGenerator;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.pipeline.UIPipeline;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.box.BoxContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.box.WrapperBox;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.paint.GlobalPaintContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.paint.LocalPaintContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.GlobalRenderContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.LocalRenderContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.unit.RenderedUnitGenerator;
import com.github.webicitybrowser.thready.gui.message.MessageHandler;
import com.github.webicitybrowser.threadyweb.graphical.directive.OuterDisplayDirective;
import com.github.webicitybrowser.threadyweb.graphical.directive.OuterDisplayDirective.OuterDisplay;

public class ContentsDisplay implements UIDisplay<ContentsContext, ContentsBox, ContentsUnit> {
	
	private final UIDisplay<?, ?, ?> innerDisplay;

	public ContentsDisplay(UIDisplay<?, ?, ?> innerDisplay) {
		this.innerDisplay = innerDisplay;
	}

	@Override
	public ContentsContext createContext(ComponentUI componentUI) {
		UIPipeline<?, ?, ?> pipeline = UIPipeline.create(innerDisplay);
		return new ContentsContext(componentUI, pipeline.createContext(componentUI));
	}

	@Override
	public List<ContentsBox> generateBoxes(ContentsContext displayContext, BoxContext boxContext, StyleGenerator styleGenerator) {
		DirectivePool directives = styleGenerator.getStyleDirectives();
		boolean isContents = directives
			.getDirectiveOrEmpty(OuterDisplayDirective.class)
			.map(v -> v.getOuterDisplay() == OuterDisplay.CONTENTS)
			.orElse(false);
		if (isContents) {
			// TODO: Hacky and issue-prone. Improve this later.
			List<BoundBox<?, ?>> children = displayContext
				.innerContext()
				.generateBoxes(boxContext, styleGenerator)
				.stream()
				.map(box -> {
					if (box.getRaw() instanceof WrapperBox wrapperBox) {
						return wrapperBox.innerMostBox();
					} else {
						return box;
					}
				})
				.flatMap(box -> box.getAdjustedBoxTree().stream())
				.toList();

			return List.of(new ContentsProxyBox(displayContext.componentUI().getComponent(), directives, children));
		} else {
			return displayContext
				.innerContext()
				.generateBoxes(boxContext, styleGenerator)
				.stream()
				.map(box -> (ContentsBox) new ContentsWrapperBox(displayContext.componentUI().getComponent(), directives, box))
				.toList();
		}
	}

	@Override
	public RenderedUnitGenerator<ContentsUnit> renderBox(ContentsBox box, GlobalRenderContext globalRenderContext, LocalRenderContext localRenderContext) {
		if (box instanceof ContentsWrapperBox wrapperBox) {
			BoundRenderedUnitGenerator<?> innerUnitGenerator = wrapperBox.innerBox().render(globalRenderContext, localRenderContext);
			return new RenderedUnitGenerator<ContentsUnit>() {
				@Override
				public ContentsUnit generateNextUnit(AbsoluteSize preferredBounds, boolean forceFit) {
					BoundRenderedUnit<?> innerUnit = innerUnitGenerator.getUnit(g -> g.generateNextUnit(preferredBounds, forceFit));
					if (innerUnit == null) {
						return null;
					}
					return new ContentsUnit(innerUnit);
				}

				@Override
				public boolean completed() {
					return innerUnitGenerator.getRaw().completed();
				}
			};
		} else {
			throw new UnsupportedOperationException();
		}
	}

	@Override
	public void paint(ContentsUnit unit, GlobalPaintContext globalPaintContext, LocalPaintContext localPaintContext) {
		unit.innerUnit().paint(globalPaintContext, localPaintContext);
	}

	@Override
	public MessageHandler createMessageHandler(ContentsUnit unit, Rectangle documentRect) {
		return unit.innerUnit().createMessageHandler(documentRect);
	}

}
