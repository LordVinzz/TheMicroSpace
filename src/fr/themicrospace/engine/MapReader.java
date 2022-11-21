package fr.themicrospace.engine;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import fr.themicrospace.engine.entities.Booster;
import fr.themicrospace.engine.entities.Player;
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


		for (int y = 0; y < h; y++) {
			for (int x = 0; x < w; x++) {
				int i = pixels[x + y * w];

				if(i == 0xFF000000) {
					list.add(new Wall(x*32, y*32, 32, 32));
				}
				if(i == 0xFFFF0000) {
					list.add(new Player(x*32, y*32));
				}
				if(i == 0xFF00FF00) {
					list.add(new Booster(x*32, y*32, false));
				}
				if(i == 0xFF0000FF) {
					list.add(new Booster(x*32, y*32, true));
				}
			}
		}

		
		return list;
	}
	
}
