package fr.themicrospace.engine;

public class Lifespan extends Attribute<Integer>{

	private static final long serialVersionUID = -278560394361576463L;

	public Lifespan(Integer attribute) {
		super(attribute, "Lifespan");
	}
	
	public void updateLifespan() {
		this.attribute--;
	}
	
}
