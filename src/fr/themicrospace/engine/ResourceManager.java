package fr.themicrospace.engine;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import fr.themicrospace.graphics.Renderer;

public class ResourceManager {

	private Renderer renderer = new Renderer();
	
	private List<GameObject> updatables = new ArrayList<GameObject>();
	private List<GameObject> gameObjects = new ArrayList<GameObject>();
	private List<GameObject> shortlived = new ArrayList<GameObject>();
	
	private List<GameObject> queue = new ArrayList<GameObject>();
	
	private Camera camera;
	
	private boolean isUpdating = false;
	private boolean reloadFlag = false;
	
	public void init() {
		List<GameObject> go = MapReader.read("/map.png");
		
		for(GameObject gameObj : go) {
			addResource(gameObj);
		}
				
		try(FileOutputStream fos = new FileOutputStream("save.dat");
			ObjectOutputStream oos = new ObjectOutputStream(fos);) {
			
			oos.writeObject(go);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void addResource(GameObject go) {
		if(!isUpdating) {
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
			
			if((b = go.getAttribute("Lifespan")) != null) {
				shortlived.add(go);
			}
		}else {
			queue.add(go);
		}
	}
	
	public void render() {
		renderer.render();
	}

	public void update() {
		isUpdating = true;
		for(GameObject obj : updatables) {
			obj.update();
		}
		for(GameObject obj : updatables) {
			Collider collider;
			if((collider = (Collider) obj.getAttribute("Collider")) != null) {
				collider.passObjects(new ArrayList<GameObject>(gameObjects));
			}
		}
		isUpdating = false;
		
		for(GameObject go : queue) {
			addResource(go);
		}
		
		for(Iterator<GameObject> it = shortlived.iterator(); it.hasNext();) {
			GameObject go = it.next();
			Lifespan ls = (Lifespan)go.getAttribute("Lifespan");

			if(ls.value() < 0) {
				renderer.remove((Sprite)go.getAttribute("Sprite"));
				updatables.remove(go);
				gameObjects.remove(go);
				it.remove();
			}
		}
		
		queue.clear();
		if(reloadFlag) {
			updatables.clear();
			shortlived.clear();
			gameObjects.clear();
			queue.clear();
			renderer.clear();
			
			reload();
			reloadFlag = false;
		}
	}
	
	public void updateCamera() {
		camera.update();
	}

	public void reload() {
		File file = new File("save.dat");
		if(!file.exists()) {
			System.exit(0);
		}
		
		try (FileInputStream fis = new FileInputStream(file);
				ObjectInputStream ois = new ObjectInputStream(fis)){
			
			List<GameObject> gameObjects = (List<GameObject>) ois.readObject();
			
			for(GameObject obj : gameObjects) {
				addResource(obj);
			}
			
			
			camera.grabDisplay();
			
		}catch(IOException | ClassNotFoundException e) {
			e.printStackTrace();
			System.exit(1);
		}
	}

	public void setReloadFlag() {
		reloadFlag = true;
	}
	
}
