package com.github.webicitybrowser.webicity.renderer.backend.html.cssom.imp;

import com.github.webicitybrowser.webicity.renderer.backend.html.cssom.CSSOMFilter;
import com.github.webicitybrowser.webicity.renderer.backend.html.cssom.CSSOMNode;
import com.github.webicitybrowser.webicity.renderer.backend.html.cssom.CSSOMParticipant;

public record CSSOMTask<T, U> (int priority, CSSOMParticipant<T> participant, CSSOMNode<T, U> cssomNode, CSSOMFilter<T, U> filter) {

}
