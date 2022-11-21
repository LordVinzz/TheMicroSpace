package fr.themicrospace.engine.entities;

import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Matrix2f;

import fr.themicrospace.engine.Camera;
import fr.themicrospace.engine.Collider;
import fr.themicrospace.engine.GameObject;
import fr.themicrospace.engine.Sprite;
import fr.themicrospace.engine.Transform;
import fr.themicrospace.engine.Velocity;
import fr.themicrospace.graphics.Texture;

public class Player extends GameObject {

	private Transform positions = new Transform(0,0,0);
	private PlayerVelocity velocity = new PlayerVelocity(positions, 0F, 0.005F, 16F);
	private Sprite sprite = new Sprite(Texture.characters, this);
	private Collider collider;
	private Camera camera;

	public Player(float x, float y) {
		super(true);
		positions.setX(x);
		positions.setY(y);
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
	}

	public void updateCam() {
		camera.update();
	}
	
	private class PlayerVelocity extends Velocity{
		
		boolean tGroundedPrev = false, bGroundedPrev = false, lGroundedPrev = false, rGroundedPrev = false;
		
		public PlayerVelocity(Matrix2f attribute, Transform positions) {
			super(attribute, positions);
		}
		
		

		public PlayerVelocity(Transform positions, float f, float g, float h) {
			super(positions,f,g,h);
		}

		@Override
		public void update() {
			
			if(tGroundedPrev != collider.isTGrounded() && collider.isTGrounded()) {
				positions.setY(positions.getY() - collider.getPy1());
				velocity.setVy(0);
			}
			if(bGroundedPrev != collider.isBGrounded() && collider.isBGrounded()) {
				positions.setY(positions.getY() - collider.getPy2());
				velocity.setVy(0);
			}
			if(lGroundedPrev != collider.isLGrounded() && collider.isLGrounded()) {
				positions.setX(positions.getX() - collider.getPx1());
				velocity.setVx(0);
			}
			if(rGroundedPrev != collider.isRGrounded() && collider.isRGrounded()) {
				positions.setX(positions.getX() - collider.getPx2());
				velocity.setVx(0);
			}
			
			
			if(!collider.isTGrounded()) {
				
				attribute.m00 += attribute.m10 * (maxA - attribute.m00); //adds acceleration to speed
				attribute.m01 += attribute.m11 * (maxA - attribute.m01);
			}
			if((!collider.isLGrounded() && attribute.m00 > 0) || (!collider.isRGrounded() && attribute.m00 < 0)) {
				positions.setX(positions.getX() + attribute.m00);
			}
			positions.setY(positions.getY() + attribute.m01);
			
			
			tGroundedPrev = collider.isTGrounded();
			bGroundedPrev = collider.isBGrounded();
			lGroundedPrev = collider.isLGrounded();
			rGroundedPrev = collider.isRGrounded();
		}
		
		@Override
		public Transform predict() {
			Transform transform = new Transform(positions);
			transform.setX(positions.getX() + attribute.m00);
			transform.setY(positions.getY() + attribute.m00);
			return transform;
		
		}
		
	}
}
