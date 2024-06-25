package com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.ui.document;

import java.util.List;

import com.github.webicitybrowser.thready.dimensions.Rectangle;
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
import com.github.webicitybrowser.threadyweb.graphical.layout.flow.FlowInnerDisplayLayout;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.stage.render.unit.BuildableRenderedUnit;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.ui.element.ElementCompositor;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.ui.element.ElementPainter;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.ui.element.ElementUnit;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.ui.element.inline.ElementInlineDisplay;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.ui.element.styled.StyledUnit;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.ui.element.styled.StyledUnitDisplay;

public class DocumentDisplay implements UIDisplay<DocumentContext, ChildrenBox, ElementUnit> {

	private final UIDisplay<?, ?, ?> ELEMENT_INLINE_DISPLAY = new ElementInlineDisplay();
	private static final UIDisplay<?, ?, ?> ELEMENT_STYLED_DISPLAY = new StyledUnitDisplay();

	private final SolidLayoutManager INNER_DISPLAY_LAYOUT = new FlowInnerDisplayLayout(
		directives -> BuildableRenderedUnit.create(ELEMENT_INLINE_DISPLAY, directives),
		context -> new StyledUnit(ELEMENT_STYLED_DISPLAY, context));
	
	@Override
	public DocumentContext createContext(ComponentUI componentUI) {
		return new DocumentContext(this, componentUI);
	}

	@Override
	public List<ChildrenBox> generateBoxes(DocumentContext displayContext, BoxContext boxContext) {
		return DocumentBoxGenerator.generateBoxes(displayContext, boxContext);
	}

	@Override
	public ElementUnit renderBox(ChildrenBox box, GlobalRenderContext globalRenderContext, LocalRenderContext localRenderContext) {
		LayoutManagerContext layoutManagerContext = new LayoutManagerContext(
			box, box.getChildrenTracker().getChildren(),
			globalRenderContext, localRenderContext);
		LayoutResult layoutResult = INNER_DISPLAY_LAYOUT.render(layoutManagerContext);
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
