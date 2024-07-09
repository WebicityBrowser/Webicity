package com.github.webicitybrowser.threadyweb.graphical.layout.flow.context.inline;

import com.github.webicitybrowser.thready.dimensions.AbsoluteSize;
import com.github.webicitybrowser.thready.gui.directive.core.pool.DirectivePool;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.unit.RenderedUnit;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.ui.text.TextUnit;

public interface LineEntry {

	AbsoluteSize getSize();
	
	public record Unit(RenderedUnit unit) implements LineEntry {

		@Override
		public AbsoluteSize getSize() {
			return unit.fitSize();
		}

	}

	public record Text(TextUnit textUnit) implements LineEntry {

		@Override
		public AbsoluteSize getSize() {
			return textUnit.fitSize();
		}

	}

	public record UnitEnter(DirectivePool directives, boolean isStart) implements LineEntry {

		public UnitEnter split() {
			return new UnitEnter(directives, false);
		}

		@Override
		public AbsoluteSize getSize() {
			// TODO
			return AbsoluteSize.ZERO_SIZE;
		}

	}

	public record UnitExit() implements LineEntry {
		
		@Override
		public AbsoluteSize getSize() {
			// TODO
			return AbsoluteSize.ZERO_SIZE;
		}
		
	}

	public record PreserveLine() implements LineEntry {
		
		@Override
		public AbsoluteSize getSize() {
			return AbsoluteSize.ZERO_SIZE;
		}

	}

}
