package everyos.engine.ribbon.core.ui;

public interface UIDirectiveWrapper {
	public Class<? extends ComponentUI>[] getAffectedUIs();
	public int getPipelineHint();
	public UIDirective getDirective();
	
	@SafeVarargs
	public static <T extends UIDirective> UIDirectiveWrapper wrap(T directive, int hint, Class<? extends ComponentUI>... uis) {
		return new UIDirectiveWrapper() {
			@Override public Class<? extends ComponentUI>[] getAffectedUIs() {
				return uis;
			}

			@Override public int getPipelineHint() {
				return hint;
			}

			@Override public UIDirective getDirective() {
				return directive;
			}
		};
	}
}
