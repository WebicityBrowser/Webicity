package com.github.webicitybrowser.spec.css.property.fontweight;

import com.github.webicitybrowser.spec.css.property.CSSValue;

public interface FontWeightValue extends CSSValue {

	int getWeight(int parentWeight);

}