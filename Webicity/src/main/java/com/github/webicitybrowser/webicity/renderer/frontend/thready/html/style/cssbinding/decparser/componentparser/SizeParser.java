package com.github.webicitybrowser.webicity.renderer.frontend.thready.html.style.cssbinding.decparser.componentparser;

import com.github.webicitybrowser.spec.css.property.CSSValue;
import com.github.webicitybrowser.spec.css.property.shared.length.AbsoluteLengthValue;
import com.github.webicitybrowser.spec.css.property.shared.length.RelativeLengthValue;
import com.github.webicitybrowser.spec.css.property.shared.math.MathValue;
import com.github.webicitybrowser.spec.css.property.shared.percentage.PercentageValue;
import com.github.webicitybrowser.thready.dimensions.RelativeDimension;
import com.github.webicitybrowser.threadyweb.graphical.value.SizeCalculation;
import com.github.webicitybrowser.threadyweb.graphical.value.SizeCalculation.SizeCalculationContext;
import com.github.webicitybrowser.threadyweb.graphical.value.math.MathCalculation;

public final class SizeParser {
	
	private SizeParser() {}
	
	public static SizeCalculation parseWithBoxPercents(CSSValue value) {
		if (value instanceof MathValue mathValue) {
			MathCalculation<SizeCalculation> mathCalculation = MathParser.parseMathValue(mathValue, SizeParser::parseWithBoxPercents);
			return (context, isHorizontal) -> mathCalculation.evaluate(calculation -> calculation.calculate(context, isHorizontal));
		}
		if (value instanceof PercentageValue percentageValue) {
			return (context, isHorizontal) -> translateBoxPercentageValue(context, percentageValue, isHorizontal);
		}
		return parseNonPercent(value);
	}

	public static SizeCalculation parseWithFontPercents(CSSValue value) {
		if (value instanceof MathValue mathValue) {
			MathCalculation<SizeCalculation> mathCalculation = MathParser.parseMathValue(mathValue, SizeParser::parseWithFontPercents);
			return (context, isHorizontal) -> mathCalculation.evaluate(calculation -> calculation.calculate(context, isHorizontal));
		}
		if (value instanceof PercentageValue percentageValue) {
			return (context, isHorizontal) -> context.relativeFont().getSize() * percentageValue.getValue() / 100;
		}
		return parseNonPercent(value);
	}

	public static SizeCalculation parseNonPercent(CSSValue value) {
		if (value instanceof MathValue mathValue) {
			MathCalculation<SizeCalculation> mathCalculation = MathParser.parseMathValue(mathValue, SizeParser::parseNonPercent);
			return (context, isHorizontal) -> mathCalculation.evaluate(calculation -> calculation.calculate(context, isHorizontal));
		} else if (value instanceof AbsoluteLengthValue lengthValue) {
			float translatedValue = translateAbsoluteValue(lengthValue);
			return (_1, _2) -> translatedValue;
		} else if (value instanceof RelativeLengthValue lengthValue) {
			return translateRelativeValue(lengthValue);
		}
		throw new UnsupportedOperationException("Unrecognized CSSValue: " + value);
	}

	private static float translateAbsoluteValue(AbsoluteLengthValue lengthValue) {
		float initialValue = lengthValue.getValue();
		switch (lengthValue.getUnit()) {
			case PX:
				return initialValue;
			case CM:
				return initialValue * 96 / 2.54f;
			case MM:
				return initialValue * 96 / 25.4f;
			case Q:
				return initialValue * 96 / 2.54f / 40;
			case IN:
				return initialValue * 96;
			case PC:
				return initialValue * 96 / 6;
			case PT:
				return initialValue * 96 / 72;
			default:
				throw new UnsupportedOperationException("Unrecognized AbsoluteLengthUnit: " + lengthValue.getUnit());
		}
	}

	private static SizeCalculation translateRelativeValue(RelativeLengthValue lengthValue) {
		float initialValue = lengthValue.getValue();
		switch (lengthValue.getUnit()) {
		case EM:
			return (context, isHorizontal) -> context.relativeFont().getSize() * initialValue;
		case EX:
			return (context, isHorizontal) -> context.relativeFont().getSize() * initialValue / 2;
		case CAP:
			return (context, isHorizontal) -> context.relativeFont().getCapHeight() * initialValue;
		case CH:
			return (context, isHorizontal) -> getCharacterAdvance(context, '0', .5f) * initialValue;
		case IC:
			return (context, isHorizontal) -> getCharacterAdvance(context, '\u6C34', .5f) * initialValue;
		case REM:
			return (context, isHorizontal) -> context.rootFont().getSize() * initialValue;
		case LH:
			throw new UnsupportedOperationException("LH (Line Height) is not supported yet");
		case RLH:
			throw new UnsupportedOperationException("RLH (Root-Relative Line Height) is not supported yet");
		case VW:
			return (context, isHorizontal) -> context.viewportSize().width() * initialValue / 100;
		case VH:
			return (context, isHorizontal) -> context.viewportSize().height() * initialValue / 100;
		case VI:
			throw new UnsupportedOperationException("VI (Viewport Inline Size) is not supported yet");
		case VB:
			throw new UnsupportedOperationException("VB (Viewport Block Size) is not supported yet");
		case VMIN:
			return (context, isHorizontal) -> Math.min(context.viewportSize().width(), context.viewportSize().height()) * initialValue / 100;
		case VMAX:
			return (context, isHorizontal) -> Math.max(context.viewportSize().width(), context.viewportSize().height()) * initialValue / 100;
		default:
			throw new UnsupportedOperationException("Unrecognized RelativeLengthUnit: " + lengthValue.getUnit());
		}
	}

	private static float translateBoxPercentageValue(SizeCalculationContext context, PercentageValue percentageValue, boolean isHorizontal) {
		float axisValue = isHorizontal ?
			context.parentSize().width() :
			context.parentSize().height();
		if (axisValue == RelativeDimension.UNBOUNDED) return RelativeDimension.UNBOUNDED;
		return axisValue * percentageValue.getValue() / 100;
	}

	private static float getCharacterAdvance(SizeCalculationContext context, char c, float fallback) {
		// TODO: Detect inline axis
		float zeroWidth = context.relativeFont().getCharacterWidth('0');
		if (zeroWidth > 0) return zeroWidth;
		return context.relativeFont().getHeight() * fallback;
	}

}
