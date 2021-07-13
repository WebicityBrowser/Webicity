package everyos.browser.webicitybrowser.gui.appearence;

import everyos.engine.ribbon.core.event.UIEvent;
import everyos.engine.ribbon.core.rendering.Renderer;
import everyos.engine.ribbon.core.shape.Dimension;
import everyos.engine.ribbon.core.shape.SizePosGroup;
import everyos.engine.ribbon.core.ui.UIDirective;
import everyos.engine.ribbon.core.ui.UIManager;
import everyos.engine.ribbon.ui.simple.appearence.Appearence;

public class InnerEllipse implements Appearence {

    private Dimension bounds;

    @Override
    public void render(Renderer r, SizePosGroup sizepos, UIManager uimgr) {
        bounds = sizepos.getSize();
    }

    @Override
    public void paint(Renderer r) {
        r.useBackground();
        r.drawEllipse(0, 0, bounds.getWidth(), bounds.getHeight());
    }

    @Override
    public void directive(UIDirective directive) {
    }

    @Override
    public void processEvent(UIEvent e) {
    }
}
