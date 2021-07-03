package everyos.browser.webicitybrowser.gui.ui;

import everyos.browser.webicitybrowser.gui.Styling;
import everyos.browser.webicitybrowser.gui.component.CircularText;
import everyos.engine.ribbon.core.component.Component;
import everyos.engine.ribbon.core.rendering.Renderer;
import everyos.engine.ribbon.core.shape.Rectangle;
import everyos.engine.ribbon.core.shape.SizePosGroup;
import everyos.engine.ribbon.core.ui.ComponentUI;
import everyos.engine.ribbon.core.ui.UIManager;
import everyos.engine.ribbon.ui.simple.SimpleBlockComponentUI;
import everyos.engine.ribbon.ui.simple.helper.StringWrapHelper;

public class WebicityButtonUI extends SimpleBlockComponentUI {
    protected String text;
    private int strwidth;

    public WebicityButtonUI(Component c, ComponentUI parent) {
        super(c, parent);
    }

    @Override
    protected void renderUI(Renderer r, SizePosGroup sizepos, UIManager uimgr) {
        super.renderUI(r, sizepos, uimgr);
        this.strwidth = StringWrapHelper.stringWidth(r, Styling.PRODUCT_NAME);
        sizepos.move(strwidth + r.getFontPaddingHeight(), true);
        sizepos.setMinLineHeight(r.getFontHeight());

        //super.calcInternalSize(r, sizepos, data);
    }

    @Override
    protected void paintUI(Renderer r) {
        paintMouse(r);

        Rectangle bounds = getBounds();

        r.useBackground();

        int rectWidth = bounds.getWidth() - bounds.getHeight() / 2;

        r.drawFilledRect(0, 0, rectWidth, bounds.getHeight());
        r.drawEllipse(bounds.getWidth() - bounds.getHeight(), 0, bounds.getHeight(), bounds.getHeight());
        r.drawFilledRect(rectWidth, 0, bounds.getHeight()/2, bounds.getHeight()/2);

        r.useForeground();
        r.drawText(bounds.getWidth() / 2 - strwidth / 2, Styling.ELEMENT_PADDING, Styling.PRODUCT_NAME);

        paintChildren(r);
    }
}
