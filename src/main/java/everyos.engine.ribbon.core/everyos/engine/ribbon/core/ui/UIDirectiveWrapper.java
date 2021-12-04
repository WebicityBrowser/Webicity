package everyos.engine.ribbon.core.ui;

import everyos.engine.ribbon.core.graphics.InvalidationLevel;

public interface UIDirectiveWrapper {
	
	public Class<? extends ComponentUI>[] getAffectedUIs();
	public InvalidationLevel getPipelineHint();
	public UIDirective getDirective();
	
	@SafeVarargs
	public static <T extends UIDirective> UIDirectiveWrapper wrap(T directive, InvalidationLevel hint, Class<? extends ComponentUI>... uis) {
		return new UIDirectiveWrapper() {
			@Override
			public Class<? extends ComponentUI>[] getAffectedUIs() {
				return uis;
			}

			@Override
			public InvalidationLevel getPipelineHint() {
				return hint;
			}

			@Override
			public UIDirective getDirective() {
				return directive;
			}
		};
	}
	
}
