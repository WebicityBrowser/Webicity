package com.github.webicitybrowser.webicity.renderer.backend.html;

import com.github.webicitybrowser.webicity.renderer.backend.html.tags.TagAction;

public interface TagActions {

	void registerTagAction(String tagName, TagAction action);

	TagAction getAction(String tagName);

}
