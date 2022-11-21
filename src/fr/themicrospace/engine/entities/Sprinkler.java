package fr.themicrospace.engine.entities;

import fr.themicrospace.engine.Bullet;
import fr.themicrospace.engine.Collider;
import fr.themicrospace.engine.GameObject;
import fr.themicrospace.engine.ResourceManager;
import fr.themicrospace.engine.Sprite;
import fr.themicrospace.engine.Timer;
import fr.themicrospace.engine.Transform;
import fr.themicrospace.graphics.Texture;
import fr.themicrospace.main.Main;
import fr.themicrospace.main.TheMicroSpace;

public class Sprinkler extends GameObject{
	
	private Transform positions = new Transform(0,0,0);
	private Sprite sprite = new Sprite(Texture.mob, this);
	private Collider collider;
	private Timer timer = new Timer(20);
	
	private ResourceManager rm = TheMicroSpace.getInstance().getResourceManager();
	
	public Sprinkler(float x, float y) {
		super(true);
		positions.setX(x);
		positions.setY(y);
		addAttribute(positions);
		addAttribute(new Transform(32,32, "Quad"));
		addAttribute(new Transform(0,0,"SpriteBase"));
		addAttribute(new Transform(1,1,"SpriteOffset"));
		collider = new Collider(true, this);
		addAttribute(collider);
		addAttribute(sprite);
		addAttribute(timer);
		timer.start();
	}

	@Override
	public void update() {
		timer.update();
		if(timer.isReady()) {
			timer.reset();
			timer.start();
			rm.addResource(new Bullet(positions.getX() + 12, positions.getY()+12, (float)Math.random()*20 - 10F, -5F - (float)Math.random() * 3F));
		}
	}

	
	@Override
	public void collideWith(GameObject go) {
		
	}

}
