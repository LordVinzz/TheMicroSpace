package fr.themicrospace.engine;

import java.util.ArrayList;

import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Matrix2f;

public class Collider extends Attribute<Boolean>{

	private ArrayList<GameObject> gameObjects;
	private GameObject ref;
	private boolean tGrounded = false, bGrounded = false, lGrounded = false, rGrounded = false;
	
	private Transform positions;
	private Velocity velocity;
	private Transform quad;
	
	public Collider(Boolean attribute, GameObject ref) {
		super(attribute, "Collider");
		this.ref=ref;
		positions = (Transform) ref.getAttribute("Transform");
		velocity = (Velocity) ref.getAttribute("Velocity");
		quad = (Transform) ref.getAttribute("Quad");
	}
	
	public void passObjects(ArrayList<GameObject> arrayList) {
		tGrounded = false;
		bGrounded = false;
		lGrounded = false;
		rGrounded = false;
		
		gameObjects = arrayList;
		gameObjects.remove(ref);
		
		for(GameObject obj : gameObjects) {
			Transform objP = (Transform) obj.getAttribute("Transform");
			Transform objQ = (Transform) obj.getAttribute("Quad");
			Collider objC = (Collider) obj.getAttribute("Collider");
			
			float px1 = positions.value().x + quad.value().x - objP.value().x;
			float py1 = positions.value().y + quad.value().y - objP.value().y;
			float px2 = positions.value().x - objP.value().x - objQ.value().x;
			float py2 = positions.value().y - objP.value().y - objQ.value().y;
			if ((px1 > 0) && (py1 > 0) && (px2 < 0) && (py2 < 0)) {
				if (objC.value()) {
					TriggerBehavior tb = (TriggerBehavior) obj.getAttribute("TriggerBehavior");
					ref.applyBehavior(tb);
				} else {
					switch (closerToZero(px1, py1, px2, py2)) {
					case 1:
						positions.x(positions.x() - px1);
						velocity.setVx(0);
						lGrounded = true;
						obj.setLight((byte) 255);
						break;
					case 2:
						positions.y(positions.y() - py1);
						velocity.setVy(0);
						tGrounded = true;
						obj.setLight((byte) 255);
						break;
					case 3:
						positions.x(positions.x() - px2);
						velocity.setVx(0);
						rGrounded = true;
						obj.setLight((byte) 255);
						break;
					case 4:
						positions.y(positions.y() - py2);
						velocity.setVy(0);
						bGrounded = true;
						obj.setLight((byte) 255);
						break;
					}
				}
			} else {
				obj.setLight((byte) 127);
				tGrounded |= false;
				rGrounded |= false;
				lGrounded |= false;
				bGrounded |= false;
			}
		}
	}
	
	private int closerToZero(float f1, float f2, float f3, float f4) {
		float f10 = Math.abs(f1),
				f20 = Math.abs(f2),
				f30 = Math.abs(f3),
				f40 = Math.abs(f4);
		if(f10 <= f20 && f10 <= f30 && f10 <= f40) {
			return 1;
		}
		if(f20 <= f30 && f20 <= f40) {
			return 2;
		}
		if(f30 <= f40) {
			return 3;
		}
		return 4;
	}
	
	public Transform predict(Transform pos, Velocity vel) {
		Matrix2f mat = vel.predict();
		Transform t = pos.copy();
		
		t.x(t.x() + mat.m00);
		t.y(t.y() + mat.m01);
		return t;
	}
	
	public boolean isTGrounded() {
		return tGrounded;
	}
	
	public boolean isLGrounded() {
		return lGrounded;
	}
	public boolean isRGrounded() {
		return rGrounded;
	}
	
	public boolean isBGrounded() {
		return bGrounded;
	}

}
