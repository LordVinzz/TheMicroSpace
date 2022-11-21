package fr.themicrospace.engine;

import java.util.HashMap;
import java.util.Map;

public abstract class GameObject {
	
	protected Map<String, Attribute<?>> map = new HashMap<String, Attribute<?>>();
	
	public GameObject(boolean isUpdatable) {
		addAttribute(new Attribute<Boolean>(isUpdatable, "Updatable") {});
	}
	
	public Attribute<?> getAttribute(String name) {
		return map.get(name);
	}
	
	public void addAttribute(Attribute<?> attribute) {
		map.put(attribute.getAttributeName(), attribute);
	}
	
	public abstract void update();
	public abstract void collideWith(GameObject go);
}
