package fr.themicrospace.engine;

import org.lwjgl.opengl.GL11;

import fr.themicrospace.graphics.Texture;

public class Sprite extends Attribute<Integer>{

	private static final long serialVersionUID = -2831471745839762029L;

	private GameObject ref;
	private byte light = -1;
	
	public Sprite(Texture texture, GameObject ref) {
		super(texture.getId(), "Sprite");
		this.ref = ref;
	}
	
	public Sprite(Integer attribute, GameObject ref) {
		super(attribute, "Sprite");
		this.ref = ref;
	}
	
	public int getTextureId() {
		return this.value();
	}
	
	public Transform getCoords() {
		return (Transform) ref.getAttribute("Transform");
	}
	
	public void render() {
		if(ref.getAttribute("Transform") instanceof Transform transform &&
		   ref.getAttribute("Quad") instanceof Transform quad) {
			
			Transform sO = (Transform) ref.getAttribute("SpriteOffset"),
					  sB = (Transform) ref.getAttribute("SpriteBase");
			GL11.glColor4ub(light, light, light, light);
			if(sO == null || sB == null) {			
				GL11.glVertex2f(transform.getX(), transform.getY());
				GL11.glVertex2f(transform.getX() + quad.getX(), transform.getY());
				GL11.glVertex2f(transform.getX() + quad.getX(), transform.getY() + quad.getY());
				GL11.glVertex2f(transform.getX(), transform.getY() + quad.getY());
			} else {
				GL11.glTexCoord2f(sB.getX(), sB.getY());
				GL11.glVertex2f(transform.getX(), transform.getY());
				
				GL11.glTexCoord2f(sB.getX() + sO.getX(), sB.getY());
				GL11.glVertex2f(transform.getX() + quad.getX(), transform.getY());
				
				GL11.glTexCoord2f(sB.getX() + sO.getX(), sB.getY() + sO.getY());
				GL11.glVertex2f(transform.getX() + quad.getX(), transform.getY() + quad.getY());
				
				GL11.glTexCoord2f(sB.getX(), sB.getY() + sO.getY());
				GL11.glVertex2f(transform.getX(), transform.getY() + quad.getY());

			}
		}
	}

	public void setLight(byte i) {
		this.light = i;
	}
}
