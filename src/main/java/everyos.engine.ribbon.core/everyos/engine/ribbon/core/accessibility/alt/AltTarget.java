package everyos.engine.ribbon.core.accessibility.alt;

import java.util.Optional;

public interface AltTarget {

	AltLabel getLabel();
	//TODO: Check that this approach would actually work
	Optional<AltTarget> execAction();
	
	//TODO: Or should we have a getChildren() instead?
	AltTarget getNext();
	AltTarget getPrev();
	
}
