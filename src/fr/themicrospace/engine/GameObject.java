package fr.themicrospace.engine;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public abstract class GameObject implements Serializable{
	
	private static final long serialVersionUID = -7985025765778494785L;

	protected Map<String, Attribute<?>> map = new HashMap<String, Attribute<?>>();
	
	public GameObject(boolean isUpdatable) {
		addAttribute(new Attribute<Boolean>(isUpdatable, "Updatable") {
			private static final long serialVersionUID = 7531151939127373107L;
		});
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
