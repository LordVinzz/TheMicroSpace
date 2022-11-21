package fr.themicrospace.engine;

import org.lwjgl.opengl.GL11;

import fr.themicrospace.graphics.DisplayComponent;
import fr.themicrospace.main.TheMicroSpace;

public class Camera extends Attribute<Transform>{

	private DisplayComponent display;
	
	public Camera(Transform attribute) {
		super(attribute, "Camera");
		display = TheMicroSpace.getInstance().getDisplay();
	}
	public void update() {
		float cx = display.getWidth() / 2 - attribute.x();
		float cy = display.getHeight() / 2 - attribute.y();
		GL11.glTranslatef(cx, cy, 0);
	}

}
