package fr.themicrospace.engine;

public class TriggerBehavior extends Attribute<Behavior>{
	
	public TriggerBehavior(Behavior movement) {
		super(movement, "TriggerBehavior");
	}

	public void applyBehavior(GameObject gameObject) {
		Velocity v = (Velocity) gameObject.getAttribute("Velocity");
		v.setAx(attribute.value().x * attribute.value().y);
		System.out.println(v);
	}

}
