package com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.ui.text;

import com.github.webicitybrowser.thready.gui.directive.core.pool.DirectivePool;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.box.Box;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.PrerenderMessage;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.text.TextConsolidationPrerenderMessage;
import com.github.webicitybrowser.threadyweb.tree.TextComponent;

public record TextBox(TextComponent owningComponent, DirectivePool styleDirectives) implements Box {

	@Override
	public boolean isFluid() {
		return true;
	}

	@Override
	public void message(PrerenderMessage message) {
		if (message instanceof TextConsolidationPrerenderMessage textMessage) {
			textMessage.getConsolidation().addText(owningComponent, owningComponent.getText());
		} else {
			message.handleDefault(this);
		}
	}
	
}
