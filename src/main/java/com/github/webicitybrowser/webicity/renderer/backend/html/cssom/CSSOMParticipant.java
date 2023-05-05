package com.github.webicitybrowser.webicity.renderer.backend.html.cssom;

import java.util.List;

public interface CSSOMParticipant<T> {
	
	T getValue();

	CSSOMParticipant<T> getParent();
	
	List<CSSOMParticipant<T>> getChildren();
	
}
