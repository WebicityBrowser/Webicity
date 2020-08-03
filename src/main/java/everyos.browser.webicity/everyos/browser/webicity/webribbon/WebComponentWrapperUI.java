package everyos.browser.webicity.webribbon;

import everyos.browser.webicity.webribbon.component.WebComponent;
import everyos.browser.webicity.webribbon.misc.DrawData;
import everyos.browser.webicity.webribbon.shape.Position;
import everyos.engine.ribbon.core.component.Component;
import everyos.engine.ribbon.core.ui.ComponentUI;
import everyos.engine.ribbon.core.ui.UIManager;
import everyos.engine.ribbon.renderer.guirenderer.GUIComponentUI;
import everyos.engine.ribbon.renderer.guirenderer.GUIRenderer;
import everyos.engine.ribbon.renderer.guirenderer.shape.Dimension;
import everyos.engine.ribbon.renderer.guirenderer.shape.SizePosGroup;
import everyos.engine.ribbon.ui.simple.SimpleBlockComponentUI;

public class WebComponentWrapperUI extends SimpleBlockComponentUI {
	private WebComponent wui;
	
	public WebComponentWrapperUI() {}
	public WebComponentWrapperUI(Component c, GUIComponentUI parent) {
		super(c, parent);
		autofill = false;
	}
	@Override public ComponentUI create(Component c, ComponentUI parent) {
		return new WebComponentWrapperUI(c, (GUIComponentUI) parent);
	};

	@Override protected void renderUI(GUIRenderer r, SizePosGroup sizepos, UIManager<GUIComponentUI> uimgr) {
		this.wui = this.<WebComponentWrapper>getComponent().getUI();
		if (wui!=null) {
			System.out.println("W:"+sizepos.size.width);
			wui.render(r, new everyos.browser.webicity.webribbon.shape.SizePosGroup(
				new Position(),
				new Dimension(sizepos.size.width, sizepos.size.height),
				sizepos.size.width));
		}
	}

	@Override protected void paintUI(GUIRenderer r) {
		if (wui!=null) {
			DrawData d = new DrawData(); 
			wui.paint(r, d);
		}
	}
}
