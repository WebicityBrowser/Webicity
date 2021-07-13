package everyos.browser.webicitybrowser.gui.ui;

import everyos.browser.webicitybrowser.gui.component.CircularText;
import everyos.browser.webicitybrowser.gui.component.TextButton;
import everyos.engine.ribbon.core.component.Component;
import everyos.engine.ribbon.core.event.UIEvent;
import everyos.engine.ribbon.core.graphics.Color;
import everyos.engine.ribbon.core.rendering.Renderer;
import everyos.engine.ribbon.core.shape.Dimension;
import everyos.engine.ribbon.core.shape.SizePosGroup;
import everyos.engine.ribbon.core.ui.ComponentUI;
import everyos.engine.ribbon.core.ui.UIDirective;
import everyos.engine.ribbon.core.ui.UIManager;
import everyos.engine.ribbon.ui.simple.SimpleBlockComponentUI;
import everyos.engine.ribbon.ui.simple.appearence.Appearence;
import everyos.engine.ribbon.ui.simple.helper.StringWrapHelper;

public class TextButtonUI extends SimpleBlockComponentUI {

	private TextButton button;
    private Appearence appearence;

    public TextButtonUI(Component c, ComponentUI parent) {
        super(c, parent);
        button = c.casted();
        this.appearence = new TextAppearence(button.getAppearence());
    }

    @Override
    protected Appearence getAppearence() {
        return this.appearence;
    }

    private class TextAppearence implements Appearence {
        private String text;
        private int strwidth;
        private Dimension bounds;
        private Appearence background;

        private TextAppearence(Appearence background) {
            this.background = background;
        }

        @Override
        public void render(Renderer r, SizePosGroup sizepos, UIManager uimgr) {
            this.text = button.getText();

            this.strwidth = StringWrapHelper.stringWidth(r, text);
            sizepos.move(strwidth + r.getFontPaddingHeight(), true);
            sizepos.setMinLineHeight(r.getFontHeight());

            this.bounds = sizepos.getSize();
            background.render(r, sizepos, uimgr);
        }

        @Override
        public void paint(Renderer r) {
            background.paint(r);

            r.useForeground();
            r.drawText(bounds.getWidth() / 2 - strwidth / 2, bounds.getHeight() / 2 - r.getFontHeight() / 2, text);
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
