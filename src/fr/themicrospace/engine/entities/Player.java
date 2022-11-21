package fr.themicrospace.engine.entities;

import org.lwjgl.input.Keyboard;

import fr.themicrospace.engine.Camera;
import fr.themicrospace.engine.Collider;
import fr.themicrospace.engine.GameObject;
import fr.themicrospace.engine.Sprite;
import fr.themicrospace.engine.Timer;
import fr.themicrospace.engine.Transform;
import fr.themicrospace.engine.TriggerBehavior;
import fr.themicrospace.engine.Velocity;
import fr.themicrospace.graphics.Texture;

public class Player extends GameObject {

	private Transform positions = new Transform(0,0,0);
	private Velocity velocity = new Velocity(positions, 0F, 0.005F, 16F);
	private Sprite sprite = new Sprite(Texture.characters, this);
	private Collider collider;
	private Camera camera;

	public Player(float x, float y) {
		super(true);
		positions.x(x);
		positions.y(y);
		addAttribute(positions);
		addAttribute(velocity);
		addAttribute(new Transform(32, 32, "Quad"));
		addAttribute(new Transform(0, 0, "SpriteBase"));
		addAttribute(new Transform(1, 1, "SpriteOffset"));
		collider = new Collider(false, this);
		camera = new Camera(positions);
		addAttribute(camera);
		addAttribute(collider);
		addAttribute(sprite);
	}

	@Override
	public void update() {

		if (Keyboard.isKeyDown(Keyboard.KEY_Q) && !collider.isRGrounded()) {
			velocity.setVx(-5F);
		} else if (Keyboard.isKeyDown(Keyboard.KEY_D) && !collider.isLGrounded()) {
			velocity.setVx(5F);
		} else {
			velocity.setVx(velocity.getVx() / 2F);
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_SPACE) && collider.isTGrounded()) {
			velocity.setVy(-3.9F);
		}
		velocity.update();
		velocity.setAx(velocity.getAx() / 1.1F);
	}

	public void updateCam() {
		camera.update();
	}
	
	@Override
	protected void setLight(byte i) {}
	
	@Override
	protected void applyBehavior(TriggerBehavior tb) {
		tb.applyBehavior(this);
	}
	
}
