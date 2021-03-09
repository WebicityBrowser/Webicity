package everyos.engine.ribbon.core.component;

public class TextBoxComponent extends BlockComponent {
	private String text = "";

	public TextBoxComponent(Component parent) {
		super(parent);
	}

	public TextBoxComponent text(String text) {
		this.text = text;
		invalidate();
		return this;
	}
	public String getText() {
		return this.text;
	}
}
