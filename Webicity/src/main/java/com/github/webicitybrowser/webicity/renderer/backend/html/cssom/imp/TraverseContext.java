package com.github.webicitybrowser.webicity.renderer.backend.html.cssom.imp;

import com.github.webicitybrowser.webicity.renderer.backend.html.cssom.CSSOMParticipantTraverser;

public record TraverseContext<T, U>(
	CSSOMParticipantTraverser<T, U> traverser,
	CSSOMTips<T, U> tips
) {}