package fr.themicrospace.engine;

public class Lifespan extends Attribute<Integer>{

	public Lifespan(Integer attribute) {
		super(attribute, "Lifespan");
	}
	
	public void updateLifespan() {
		this.attribute--;
	}
	
}
