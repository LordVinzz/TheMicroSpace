package fr.themicrospace.engine;

public abstract class Attribute<T> {

	protected T attribute;
	protected String attributeName;
	
	public Attribute(T attribute, String attributeName) {
		this.attribute = attribute;
		this.attributeName = attributeName;
	}
	
	public T value() {
		return attribute;
	}
	
	public void setValue(T attribute) {
		this.attribute = attribute;
	}
	
	public String getAttributeName() {
		return attributeName;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("AttributeName=");
		builder.append(attributeName);
		builder.append(", attribute [attribute=");
		builder.append(attribute);
		builder.append("]");
		return builder.toString();
	}
	
	
	
}
