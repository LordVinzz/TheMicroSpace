package fr.themicrospace.engine;

import org.lwjgl.util.vector.Matrix2f;

import fr.themicrospace.graphics.Texture;

public class Bullet extends GameObject{

	private Transform positions = new Transform(0,0,1);
	private BulletVelocity velocity = new BulletVelocity(positions, 0F, 0.005F, 16F);
	private Sprite sprite = new Sprite(Texture.stone, this);
	private Lifespan lifespan = new Lifespan(180);
	private Collider collider;
	
	public Bullet(float x, float y, float vx, float vy) {
		super(true);
		positions.setX(x);
		positions.setY(y);
		velocity.setVx(vx);
		velocity.setVy(vy);
		addAttribute(positions);
		addAttribute(velocity);
		addAttribute(new Transform(8, 8, "Quad"));
		addAttribute(new Transform(0, 0, "SpriteBase"));
		addAttribute(new Transform(1, 1, "SpriteOffset"));
		addAttribute(lifespan);
		collider = new Collider(true, this);
		addAttribute(collider);
		addAttribute(sprite);
	}

	@Override
	public void update() {
		lifespan.updateLifespan();
		velocity.update();
	}
	
	private class BulletVelocity extends Velocity{

		public BulletVelocity(Matrix2f attribute, Transform positions) {
			super(attribute, positions);
		}
		
		public BulletVelocity(Transform positions, float f, float g, float h) {
			super(positions,f,g,h);
		}
		
		@Override
		public void update() {
			attribute.m00 += attribute.m10 * (maxA - attribute.m00);
			attribute.m01 += attribute.m11 * (maxA - attribute.m01);
			positions.setX(positions.getX() + attribute.m00);
			positions.setY(positions.getY() + attribute.m01);
		}
		
	}

	
	@Override
	public void collideWith(GameObject go) {
	}
}
