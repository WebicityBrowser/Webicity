package com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.ui.element;

import java.util.List;
import java.util.function.Function;

import com.github.webicitybrowser.thready.dimensions.Rectangle;
import com.github.webicitybrowser.thready.gui.directive.core.pool.DirectivePool;
import com.github.webicitybrowser.thready.gui.graphical.layout.core.LayoutManagerContext;
import com.github.webicitybrowser.thready.gui.graphical.layout.core.LayoutResult;
import com.github.webicitybrowser.thready.gui.graphical.layout.core.SolidLayoutManager;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.ComponentUI;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.UIDisplay;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.box.BoxContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.box.ChildrenBox;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.composite.GlobalCompositeContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.composite.LocalCompositeContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.paint.GlobalPaintContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.paint.LocalPaintContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.GlobalRenderContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.LocalRenderContext;
import com.github.webicitybrowser.thready.gui.message.MessageHandler;
import com.github.webicitybrowser.thready.gui.message.NoopMessageHandler;
import com.github.webicitybrowser.threadyweb.graphical.layout.adjusted.AdjustedLayoutManager;
import com.github.webicitybrowser.threadyweb.graphical.layout.flow.FlowInnerDisplayLayout;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.stage.render.unit.BuildableRenderedUnit;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.stage.render.unit.StyledUnitGenerator;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.ui.element.inline.ElementInlineDisplay;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.ui.element.styled.StyledUnit;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.ui.element.styled.StyledUnitDisplay;

public class ElementDisplay implements UIDisplay<ElementContext, ChildrenBox, ElementUnit> {

	private static final UIDisplay<?, ?, ?> ELEMENT_INLINE_DISPLAY = new ElementInlineDisplay();
	private static final UIDisplay<?, ?, ?> ELEMENT_STYLED_DISPLAY = new StyledUnitDisplay();

	private final Function<DirectivePool, BuildableRenderedUnit> innerUnitGenerator =
		directives -> BuildableRenderedUnit.create(ELEMENT_INLINE_DISPLAY, directives);
	private final StyledUnitGenerator styledUnitGenerator =
		context -> new StyledUnit(ELEMENT_STYLED_DISPLAY, context);

	private final ElementBoxGenerator elementBoxGenerator = new ElementBoxGenerator(innerUnitGenerator, styledUnitGenerator);
	private final FlowInnerDisplayLayout defaultLayout = new FlowInnerDisplayLayout(innerUnitGenerator, styledUnitGenerator);
	
	@Override
	public ElementContext createContext(ComponentUI componentUI) {
		return new ElementContext(this, componentUI);
	}

	@Override
	public List<ChildrenBox> generateBoxes(ElementContext displayContext, BoxContext boxContext) {
		return elementBoxGenerator.generateBoxes(displayContext, boxContext);
	}

	@Override
	public ElementUnit renderBox(ChildrenBox box, GlobalRenderContext globalRenderContext, LocalRenderContext localRenderContext) {
		SolidLayoutManager layoutManager = box instanceof ElementBlockBox elementBox ? elementBox.layout() : defaultLayout;
		SolidLayoutManager adjustedLayoutManager = new AdjustedLayoutManager(layoutManager);
		LayoutManagerContext layoutManagerContext = new LayoutManagerContext(
			box, box.getChildrenTracker().getChildren(),
			globalRenderContext, localRenderContext);
		LayoutResult layoutResult = adjustedLayoutManager.render(layoutManagerContext);
		
		return new ElementUnit(this, box.styleDirectives(), layoutResult);
	}

	@Override
	public void composite(ElementUnit unit, GlobalCompositeContext compositeContext, LocalCompositeContext localCompositeContext) {
		ElementCompositor.composite(unit, compositeContext, localCompositeContext);
	}

	@Override
	public void paint(ElementUnit unit, GlobalPaintContext globalPaintContext, LocalPaintContext localPaintContext) {
		ElementPainter.paint(unit, globalPaintContext, localPaintContext);
	}

	@Override
	public MessageHandler createMessageHandler(ElementUnit unit, Rectangle documentRect) {
		return new NoopMessageHandler();
	}
	
}
