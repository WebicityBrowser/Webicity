package everyos.browser.webicitybrowser.gui.ui;

import everyos.browser.webicitybrowser.gui.Styling;
import everyos.browser.webicitybrowser.gui.appearence.RoundBottom;
import everyos.browser.webicitybrowser.gui.component.TabButton;
import everyos.engine.ribbon.core.component.Component;
import everyos.engine.ribbon.core.event.UIEvent;
import everyos.engine.ribbon.core.rendering.Renderer;
import everyos.engine.ribbon.core.shape.Dimension;
import everyos.engine.ribbon.core.shape.SizePosGroup;
import everyos.engine.ribbon.core.ui.ComponentUI;
import everyos.engine.ribbon.core.ui.UIDirective;
import everyos.engine.ribbon.core.ui.UIManager;
import everyos.engine.ribbon.ui.simple.SimpleBlockComponentUI;
import everyos.engine.ribbon.ui.simple.appearence.Appearence;
import everyos.engine.ribbon.ui.simple.helper.StringWrapHelper;

public class TabButtonUI extends SimpleBlockComponentUI {
    private Appearence appearence;

    public TabButtonUI(Component c, ComponentUI parent) {
        super(c, parent);

        this.appearence = new TabButtonAppearence();
    }


    @Override
    protected Appearence getAppearence() {
        return this.appearence;
    }

    private class TabButtonAppearence implements Appearence {
        private String text;
        private int strwidth;
        private Dimension bounds;
        private Appearence background;

        @Override
        public void render(Renderer r, SizePosGroup sizepos, UIManager uimgr) {
            this.bounds = sizepos.getSize();
            // Width in which we expect the text to be fit in.
            int expectedWidth = bounds.getWidth() - Styling.BUTTON_WIDTH - 2 * Styling.ELEMENT_PADDING;

            background = new RoundBottom(Styling.BUTTON_WIDTH);

            this.text = StringWrapHelper.trim(r, getComponent().casted(TabButton.class).getText(), expectedWidth);
            this.strwidth = StringWrapHelper.stringWidth(r, text);
            sizepos.move(strwidth + r.getFontPaddingHeight(), true);
            sizepos.setMinLineHeight(r.getFontHeight());

            background.render(r, sizepos, uimgr);
        }

        @Override
        public void paint(Renderer r) {
            background.paint(r);
//            r.useBackground();
//            r.drawEllipse(0, 0, bounds.getHeight(), bounds.getHeight());
//            r.drawEllipse(bounds.getWidth() - bounds.getHeight(), 0, bounds.getHeight(), bounds.getHeight());
//            r.drawFilledRect(bounds.getHeight() / 2, 0, bounds.getWidth() - bounds.getHeight(), bounds.getHeight());
//            r.drawFilledRect(0, 0, bounds.getWidth(), bounds.getHeight() / 2);

            r.useForeground();
            r.drawText(Styling.BUTTON_WIDTH + Styling.ELEMENT_PADDING, Styling.ELEMENT_PADDING, text);

        }

        @Override
        public void directive(UIDirective directive) {

        }

        @Override
        public void processEvent(UIEvent e) {
            // TODO Auto-generated method stub

        }
    }
}
