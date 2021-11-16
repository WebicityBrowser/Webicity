package everyos.browser.spec.jcss.cssom.property;

public class InheritProperty implements Property {
	
	private PropertyName name;

	public InheritProperty(PropertyName name) {
		this.name = name;
	}

	@Override
	public PropertyName getPropertyName() {
		return name;
	}

}
