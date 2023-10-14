package com.github.webicitybrowser.webicity.renderer.backend.html;

import com.github.webicitybrowser.spec.dom.node.Element;
import com.github.webicitybrowser.webicity.renderer.backend.html.tags.TagAction;

import java.util.HashMap;

public class ActionsRegistry implements TagActions{

	private final HashMap<String, TagAction> actions;

	public ActionsRegistry() {
		actions = new HashMap<>();
	}

	@Override
	public void registerTagAction(String tagName, TagAction action) {
		actions.put(tagName, action);
	}

	@Override
	public TagAction getAction(String tagName) {
		return actions.getOrDefault(tagName, new TagAction(){});
	}

}
