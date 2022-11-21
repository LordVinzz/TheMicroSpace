package fr.themicrospace.main;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.GL11;

import fr.themicrospace.engine.ResourceManager;
import fr.themicrospace.graphics.DisplayComponent;

public class TheMicroSpace {

	private boolean isLooping = false;
	private DisplayComponent display = null;

	public void start() {
		isLooping = true;
		try {
			display = new DisplayComponent();
		} catch (LWJGLException e) {
			e.printStackTrace();
			System.exit(1);
		}
		loop();
		display.dispose();
	}

	public void update() {
		rm.update();
	}


	public void render() {
		display.view2D();
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);

		rm.updateCamera();
		rm.render();
	}

	ResourceManager rm = new ResourceManager();
	public void loop() {

		rm.init();
		
		
		while (isLooping) {
			if (display.isCloseRequested()) isLooping = false;
			display.updateDisplay();
			update();
			render();

			synchronized (this) {
				try {
					this.wait(16, 0);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}

		}
	}
	
	public static TheMicroSpace getInstance() {
		return Main.getTms();
	}
	
	public ResourceManager getResourceManager() {
		return rm;
	}

	public boolean isLooping() {
		return isLooping;
	}

	public DisplayComponent getDisplay() {
		return display;
	}
	
}
