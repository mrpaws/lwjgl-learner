package lwjgl1;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
//import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.*;

public class DisplayTest {
	
	public static int[] screensize = {800, 600};
	private static boolean mleft_clicked = false;
	private static int mouseX = Mouse.getX(); 
	private static int mouseY = Mouse.getY(); 
	
	public void launch() {
		//Define and create display
		try {
			Display.setDisplayMode(new DisplayMode(screensize[0], screensize[1]));
			Display.setTitle("Paw's Game");
			// pleasant green: .1f, .8f, .3f
			// "yellange": 1.3f, .8f, .3f
			Display.setInitialBackground(.1f, .8f, .3f);
		} catch (LWJGLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			Display.create();
		} catch (LWJGLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//initialize OpenGL
		init_OpenGL();
		int quadX = 0, quadY = 0;
		
		//Main render loop (Continue to render display)
		while (!Display.isCloseRequested()) {

			pollInput();
			if (mleft_clicked == true ) {
				quadX = mouseX;
				quadY = mouseY;
			}
			draw_Quad(quadX, quadY);
			Display.update();
		}
	
		
		// Window closed so cleanup
		Display.destroy();
	}
	
	private void init_OpenGL() {
		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glLoadIdentity();
		GL11.glOrtho(0, screensize[0], 0, screensize[1], 1, -1);
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
	}
	
	private void draw_Quad(int x, int y) {
		// Clear the screen and depth buffer
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT); 
		// set the color of the quad (R,G,B,A)
		GL11.glColor3f(0.5f,0.5f,1.0f);
		// draw quad
		GL11.glBegin(GL11.GL_QUADS);
		GL11.glVertex2f(x,y);
		GL11.glVertex2f(x+200,y);
		GL11.glVertex2f(x+200,y+200);
		GL11.glVertex2f(x,y+200);
		GL11.glEnd();
	}
	
	public void pollInput() {
		// state machine for tracking click state at class level
		while (Mouse.next()) {
			if (Mouse.isButtonDown(0)) {
				mleft_clicked = true;
				mouseX = Mouse.getX(); 
				mouseY = Mouse.getY(); 
				System.out.println("Mouse Coordinates: " + mouseX + "," + mouseY);
			}
			else {
				mleft_clicked = false;
			}
		}
		/*
		while (Keyboard.next()) {
			int key = Keyboard.getEventKey();
			System.out.println("Key pressed: " + key);
		}
		*/
	}

	public static void main(String[] argv) {
		DisplayTest test = new DisplayTest();
		test.launch();
	}
}
