package everyos.browser.webicitybrowser.gui.appearence;

import everyos.engine.ribbon.core.event.UIEvent;
import everyos.engine.ribbon.core.rendering.Renderer;
import everyos.engine.ribbon.core.shape.Dimension;
import everyos.engine.ribbon.core.shape.SizePosGroup;
import everyos.engine.ribbon.core.ui.UIDirective;
import everyos.engine.ribbon.core.ui.UIManager;
import everyos.engine.ribbon.ui.simple.appearence.Appearence;

public class HorizontalBean implements Appearence {

    private Dimension bounds;

    @Override
    public void render(Renderer r, SizePosGroup sizepos, UIManager uimgr) {
        bounds = sizepos.getSize();
    }

    @Override
    public void paint(Renderer r) {
        r.useBackground();

        int w = bounds.getWidth();
        int h = bounds.getHeight();

        if (w < h)
            r.drawEllipse(0, 0, w, h);

        else {
            r.drawEllipse(0, 0, h, h);
            r.drawEllipse(w - h, 0, h, h);
            r.drawFilledRect(h / 2, 0, w - h, h);
        }
    }

    @Override
    public void directive(UIDirective directive) {
    }

    @Override
    public void processEvent(UIEvent e) {
    }
}
