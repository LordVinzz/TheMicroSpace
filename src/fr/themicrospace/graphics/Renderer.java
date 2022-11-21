package fr.themicrospace.graphics;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import org.lwjgl.opengl.GL11;

import fr.themicrospace.engine.GameObject;
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
		Texture currentTexture = renderables.get(0).get(0).value();
		currentTexture.bind();
		GL11.glBegin(GL11.GL_QUADS);
		
		for (Entry<Integer, ArrayList<Sprite>> mapentry : renderables.entrySet()) {
			ArrayList<Sprite> list = mapentry.getValue();
			for(Sprite sprite : list) {
				if(sprite.getTextureId() != currentTexture.getId()) {
					currentTexture = sprite.value();
					GL11.glEnd();
					
					currentTexture.bind();
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
		if(renderables.get((int)t.z()) == null) {
			list.add(sprite);
			renderables.put((int)t.z(), list);
		}else {
			list = renderables.get((int)t.z());
			list.add(sprite);
			Collections.sort(list, comparator);
		}
	}

	private class RendererComparator implements Comparator<Sprite>{

		@Override
		public int compare(Sprite o1, Sprite o2) {
			return o1.getTextureId() - o2.getTextureId();
		}
		
	}
	
}
