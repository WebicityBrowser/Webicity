package com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.ui.document;

import java.util.List;

import com.github.webicitybrowser.thready.gui.directive.basics.pool.BasicDirectivePool;
import com.github.webicitybrowser.thready.gui.directive.core.style.StyleGenerator;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.ComponentUI;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.UIDisplay;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.box.Box;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.box.BoxContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.box.ChildrenBox;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.context.Context;
import com.github.webicitybrowser.thready.gui.tree.core.Component;
import com.github.webicitybrowser.threadyweb.tree.ElementComponent;

public final class DocumentBoxGenerator {

	private DocumentBoxGenerator() {}
	
	@SuppressWarnings("unchecked")
	public static <T extends Context> List<ChildrenBox> generateBoxes(DocumentContext documentContext, BoxContext boxContext, StyleGenerator styleGenerator) {
		return documentContext
			.component()
			.getVisibleChild()
			.map(child -> getComponentUI(documentContext, boxContext, child))
			.map(ui -> {
				Context context = ui.getRootDisplay().createContext(ui);
				StyleGenerator childStyleGenerator = createChildStyleGenerator(ui, styleGenerator);
				return ((UIDisplay<T, ?, ?>) ui.getRootDisplay()).generateBoxes((T) context, boxContext, childStyleGenerator);
			})
			.map(childBoxes -> List.of(generateBlockRootBox(documentContext.component(), (List<Box>) childBoxes)))
			.orElse(List.of());
	}

	private static ChildrenBox generateBlockRootBox(Component owningComponent, List<Box> childBoxes) {
		ChildrenBox rootBox = new DocumentBox(null, owningComponent, new BasicDirectivePool());
		
		for (Box childBox: childBoxes) {
			for (Box adjustedChildBox: childBox.getAdjustedBoxTree()) {
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
