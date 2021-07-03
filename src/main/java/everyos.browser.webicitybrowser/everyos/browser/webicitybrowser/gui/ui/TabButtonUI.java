package everyos.browser.webicitybrowser.gui.ui;

import everyos.browser.webicitybrowser.gui.Styling;
import everyos.browser.webicitybrowser.gui.component.CircularText;
import everyos.browser.webicitybrowser.gui.component.TabButton;
import everyos.engine.ribbon.core.component.Component;
import everyos.engine.ribbon.core.rendering.Renderer;
import everyos.engine.ribbon.core.shape.Rectangle;
import everyos.engine.ribbon.core.shape.SizePosGroup;
import everyos.engine.ribbon.core.ui.ComponentUI;
import everyos.engine.ribbon.core.ui.UIManager;
import everyos.engine.ribbon.ui.simple.SimpleBlockComponentUI;
import everyos.engine.ribbon.ui.simple.helper.StringWrapHelper;

public class TabButtonUI extends SimpleBlockComponentUI {
	protected String text;
	private int strwidth;

	public TabButtonUI(Component c, ComponentUI parent) {
		super(c, parent);
	}
	
	@Override
	protected void renderUI(Renderer r, SizePosGroup sizepos, UIManager uimgr) {
		super.renderUI(r, sizepos, uimgr);

		// Width in which we expect the text to be fit in.
		int expectedWidth = getBounds().getWidth() - Styling.BUTTON_WIDTH - 2 * Styling.ELEMENT_PADDING;

		this.text = StringWrapHelper.trim(r, this.<TabButton>getComponent().getText(), expectedWidth);

		sizepos.move(strwidth + r.getFontPaddingHeight(), true);
		sizepos.setMinLineHeight(r.getFontHeight());

		//super.calcInternalSize(r, sizepos, data);
	}

	@Override
	protected void paintUI(Renderer r) {
		paintMouse(r);

		Rectangle bounds = getBounds();

		r.useBackground();
		r.drawEllipse(0, 0, bounds.getHeight(), bounds.getHeight());
		r.drawEllipse(bounds.getWidth() - bounds.getHeight(), 0, bounds.getHeight(), bounds.getHeight());
		r.drawFilledRect(bounds.getHeight() / 2, 0, bounds.getWidth() - bounds.getHeight(), bounds.getHeight());
		r.drawFilledRect(0, 0, bounds.getWidth(), bounds.getHeight() / 2);

		r.useForeground();
		r.drawText(Styling.BUTTON_WIDTH + Styling.ELEMENT_PADDING, Styling.ELEMENT_PADDING, text);

		super.paintChildren(r);
	}
}
