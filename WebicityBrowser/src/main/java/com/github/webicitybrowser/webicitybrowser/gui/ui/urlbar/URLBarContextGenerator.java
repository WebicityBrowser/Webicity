package com.github.webicitybrowser.webicitybrowser.gui.ui.urlbar;

import com.github.webicitybrowser.thready.gui.graphical.base.InvalidationLevel;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.ComponentUI;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.UIDisplay;
import com.github.webicitybrowser.thready.gui.graphical.view.textfield.TextFieldContext;
import com.github.webicitybrowser.thready.gui.graphical.viewmodel.textfield.TextFieldViewModel;
import com.github.webicitybrowser.thready.gui.graphical.viewmodel.textfield.TextFieldViewModelListener;
import com.github.webicitybrowser.thready.model.textfield.TextFieldModel;
import com.github.webicitybrowser.webicitybrowser.component.URLBarComponent;

public final class URLBarContextGenerator {

	private URLBarContextGenerator() {}
	
	public static URLBarContext generateContext(UIDisplay<?, ?, ?> display, ComponentUI componentUI) {
		TextFieldContext textFieldContext = new TextFieldContext(componentUI, createViewModel(componentUI));
		return new URLBarContext(display, componentUI, textFieldContext);
	}
	
	private static TextFieldViewModel createViewModel(ComponentUI componentUI) {
		URLBarComponent component = (URLBarComponent) componentUI.getComponent();
		TextFieldModel textFieldModel = TextFieldModel.create();
		textFieldModel.setText(component.getValue());
		return new TextFieldViewModel(textFieldModel, new TextFieldViewModelListener() {
			@Override
			public void onSubmit(TextFieldViewModel textFieldViewModel) {
				handleSubmission(component, textFieldViewModel);
			}

			@Override
			public void onStateChanged(TextFieldViewModel textFieldViewModel) {
				componentUI.invalidate(InvalidationLevel.PAINT);
			}
		});
	}
	
	private static void handleSubmission(URLBarComponent component, TextFieldViewModel textFieldViewModel) {
		component.getAction()
			.ifPresent(action -> action.accept(textFieldViewModel.getText()));
	}
	
}
