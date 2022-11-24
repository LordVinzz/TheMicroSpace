package fr.themicrospace.engine;

import java.util.ArrayList;

public class Collider extends Attribute<Boolean>{

	private static final long serialVersionUID = 1716342284891331702L;

	private ArrayList<GameObject> gameObjects;
	private GameObject ref;
	private boolean tGrounded = false, bGrounded = false, lGrounded = false, rGrounded = false;
	private float px1, px2, py1, py2;
	
	private Transform positions;

	@SuppressWarnings("unused")
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
			
			if ((px1 >= 0) && (py1 >= 0) && (px2 <= 0) && (py2 <= 0)) {
				if (objC.value()) {
					ref.collideWith(obj);
				} else {
					switch (closerToZero(px1, py1, px2, py2)) {
					case 1:
						this.px1 = px1;
						lGrounded = true;
						break;
					case 2:
						this.py1 = py1;
						tGrounded = true;
						break;
					case 3:
						this.px2 = px2;
						rGrounded = true;
						break;
					case 4:
						this.py2 = py2;
						bGrounded = true;
						break;
					}
				}
			} else {
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
		if(f20 <= f10 && f20 <= f30 && f20 <= f40) {
			return 2;
		}
		if(f10 <= f30 && f10 <= f40) {
			return 1;
		}
		if(f30 <= f40) {
			return 3;
		}
		return 4;
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
	
	public float getPx1() {
		return px1;
	}public float getPx2() {
		return px2;
	}public float getPy1() {
		return py1;
	}public float getPy2() {
		return py2;
	}

}
