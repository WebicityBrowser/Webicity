package com.github.webicitybrowser.spiderhtml.misc;

import com.github.webicitybrowser.spec.html.parse.tree.HTMLLeaf;

public record InsertionLocation(HTMLLeaf parent, HTMLLeaf before) {

}
