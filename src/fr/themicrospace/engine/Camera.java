package fr.themicrospace.engine;

import org.lwjgl.opengl.GL11;

import fr.themicrospace.graphics.DisplayComponent;
import fr.themicrospace.main.TheMicroSpace;

public class Camera extends Attribute<Transform>{

	private static final long serialVersionUID = -8680226944660531662L;
	private transient DisplayComponent display;
	
	public Camera(Transform attribute) {
		super(attribute, "Camera");
		grabDisplay();
	}
	
	public void grabDisplay() {
		display = TheMicroSpace.getInstance().getDisplay();
	}
	
	public void update() {
		float cx = display.getWidth() / 2 - attribute.getX();
		float cy = display.getHeight() / 2 - attribute.getY();
		GL11.glTranslatef(cx, cy, 0);
	}

}
