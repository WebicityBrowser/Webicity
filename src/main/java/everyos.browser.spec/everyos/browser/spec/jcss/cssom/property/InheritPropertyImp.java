package everyos.browser.spec.jcss.cssom.property;

public class InheritPropertyImp implements InheritProperty {

	private Property defaultProperty;

	public InheritPropertyImp(Property defaultProperty) {
		this.defaultProperty = defaultProperty;
	}

	public Property getDefault() {
		return this.defaultProperty;
	}

	@Override
	public PropertyName getPropertyName() {
		return defaultProperty.getPropertyName();
	}

}
