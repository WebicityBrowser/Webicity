package com.github.webicitybrowser.webicitybrowser.gui.ui.urlbar;

import com.github.webicitybrowser.thready.gui.directive.core.pool.DirectivePool;
import com.github.webicitybrowser.thready.gui.directive.core.style.StyleGenerator;
import com.github.webicitybrowser.thready.gui.graphical.base.InvalidationLevel;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.base.stage.box.BasicBox;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.ComponentUI;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.box.Box;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.box.BoxContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.simplelaf.stage.style.SimpleStylePoolGenerator;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.simplelaf.util.SimpleBoxGenerator;
import com.github.webicitybrowser.thready.gui.graphical.viewmodel.textfield.TextFieldViewModel;
import com.github.webicitybrowser.thready.gui.graphical.viewmodel.textfield.TextFieldViewModelListener;
import com.github.webicitybrowser.thready.gui.tree.core.Component;
import com.github.webicitybrowser.thready.model.textfield.TextFieldModel;
import com.github.webicitybrowser.webicitybrowser.component.URLBarComponent;

public class URLBarComponentUI implements ComponentUI {

	private final URLBarComponent component;
	private final ComponentUI parent;
	private final SimpleStylePoolGenerator stylePoolGenerator;
	private final TextFieldViewModel textFieldViewModel;

	public URLBarComponentUI(Component component, ComponentUI parent) {
		this.component = (URLBarComponent) component;
		this.parent = parent;
		this.stylePoolGenerator = new SimpleStylePoolGenerator(this, component.getStyleDirectives());
		this.textFieldViewModel = createViewModel();
	}
	
	@Override
	public Component getComponent() {
		return this.component;
	}
	
	@Override
	public void invalidate(InvalidationLevel level) {
		parent.invalidate(level);
	}

	@Override
	public Box[] generateBoxes(BoxContext context, DirectivePool parentDirectives, StyleGenerator styleGenerator) {
		DirectivePool directives = stylePoolGenerator.createStylePool(parentDirectives, styleGenerator);
		
		return SimpleBoxGenerator.generateBoxes(() -> new Box[] {
			new BasicBox(
				component, directives,
				(box, children) -> new URLBarComponentRenderer(box, textFieldViewModel))
		});
	}
	
	private TextFieldViewModel createViewModel() {
		TextFieldModel textFieldModel = TextFieldModel.create();
		textFieldModel.setText(component.getValue());
		return new TextFieldViewModel(textFieldModel, new TextFieldViewModelListener() {
			@Override
			public void onSubmit() {
				handleSubmission();
			}

			@Override
			public void onStateChanged() {
				invalidate(InvalidationLevel.PAINT);
			}
		});
	}
	
	private void handleSubmission() {
		component.getAction()
			.ifPresent(action -> action.accept(textFieldViewModel.getText()));
	}

}
