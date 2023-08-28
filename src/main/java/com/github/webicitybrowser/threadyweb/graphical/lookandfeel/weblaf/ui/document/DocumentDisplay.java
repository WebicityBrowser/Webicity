package com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.ui.document;

import java.util.List;

import com.github.webicitybrowser.thready.dimensions.Rectangle;
import com.github.webicitybrowser.thready.gui.directive.core.style.StyleGenerator;
import com.github.webicitybrowser.thready.gui.graphical.layout.core.LayoutResult;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.ComponentUI;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.UIDisplay;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.box.BoxContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.box.ChildrenBox;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.paint.GlobalPaintContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.paint.LocalPaintContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.GlobalRenderContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.LocalRenderContext;
import com.github.webicitybrowser.thready.gui.message.MessageHandler;
import com.github.webicitybrowser.thready.gui.message.NoopMessageHandler;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.layout.InnerDisplayLayout;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.layout.flow.FlowInnerDisplayLayout;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.stage.unit.BuildableRenderedUnit;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.ui.element.ElementDisplay;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.ui.element.ElementPainter;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.ui.element.ElementUnit;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.ui.element.inline.ElementInlineDisplay;

public class DocumentDisplay implements UIDisplay<DocumentContext, ChildrenBox, ElementUnit> {

	private final UIDisplay<?, ?, ?> ELEMENT_DISPLAY = new ElementDisplay();
	private final UIDisplay<?, ?, ?> ELEMENT_INLINE_DISPLAY = new ElementInlineDisplay();

	private final InnerDisplayLayout INNER_DISPLAY_LAYOUT = new FlowInnerDisplayLayout(
		(layoutResult, directives) -> new ElementUnit(ELEMENT_DISPLAY, directives, layoutResult),
		directives -> BuildableRenderedUnit.create(ELEMENT_INLINE_DISPLAY, directives));
	
	@Override
	public DocumentContext createContext(ComponentUI componentUI) {
		return new DocumentContext(this, componentUI);
	}

	@Override
	public List<ChildrenBox> generateBoxes(DocumentContext displayContext, BoxContext boxContext, StyleGenerator styleGenerator) {
		return DocumentBoxGenerator.generateBoxes(displayContext, boxContext, styleGenerator);
	}

	@Override
	public ElementUnit renderBox(ChildrenBox box, GlobalRenderContext globalRenderContext, LocalRenderContext localRenderContext) {
		LayoutResult layoutResult = INNER_DISPLAY_LAYOUT.renderBox(box, globalRenderContext, localRenderContext);
		return new ElementUnit(this, box.styleDirectives(), layoutResult);
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
