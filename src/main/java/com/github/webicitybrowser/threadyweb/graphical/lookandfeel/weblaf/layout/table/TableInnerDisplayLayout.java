package com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.layout.table;

import com.github.webicitybrowser.thready.dimensions.AbsolutePosition;
import com.github.webicitybrowser.thready.dimensions.AbsoluteSize;
import com.github.webicitybrowser.thready.dimensions.Rectangle;
import com.github.webicitybrowser.thready.gui.directive.basics.pool.BasicDirectivePool;
import com.github.webicitybrowser.thready.gui.directive.core.pool.DirectivePool;
import com.github.webicitybrowser.thready.gui.graphical.layout.core.ChildLayoutResult;
import com.github.webicitybrowser.thready.gui.graphical.layout.core.LayoutManagerContext;
import com.github.webicitybrowser.thready.gui.graphical.layout.core.LayoutResult;
import com.github.webicitybrowser.thready.gui.graphical.layout.core.SolidLayoutManager;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.UIDisplay;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.box.Box;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.box.ChildrenBox;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.unit.RenderedUnit;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.ui.element.ElementDisplay;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.ui.element.styled.StyledUnit;

import java.util.ArrayList;
import java.util.List;

public class TableInnerDisplayLayout implements SolidLayoutManager {

	@Override
	public LayoutResult render(LayoutManagerContext layoutManagerContext) {
		//Fixup algorithm
		System.out.println("There is table layout");
		for (Box child :
			layoutManagerContext.children()) {
			System.out.println(child);
		}
		List<ChildLayoutResult> resultChild = new ArrayList<>();
//		resultChild.add(new StyledUnit(new ElementDisplay()));
		resultChild.add(new ChildLayoutResult(new RenderedUnit() {
				@Override
				public UIDisplay<?, ?, ?> display() {
					return new ElementDisplay();
				}

				@Override
				public AbsoluteSize fitSize() {
					return new AbsoluteSize(100,100);
				}

				@Override
				public DirectivePool styleDirectives() {
					return new BasicDirectivePool();
				}
			},
				new Rectangle(new AbsolutePosition(1,1), new AbsoluteSize(100,100))
			)
		);
		return LayoutResult.create(resultChild.toArray(ChildLayoutResult[]::new), new AbsoluteSize(100,100));
	}

}
