package everyos.browser.webicitybrowser.gui.component;

import everyos.browser.webicitybrowser.gui.Styling;
import everyos.engine.ribbon.core.component.BlockComponent;
import everyos.engine.ribbon.core.component.Component;
import everyos.engine.ribbon.core.directive.FontInfoDirective;
import everyos.engine.ribbon.core.directive.PositionDirective;
import everyos.engine.ribbon.core.directive.SizeDirective;
import everyos.engine.ribbon.core.graphics.InvalidationLevel;
import everyos.engine.ribbon.core.graphics.font.FontInfo;
import everyos.engine.ribbon.core.graphics.font.FontWeight;
import everyos.engine.ribbon.core.shape.Location;

public class TabButton extends BlockComponent {
	
	private final CircularText closeButton;
	private final BlockComponent spacer;
	
	private String text = "";

	public TabButton() {
		super();

		closeButton = new CircularText();
		closeButton.directive(PositionDirective.of(new Location(0, Styling.ELEMENT_PADDING / 2, 0, Styling.ELEMENT_PADDING / 2)));
		closeButton.directive(SizeDirective.of(new Location(0, Styling.BUTTON_WIDTH, 0, Styling.BUTTON_WIDTH)));
		closeButton.text("X");
		addChild(closeButton);
		
		spacer = new BlockComponent();

		this.directive(FontInfoDirective.of(FontInfo.getByName("Open Sans", 14, FontWeight.NORMAL)));
	}

	public Component text(String text) {
		this.text = text;
		invalidate(InvalidationLevel.RENDER);
		
		return this;
	}

	public String getText() {
		return this.text;
	}

	public CircularText getCloseButton() {
		return closeButton;
	}
	
	public Component getSpacer() {
		return spacer;
	}
	
	@Override
	public void delete() {
		spacer.delete();
		super.delete();
	}
}
