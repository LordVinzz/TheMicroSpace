package fr.themicrospace.engine;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import fr.themicrospace.engine.entities.Hoopa;
import fr.themicrospace.engine.entities.Player;
import fr.themicrospace.engine.entities.Sprinkler;
import fr.themicrospace.engine.entities.Wall;
import fr.themicrospace.graphics.Texture;

public class MapReader {

	public static List<GameObject> read(String map){
		List<GameObject> list = new ArrayList<GameObject>();
		BufferedImage image = null;
		try {
			image = ImageIO.read(Texture.class.getResource(map));
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(2);
		}

		int w = image.getWidth();
		int h = image.getHeight();

		int[] pixels = new int[w * h];
		image.getRGB(0, 0, w, h, pixels, 0, w);

		Player p = new Player(0,0);
		for (int y = 0; y < h; y++) {
			for (int x = 0; x < w; x++) {
				int i = pixels[x + y * w];

				if(i == 0xFF000000) {
					Wall w1 = new Wall(x*32, y*32, 32, 32);
					w1.setLight((byte)(200 + (x*y) % 55));
					list.add(w1);
				}
				if(i == 0xFFFF0000) {
					p.setCoords(x*32, y*32);
					list.add(p);
				}
				if(i == 0xFF00FF00) {
					list.add(new Sprinkler(x*32, y*32));
				}
				if(i == 0xFF0000FF) {
					//TODO list.add(new Booster(x*32, y*32, true));
					list.add(new Hoopa(x*32, y*32, p));
				}
			}
		}

		
		return list;
	}
	
}
