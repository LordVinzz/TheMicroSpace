package fr.themicrospace.engine;

import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

public class Transform extends Attribute<Vector3f>{

	public Transform(Vector3f attribute, String customName) {
		super(attribute, customName);
	}
	
	public Transform(Vector3f attribute) {
		super(attribute, "Transform");
	}
	
	public Transform(float x, float y, float z) {
		super(new Vector3f(x,y,z), "Transform");
	}
	
	public Transform(float x, float y, float z, String customName) {
		super(new Vector3f(x,y,z), customName);
	}
	
	public Transform(Vector2f attribute) {
		super(new Vector3f(attribute.x, attribute.y, 0), "Transform");
	}
	
	public Transform(float x, float y) {
		super(new Vector3f(x,y,0), "Transform");
	}
	
	public Transform(float x, float y, String customName) {
		super(new Vector3f(x,y,0), customName);
	}
	
	public float x() {
		return this.value().getX();
	}
	
	public void x(float x) {
		this.value().setX(x);
	}
	
	public float y() {
		return this.value().getY();
	}
	
	public void y(float y) {
		this.value().setY(y);
	}
	
	public float z() {
		return this.value().getZ();
	}
	
	public void z(float z) {
		this.value().setZ(z);
	}

	public Transform copy() {
		return new Transform(this.x(), this.y(), this.z());
	}
	
	
}
