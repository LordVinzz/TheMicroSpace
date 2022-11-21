package fr.themicrospace.engine;

import java.util.ArrayList;
import java.util.List;

import fr.themicrospace.graphics.Renderer;

public class ResourceManager {

	private Renderer renderer = new Renderer();
	
	private List<GameObject> updatables = new ArrayList<GameObject>();
	private List<GameObject> gameObjects = new ArrayList<GameObject>();
	private Camera camera;
	
	public void init() {
		List<GameObject> go = MapReader.read("/map.png");
		for(GameObject gameObj : go) {
			addResource(gameObj);
		}
	}
	
	public void addResource(GameObject go) {
		Sprite spr;
		if((spr = (Sprite) go.getAttribute("Sprite")) != null) {
			renderer.add(spr);
		}
		
		Attribute<?> b;
		if((b = go.getAttribute("Updatable")) != null) {
			if((Boolean)b.value()) updatables.add(go);
		}
		
		if((b = go.getAttribute("Collider")) != null) {
			gameObjects.add(go);
		}
		
		if((b = go.getAttribute("Camera")) != null) {
			camera = (Camera) go.getAttribute("Camera");
		}
	}
	
	public void render() {
		renderer.render();
	}

	public void update() {
		for(GameObject obj : updatables) {
			obj.update();
		}
		for(GameObject obj : updatables) {
			Collider collider;
			if((collider = (Collider) obj.getAttribute("Collider")) != null) {
				collider.passObjects(new ArrayList<GameObject>(gameObjects));
			}
		}
	}
	
	public void updateCamera() {
		camera.update();
	}
	
}
