package everyos.browser.webicitybrowser.gui.component;

import everyos.browser.webicitybrowser.gui.appearence.InnerEllipse;
import everyos.engine.ribbon.core.component.BlockComponent;
import everyos.engine.ribbon.core.component.Component;
import everyos.engine.ribbon.core.directive.FontSizeDirective;
import everyos.engine.ribbon.core.graphics.InvalidationLevel;
import everyos.engine.ribbon.ui.simple.appearence.Appearence;

public class TextButton extends BlockComponent {

    private String text;
    private Appearence appearence;

	public TextButton(String text) {
		this(text, new InnerEllipse());
	}

	public TextButton(String text, Appearence appearence) {
		this.text = text;
		this.appearence = appearence;
	}

	public TextButton() {
        super();
        this.directive(FontSizeDirective.of(14));
    }

    public Component text(String text) {
        this.text = text;
        invalidate(InvalidationLevel.RENDER);
        return this;
    }

    public String getText() {
        return this.text;
    }

    public Appearence getAppearence() {
        return appearence;
    }
}