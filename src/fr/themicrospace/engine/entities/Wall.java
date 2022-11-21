package fr.themicrospace.engine.entities;

import fr.themicrospace.engine.Collider;
import fr.themicrospace.engine.GameObject;
import fr.themicrospace.engine.Sprite;
import fr.themicrospace.engine.Transform;
import fr.themicrospace.graphics.Texture;

public class Wall extends GameObject{
	
	
	private Transform positions;
	private Sprite sprite = new Sprite(Texture.stone, this);

	public Wall(float x, float y, float w, float h) {
		super(false);
		positions = new Transform(x, y, 0);
		addAttribute(positions);
		addAttribute(new Transform(w, h, "Quad"));
		addAttribute(new Transform(0, 0, "SpriteBase"));
		addAttribute(new Transform(1, 1, "SpriteOffset"));
		addAttribute(new Collider(false, this));
		addAttribute(sprite);
	}

	@Override
	public void update() {}

	public void setLight(byte i) {
		sprite.setLight(i);
	}
	
}
