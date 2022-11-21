package fr.themicrospace.engine.entities;

import org.lwjgl.util.vector.Matrix2f;

import fr.themicrospace.engine.Bullet;
import fr.themicrospace.engine.Collider;
import fr.themicrospace.engine.GameObject;
import fr.themicrospace.engine.Sprite;
import fr.themicrospace.engine.Timer;
import fr.themicrospace.engine.Transform;
import fr.themicrospace.graphics.Texture;
import fr.themicrospace.engine.Velocity;

public class Hoopa extends GameObject{

	private Transform positions = new Transform(0,0,0);
	private Sprite sprite = new Sprite(Texture.mob, this);
	private HoopaVelocity velocity = new HoopaVelocity(positions, 0F, 0.005F, 16F);
	private Collider collider;
	private Timer timer = new Timer(20);
	
	public Hoopa(float x, float y) {
		super(true);
		positions.setX(x);
		positions.setY(y);
		addAttribute(positions);
		addAttribute(new Transform(24,16, "Quad"));
		addAttribute(new Transform(0,0,"SpriteBase"));
		addAttribute(new Transform(1,1,"SpriteOffset"));
		collider = new Collider(true, this);
		addAttribute(collider);
		addAttribute(sprite);
		addAttribute(timer);
		timer.start();
	}

	@Override
	public void update() {
		if(Math.random() > 0.85F) timer.update();
		if(timer.isReady()) {
			timer.reset();
			timer.start();
			velocity.changeDirection();
		}
		velocity.update();
	}

	@Override
	public void collideWith(GameObject go) {}
	
	private class HoopaVelocity extends Velocity {
		
		private int direction = 0;
		private boolean tGroundedPrev = false, bGroundedPrev = false, lGroundedPrev = false, rGroundedPrev = false;
		
		public HoopaVelocity(Matrix2f attribute, Transform positions) {
			super(attribute, positions);
		}
		
		public HoopaVelocity(Transform positions, float f, float g, float h) {
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
				changeDirection();
			}
			if(rGroundedPrev != collider.isRGrounded() && collider.isRGrounded()) {
				positions.setX(positions.getX() - collider.getPx2());
				velocity.setVx(0);
				changeDirection();
			}
			
			if(direction == 1) {
				setVx(3);
			}else {
				setVx(-3);
			}
			
			if(!collider.isTGrounded()) {
				
				attribute.m00 += attribute.m10 * (maxA - attribute.m00);
				attribute.m01 += attribute.m11 * (maxA - attribute.m01);
			}
			if((!collider.isLGrounded() && attribute.m00 > 0) || (!collider.isRGrounded() && attribute.m00 < 0)) {
				positions.setX(positions.getX() + attribute.m00);
			}
			
			if((!collider.isBGrounded() && attribute.m01 < 0) || (!collider.isTGrounded() && attribute.m01 > 0)) {
				positions.setY(positions.getY() + attribute.m01);
			}
			
			
			tGroundedPrev = collider.isTGrounded();
			bGroundedPrev = collider.isBGrounded();
			lGroundedPrev = collider.isLGrounded();
			rGroundedPrev = collider.isRGrounded();
			
		}
		
		public void changeDirection() {
			direction = 1 - direction;
		}
		
	}

}
