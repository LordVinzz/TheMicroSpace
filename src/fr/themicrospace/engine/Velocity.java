package fr.themicrospace.engine;

import org.lwjgl.util.vector.Matrix2f;

public class Velocity extends Attribute<Matrix2f>{

	private Transform positions;
	
	protected float maxA;
	
	public Velocity(Transform positions, float aX, float aY, float maxA) {
		super(null, "Velocity");
		attribute = new Matrix2f();
		attribute.setZero();
		attribute.m10 = aX;
		attribute.m11 = aY;
		this.positions = positions;
		this.maxA = maxA;
	}
	
	public Velocity(Matrix2f attribute, Transform positions) {
		super(attribute, "Velocity");
		this.positions = positions;
	}
	
	/*     | 0_ | 1_ |
	 *  ---+----+----+
	 *  _0 | vx | ax |
	 *  ---+----+----+
	 *  _1 | vy | ay |
	 */
	
	public void update() {}
	
	public Transform predict() {
		return null;
	}
	
	public void setAx(float aX) {
		attribute.m10 = aX;
	}
	
	public void setAy(float aY) {
		attribute.m11 = aY;
	}
	
	public void addAx(float aX) {
		attribute.m10 += aX;
	}
	
	public void addAy(float aY) {
		attribute.m11 += aY;
	}
	
	public void setVx(float vX) {
		attribute.m00 = vX;
	}
	
	public void setVy(float vY) {
		attribute.m01 = vY;
	}
	
	public void addVx(float vX) {
		attribute.m00 += vX;
	}
	
	public void addVy(float vY) {
		attribute.m01 += vY;
	}
	
	public float getVx() {
		return attribute.m00;
	}
	
	public float getVy() {
		return attribute.m01;
	}
	
	public float getAx() {
		return attribute.m10;
	}
	
	public float getAy() {
		return attribute.m11;
	}

}
