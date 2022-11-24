package fr.themicrospace.graphics;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.util.glu.GLU;

import static org.lwjgl.opengl.GL11.*;

public class DisplayComponent {
	
	private DisplayMode mode;
	private int scale = 2, width = 720 / scale, height = 480 / scale;
	
	public DisplayComponent() throws LWJGLException {
		
		mode = new DisplayMode(width * scale, height * scale);
		
		Display.setDisplayMode(mode);
		Display.setResizable(true);
		Display.setFullscreen(false);
		Display.setTitle("TheMicroSpace");
		Display.create();

	}
	
	public void updateDisplay() {
		width = Display.getWidth() / scale;
		height = Display.getHeight() / scale;
		Display.update();
	}
	
	public boolean isCloseRequested() {
		return Display.isCloseRequested();
	}
	
	public void dispose() {
		Display.destroy();
	}
	
	
	public void view2D() {
		glViewport(0, 0, width * scale, height * scale);
		
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		GLU.gluOrtho2D(0, width, height, 0);
		glMatrixMode(GL_MODELVIEW);
		glLoadIdentity();
		
		glEnable(GL_TEXTURE_2D);
		
		glEnable(GL_BLEND);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
	}
	
	public int getHeight() {
		return height;
	}
	
	public int getWidth() {
		return width;
	}
}
