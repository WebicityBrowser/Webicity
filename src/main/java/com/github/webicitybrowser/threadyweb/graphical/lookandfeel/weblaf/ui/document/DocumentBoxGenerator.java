package com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.ui.document;

import java.util.List;

import com.github.webicitybrowser.thready.gui.directive.basics.pool.BasicDirectivePool;
import com.github.webicitybrowser.thready.gui.directive.core.style.StyleGenerator;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.ComponentUI;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.pipeline.BoundBox;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.pipeline.PipelinedContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.pipeline.UIPipeline;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.box.BoxContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.box.ChildrenBox;
import com.github.webicitybrowser.thready.gui.tree.core.Component;
import com.github.webicitybrowser.threadyweb.tree.ElementComponent;

public final class DocumentBoxGenerator {

	private DocumentBoxGenerator() {}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static List<ChildrenBox> generateBoxes(DocumentContext documentContext, BoxContext boxContext, StyleGenerator styleGenerator) {
		return documentContext
			.component()
			.getVisibleChild()
			.map(child -> getComponentUI(documentContext, boxContext, child))
			.map(ui -> {
				PipelinedContext<?, ?, ?> context = UIPipeline.create(ui.getRootDisplay()).createContext(ui);
				StyleGenerator childStyleGenerator = createChildStyleGenerator(ui, styleGenerator);
				return context.generateBoxes(boxContext, childStyleGenerator);
			})
			.map(childBoxes -> List.of(generateBlockRootBox(documentContext.component(), (List<BoundBox<?, ?>>) (List) childBoxes)))
			.orElse(List.of());
	}

	private static ChildrenBox generateBlockRootBox(Component owningComponent, List<BoundBox<?, ?>> childBoxes) {
		ChildrenBox rootBox = new DocumentBox(owningComponent, new BasicDirectivePool());
		
		for (BoundBox<?, ?> childBox: childBoxes) {
			for (BoundBox<?, ?> adjustedChildBox: childBox.getAdjustedBoxTree()) {
				rootBox.getChildrenTracker().addChild(adjustedChildBox);
			}
		}
		
		return rootBox;
	}

	private static ComponentUI getComponentUI(DocumentContext documentContext, BoxContext boxContext, ElementComponent child) {
		if (documentContext.getChildComponentUI() == null || documentContext.getChildComponentUI().getComponent() != child) {
			documentContext.setChildComponentUI(boxContext.getLookAndFeel().createUIFor(child, documentContext.componentUI()));
		}
		
		return documentContext.getChildComponentUI();
	}
	
	private static StyleGenerator createChildStyleGenerator(ComponentUI ui, StyleGenerator styleGenerator) {
		return styleGenerator.createChildStyleGenerators(new ComponentUI[] { ui })[0];
	}
	
}
