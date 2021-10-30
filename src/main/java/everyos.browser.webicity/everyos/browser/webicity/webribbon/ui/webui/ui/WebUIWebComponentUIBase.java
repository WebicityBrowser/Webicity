package everyos.browser.webicity.webribbon.ui.webui.ui;

import everyos.browser.webicity.webribbon.core.component.WebComponent;
import everyos.browser.webicity.webribbon.core.ui.WebComponentUI;
import everyos.engine.ribbon.core.graphics.InvalidationLevel;

public abstract class WebUIWebComponentUIBase implements WebComponentUI {
	
	private final WebComponent component;
	private final WebComponentUI parent;
	
	private InvalidationLevel invalidated = InvalidationLevel.RENDER;

	public WebUIWebComponentUIBase(WebComponent component, WebComponentUI parent) {
		this.component = component;
		this.parent = parent;
	}

	@Override
	public WebComponentUI getParent() {
		return this.parent;
	}
	
	@Override
	public WebComponent getComponent() {
		return this.component;
	}

	@Override
	public void invalidate(InvalidationLevel level) {
		WebComponentUI parentUI = this;
		while (parentUI != null) {
			if (!parentUI.getValidated(level)) {
				return;
			}
			parentUI.invalidateLocal(level);
			parentUI = parentUI.getParent();
		}
	}
	
	@Override
	public void invalidateLocal(InvalidationLevel level) {
		if (this.invalidated.lessThan(level)) {
			this.invalidated = level;
		}
	}
	
	@Override
	public void validateTo(InvalidationLevel level) {
		this.invalidated = level;
	}
	
	@Override
	public boolean getValidated(InvalidationLevel reference) {
		return reference.lessThan(this.invalidated);
	}

}
