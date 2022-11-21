package fr.themicrospace.engine;

import org.lwjgl.opengl.GL11;

import fr.themicrospace.graphics.Texture;

public class Sprite extends Attribute<Texture>{

	private GameObject ref;
	private byte light = -1;
	
	public Sprite(Texture attribute, GameObject ref) {
		super(attribute, "Sprite");
		this.ref = ref;
	}
	
	public int getTextureId() {
		return this.value().getId();
	}
	
	public Transform getCoords() {
		return (Transform) ref.getAttribute("Transform");
	}
	
	public void render() {
		if(ref.getAttribute("Transform") instanceof Transform transform &&
		   ref.getAttribute("Quad") instanceof Transform quad) {
			
			Transform sO = (Transform) ref.getAttribute("SpriteOffset"),
					  sB = (Transform) ref.getAttribute("SpriteBase");
			GL11.glColor3ub(light, light, light);
			if(sO == null || sB == null) {			
				GL11.glVertex2f(transform.x(), transform.y());
				GL11.glVertex2f(transform.x() + quad.x(), transform.y());
				GL11.glVertex2f(transform.x() + quad.x(), transform.y() + quad.y());
				GL11.glVertex2f(transform.x(), transform.y() + quad.y());
			} else {
				GL11.glTexCoord2f(sB.x(), sB.y());
				GL11.glVertex2f(transform.x(), transform.y());
				
				GL11.glTexCoord2f(sB.x() + sO.x(), sB.y());
				GL11.glVertex2f(transform.x() + quad.x(), transform.y());
				
				GL11.glTexCoord2f(sB.x() + sO.x(), sB.y() + sO.y());
				GL11.glVertex2f(transform.x() + quad.x(), transform.y() + quad.y());
				
				GL11.glTexCoord2f(sB.x(), sB.y() + sO.y());
				GL11.glVertex2f(transform.x(), transform.y() + quad.y());

			}
		}
	}

	public void setLight(byte i) {
		this.light = i;
	}
}
