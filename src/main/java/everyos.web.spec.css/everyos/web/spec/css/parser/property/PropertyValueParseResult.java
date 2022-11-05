package everyos.web.spec.css.parser.property;

import java.util.Optional;

public interface PropertyValueParseResult<T> {

	Optional<T> getResult();
	
	int getLength();
	
}
