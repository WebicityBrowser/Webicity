package com.github.webicitybrowser.webicity.renderer.backend.html.cssom.imp;

import com.github.webicitybrowser.webicity.renderer.backend.html.cssom.CSSOMFilter;
import com.github.webicitybrowser.webicity.renderer.backend.html.cssom.CSSOMNode;

public record CSSOMTask<T, U> (int priority, T participant, CSSOMNode<T, U> cssomNode, CSSOMFilter<T, U> filter) {

}
