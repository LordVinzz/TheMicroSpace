package fr.themicrospace.engine.entities;

import org.lwjgl.util.vector.Vector2f;

import fr.themicrospace.engine.Behavior;
import fr.themicrospace.engine.Collider;
import fr.themicrospace.engine.GameObject;
import fr.themicrospace.engine.Sprite;
import fr.themicrospace.engine.Transform;
import fr.themicrospace.engine.TriggerBehavior;
import fr.themicrospace.graphics.Texture;

public class Booster extends GameObject{

	private Transform positions;
	private Sprite sprite = new Sprite(Texture.booster, this);
	private TriggerBehavior tb;
	
	public Booster(float x, float y, boolean flipped) {
		super(false);
		if(!flipped) tb = new TriggerBehavior(new Behavior(new Vector2f(1.4F, 1)));
		else tb = new TriggerBehavior(new Behavior(new Vector2f(1.4F / 2, -1)));
		positions = new Transform(x, y, -1);
		addAttribute(positions);
		addAttribute(tb);
		addAttribute(new Transform(32, 32, "Quad"));
		addAttribute(new Transform(0, 0, "SpriteBase"));
		if(!flipped)addAttribute(new Transform(1, 1, "SpriteOffset"));
		else addAttribute(new Transform(-1, -1, "SpriteOffset"));
		addAttribute(new Collider(true, this));
		addAttribute(sprite);
	}

	@Override
	public void update() {}
	
	@Override
	protected void setLight(byte i) {
		
	}
	
	@Override
	protected void applyBehavior(TriggerBehavior tb) {
	}
	
}
