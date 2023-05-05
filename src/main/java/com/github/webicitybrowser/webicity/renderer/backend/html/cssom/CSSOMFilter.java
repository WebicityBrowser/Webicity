package com.github.webicitybrowser.webicity.renderer.backend.html.cssom;

import java.util.List;

public interface CSSOMFilter<T, U> {
	
	List<CSSOMParticipant<T>> filter(List<CSSOMNode<T, U>> prematched, CSSOMParticipant<T> item);
	
}
