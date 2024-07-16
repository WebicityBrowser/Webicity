package com.github.webicitybrowser.webicity.renderer.backend.html.cssom.imp;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.github.webicitybrowser.spec.css.componentvalue.FunctionValue;
import com.github.webicitybrowser.spec.css.parser.TokenLike;
import com.github.webicitybrowser.spec.css.parser.TokenStream;
import com.github.webicitybrowser.spec.css.parser.tokens.CommaToken;
import com.github.webicitybrowser.spec.css.parser.tokens.IdentToken;
import com.github.webicitybrowser.spec.css.parser.util.TokenUtils;
import com.github.webicitybrowser.webicity.renderer.backend.html.cssom.CSSOMMappedRuleList.RelativeResolver;

public final class CSSOMVariableResolver {

	private static final String VARIABLE_FUNCTION = "var";
	
	private CSSOMVariableResolver() {}

	public static boolean hasVariable(TokenLike[] tokens) {
		for (TokenLike token: tokens) {
			if (token instanceof FunctionValue functionValue && functionValue.getName().equals(VARIABLE_FUNCTION)) {
				return true;
			}
		}

		return false;
	}

	public static Optional<TokenLike[]> resolveVariables(TokenLike[] tokens, RelativeResolver<?> relativeResolver) {
		if (!hasVariable(tokens)) {
			return Optional.of(tokens);
		}

		List<TokenLike> adjustedTokens = new ArrayList<>();
		for (TokenLike token: tokens) {
			if (
				token instanceof FunctionValue functionValue &&
				functionValue.getName().equals(VARIABLE_FUNCTION)
			) {
				TokenStream subStream = TokenStream.create(TokenUtils.stripWhitespace(functionValue.getValue()));
				if (!parseAndResolveVariable(subStream, adjustedTokens, relativeResolver)) {
					return Optional.empty();
				}
			} else {
				adjustedTokens.add(token);
			}
		}

		return Optional.of(adjustedTokens.toArray(TokenLike[]::new));
	}

	private static boolean parseAndResolveVariable(TokenStream tokenStream, List<TokenLike> adjustedTokens, RelativeResolver<?> relativeResolver) {
		String variableName = readVariableName(tokenStream);
		if (variableName == null) return false;
		Optional<TokenLike[]> variableValue = relativeResolver.resolveVariable(variableName);
		TokenLike[] fallbackValue = null;
		if (!tokenStream.isEmpty()) {
			if (!(tokenStream.read() instanceof CommaToken)) {
				return false;
			}

			fallbackValue = determineFallbackValue(tokenStream);
		}

		if (variableValue.isEmpty() && fallbackValue == null) return false;
		if (variableValue.isEmpty()) {
			TokenLike[] resolvedFallbackValue = resolveVariables(fallbackValue, relativeResolver).orElse(null);
			if (resolvedFallbackValue == null) return false;
			adjustedTokens.addAll(List.of(resolvedFallbackValue));
			return true;
		}
		adjustedTokens.addAll(List.of(variableValue.get()));

		return true;
	}

	private static TokenLike[] determineFallbackValue(TokenStream tokenStream) {
		List<TokenLike> fallbackValue = new ArrayList<>();
		while (!tokenStream.isEmpty()) {
			TokenLike token = tokenStream.read();
			fallbackValue.add(token);
		}

		return fallbackValue.toArray(TokenLike[]::new);
	}

	private static String readVariableName(TokenStream tokenStream) {
		TokenLike token = tokenStream.read();
		if (!(token instanceof IdentToken identToken)) {
			return null;
		}
		if (!identToken.getValue().startsWith("--")) {
			return null;
		}

		return identToken.getValue();
	}

}