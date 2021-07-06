package everyos.browser.webicitybrowser.gui.ui;

import everyos.browser.webicitybrowser.gui.Styling;
import everyos.browser.webicitybrowser.gui.component.OverlyingBlockComponent;
import everyos.engine.ribbon.core.component.Component;
import everyos.engine.ribbon.core.rendering.Renderer;
import everyos.engine.ribbon.core.shape.Rectangle;
import everyos.engine.ribbon.core.ui.ComponentUI;
import everyos.engine.ribbon.core.ui.UIManager;
import everyos.engine.ribbon.ui.simple.SimpleBlockComponentUI;

public class OverlyingBlockComponentUI extends SimpleBlockComponentUI {

    private OverlyingBlockComponent component;
    private Component contentPane;
    private ComponentUI contentPaneUI;

    public OverlyingBlockComponentUI(Component c, ComponentUI parent) {
        super(c, parent);
    }

    //TODO: Fix merge conflicts
    
    /*@Override
    protected ComponentUI[] calcChildren(UIManager uimgr) {
        component = getComponent();
        if (component.getChildren().length < 1) return new ComponentUI[0];
        contentPane = component.getChildren()[0];
        contentPane.unbindAll();
        contentPaneUI = uimgr.get(contentPane, getParent());
        contentPane.bind(contentPaneUI);
        return new ComponentUI[]{contentPaneUI};
    }

    @Override
    protected ComponentUI[] getChildren() {
        return new ComponentUI[]{contentPaneUI};
    }

    @Override
    protected void paintUI(Renderer r) {
        paintMouse(r);

        if(component.isInvisible()) return;

        Rectangle bounds = getBounds();

        r.useBackground();
        int w = bounds.getWidth();
        int h = bounds.getHeight();
        int d = Styling.BUTTON_WIDTH;
        int l2 = d / 2;
        int ws = w - d;
        int hs = h - d;
        r.drawEllipse(0, 0, d, d);
        r.drawEllipse(ws, 0, d, d);
        r.drawEllipse(ws, hs, d, d);
        r.drawEllipse(0, hs, d, d);
        r.drawFilledRect(l2, 0, ws, d);
        r.drawFilledRect(ws, l2, d, hs);
        r.drawFilledRect(l2, hs, ws, d);
        r.drawFilledRect(0, l2, d, hs);
        r.drawFilledRect(d, d, w - 2 * d, h - 2 * d);

        r.useForeground();

        contentPaneUI.paint(r);
    }*/



}
