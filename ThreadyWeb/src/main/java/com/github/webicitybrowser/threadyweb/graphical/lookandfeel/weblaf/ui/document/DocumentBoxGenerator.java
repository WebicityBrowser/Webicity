package com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.ui.document;

import java.util.List;

import com.github.webicitybrowser.thready.gui.directive.basics.pool.BasicDirectivePool;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.UIDisplay;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.box.Box;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.box.BoxContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.box.ChildrenBox;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.context.Context;

public final class DocumentBoxGenerator {

	private DocumentBoxGenerator() {}
	
	@SuppressWarnings("unchecked")
	public static <T extends Context> List<ChildrenBox> generateBoxes(DocumentContext documentContext, BoxContext boxContext) {
		return documentContext
			.getVisibleChildContext()
			.map(context -> ((UIDisplay<T, ?, ?>) context.display()).generateBoxes((T) context, boxContext))
			.map(childBoxes -> List.of(generateBlockRootBox(documentContext, (List<Box>) childBoxes)))
			.orElse(List.of());
	}

	private static ChildrenBox generateBlockRootBox(DocumentContext context, List<Box> childBoxes) {
		ChildrenBox rootBox = new DocumentBox(context.display(), context.component(), new BasicDirectivePool());
		
		for (Box childBox: childBoxes) {
			rootBox.getChildrenTracker().addChild(childBox);
		}
		
		return rootBox;
	}

}
