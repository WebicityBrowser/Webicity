package everyos.browser.spec.jcss.cssom.property;

public class InheritOrDefaultProperty extends InheritProperty {

	private Property property;

	public InheritOrDefaultProperty(Property property) {
		super(property.getPropertyName());
		
		this.property = property;
	}

	public Property getDefault() {
		return this.property;
	}

}
