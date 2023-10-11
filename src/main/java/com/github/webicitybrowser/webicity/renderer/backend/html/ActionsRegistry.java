package com.github.webicitybrowser.webicity.renderer.backend.html;

import com.github.webicitybrowser.webicity.renderer.backend.html.tags.NoTagAction;
import com.github.webicitybrowser.webicity.renderer.backend.html.tags.TagAction;

import java.util.HashMap;

public class ActionsRegistry implements TagActions{

	private final HashMap<String, TagAction> actions;
	private final TagAction noAction;

	public ActionsRegistry() {
		actions = new HashMap<>();
		noAction = new NoTagAction();
	}

	@Override
	public void registerTagAction(String tagName, TagAction action) {
		actions.put(tagName, action);
	}

	@Override
	public TagAction getAction(String tagName) {
		return actions.getOrDefault(tagName, noAction);
	}

}
