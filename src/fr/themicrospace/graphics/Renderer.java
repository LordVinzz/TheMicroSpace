package fr.themicrospace.graphics;

import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.glBindTexture;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import org.lwjgl.opengl.GL11;

import fr.themicrospace.engine.Sprite;
import fr.themicrospace.engine.Transform;

public class Renderer {

	private Map<Integer, ArrayList<Sprite>> renderables;
	private RendererComparator comparator = new RendererComparator();
	
	public Renderer() {
		renderables = new TreeMap<Integer, ArrayList<Sprite>>(Comparator.naturalOrder());
	}
	
	public void render() {
		if(renderables.isEmpty()) return;
		Integer currentTexture = renderables.get(0).get(0).value();
		glBindTexture(GL_TEXTURE_2D, currentTexture);
		GL11.glBegin(GL11.GL_QUADS);
		
		for (Entry<Integer, ArrayList<Sprite>> mapentry : renderables.entrySet()) {
			ArrayList<Sprite> list = mapentry.getValue();
			for(Sprite sprite : list) {
				if(sprite.value() != currentTexture) {
					currentTexture = sprite.value();
					GL11.glEnd();
					
					glBindTexture(GL_TEXTURE_2D, currentTexture);
					GL11.glBegin(GL11.GL_QUADS);
				}
				
				sprite.render();
			}
		}
		
		
		GL11.glEnd();
		Texture.unbind();
	}
	
	public void add(Sprite sprite) {
		ArrayList<Sprite> list = new ArrayList<Sprite>();
		Transform t = sprite.getCoords();
		if(renderables.get((int)t.getZ()) == null) {
			list.add(sprite);
			renderables.put((int)t.getZ(), list);
		}else {
			list = renderables.get((int)t.getZ());
			list.add(sprite);
			Collections.sort(list, comparator);
		}
	}

	public void clear() {
		renderables.clear();
	}
	
	private class RendererComparator implements Comparator<Sprite>{

		@Override
		public int compare(Sprite o1, Sprite o2) {
			return o1.getTextureId() - o2.getTextureId();
		}
		
	}

	public void remove(Sprite sprite) {
		Transform t = sprite.getCoords();
		renderables.get((int)t.getZ()).remove(sprite);
	}
	
}
