package fr.themicrospace.engine.entities;

import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Matrix2f;

import fr.themicrospace.engine.Bullet;
import fr.themicrospace.engine.Camera;
import fr.themicrospace.engine.Collider;
import fr.themicrospace.engine.GameObject;
import fr.themicrospace.engine.Sprite;
import fr.themicrospace.engine.Timer;
import fr.themicrospace.engine.Transform;
import fr.themicrospace.engine.Velocity;
import fr.themicrospace.graphics.Texture;
import fr.themicrospace.main.TheMicroSpace;

public class Player extends GameObject {

	private static final long serialVersionUID = 7197739751207776416L;

	private Transform positions = new Transform(0,0,0);
	private PlayerVelocity velocity = new PlayerVelocity(positions, 0F, 0.005F, 16F);
	private Sprite sprite = new Sprite(Texture.characters, this);
	private Timer hitTimer = new Timer(20);
	private byte light = -1;
	private int hitTicks = 0;
	private int hits = 0;
	private boolean isHit = false, godMode = false;
	private Collider collider;
	private Camera camera;

	public Player(float x, float y) {
		super(true);
		positions.setX(x);
		positions.setY(y);
		addAttribute(positions);
		addAttribute(velocity);
		addAttribute(new Transform(16, 24, "Quad"));
		addAttribute(new Transform(0, 0, "SpriteBase"));
		addAttribute(new Transform(1, 1, "SpriteOffset"));
		collider = new Collider(true, this);
		camera = new Camera(positions);
		addAttribute(camera);
		addAttribute(collider);
		addAttribute(sprite);
	}
	
	public void setCoords(float x, float y) {
		positions.setX(x);
		positions.setY(y);
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
		if(Keyboard.isKeyDown(Keyboard.KEY_SPACE) && collider.isTGrounded() && !collider.isBGrounded()) {
			velocity.setVy(-3.4F);
		}
		
		if(Keyboard.isKeyDown(Keyboard.KEY_G)) {
			godMode = true;
		}
		
		if(godMode) {
			hits = 0;
			hitTimer.start();
		}
		
		velocity.update();
		
		if(hitTimer.isReady()) {
			light = (byte) (-1 -light);
			hitTimer.reset();
			hitTicks++;
			if(hitTicks < 6) {
				hitTimer.start();
			}else {
				hitTicks = 0;
				isHit = false;
				if(hits == 3) {
					TheMicroSpace.getInstance().getResourceManager().setReloadFlag();
				}
			}
		}
		hitTimer.update();
		sprite.setLight(light);
	}

	public void updateCam() {
		camera.update();
	}
	
	@Override
	public void collideWith(GameObject go) {
		if(go instanceof Bullet) {
			if(hitTicks == 0 && !isHit) {
				hitTimer.start();
				hits++;
				isHit=true;
			}
		}
		if(go instanceof Hoopa) {
			if(hitTicks == 0 && !isHit) {
				hitTimer.start();
				hits++;
				isHit=true;
			}
		}
	}
	
	private class PlayerVelocity extends Velocity{
		
		private static final long serialVersionUID = 1964294125278359454L;

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
			
			if((!collider.isBGrounded() && attribute.m01 < 0) || (!collider.isTGrounded() && attribute.m01 > 0)) {
				positions.setY(positions.getY() + attribute.m01);
			}
			
			
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
