package everyos.browser.webicitybrowser.gui.appearence;

import everyos.engine.ribbon.core.event.UIEvent;
import everyos.engine.ribbon.core.rendering.Renderer;
import everyos.engine.ribbon.core.shape.Dimension;
import everyos.engine.ribbon.core.shape.SizePosGroup;
import everyos.engine.ribbon.core.ui.UIDirective;
import everyos.engine.ribbon.core.ui.UIManager;
import everyos.engine.ribbon.ui.simple.appearence.Appearence;

public class RoundBottom implements Appearence {

    private Dimension bounds;
    private int d; // diameter;

    public RoundBottom(int diameter) {
        this.d = diameter;
    }

    @Override
    public void render(Renderer r, SizePosGroup sizepos, UIManager uimgr) {
        bounds = sizepos.getSize();
    }

    @Override
    public void paint(Renderer r) {
        r.useBackground();

        int w = bounds.getWidth();
        int h = bounds.getHeight();

        if (w < d) {
            r.drawFilledRect(0, 0, w, h - d / 2);
            r.drawEllipse(0, h - d, d, d);

        } else {
            r.drawFilledRect(0, 0, w, h - d / 2);

            r.drawEllipse(0, h - d, d, d);
            r.drawEllipse(w - d, h - d, d, d);

            r.drawFilledRect(d / 2, h - d / 2, w - d, h / 2);
        }
    }

    @Override
    public void directive(UIDirective directive) {
    }

    @Override
    public void processEvent(UIEvent e) {
    }
}
