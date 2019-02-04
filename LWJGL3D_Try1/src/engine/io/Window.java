package engine.io;

import engine.maths.Vector3f;

import java.nio.DoubleBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWImage;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;

//===== WHAT DOES THIS FILE DO ? ====================

//	- Define Window object, with all settings and everything, for the window we'll use to display everything
	//	> Size, position, resizability, FPS, etc.
//	- Basic event handling
//	- Basic time handling

//===================================================

public class Window {

	//Class variables
	private int width, height;
	private String title;
	private double delayFPS; //Delay between frames (aka frequency, aka 1/FPS)
	private double windowTime; 
	private long window;
	
	private GLFWImage cursorBuffer;
	private GLFWImage.Buffer iconBuffer;
	
	private Vector3f backgroundColor; //Vector3f: includes 3 float numbers (RGB)
	
	
	//Constructor
	public Window(int width, int height, int FPS, String title) {
		this.width = width;
		this.height = height;
		this.title = title;
		this.delayFPS = 1.0/FPS;
		backgroundColor = new Vector3f( 0.0f , 0.0f , 0.0f ); //Initialize background color (black)
	
		cursorBuffer = null;
		iconBuffer = null;
		
	}
	
	//=================================================================
	//    WINDOW SETTINGS
	//=================================================================
	
	//This is where we'll create our window
	public void create() {
		
		//Blindage en cas d'erreur d'initialisation
		if ( !GLFW.glfwInit() ) {
			System.err.println("Error: Couldn't initialize GLFW");
			System.exit(-1);
		}
		
		// SETTINGS (most of this is formalities that just need to be there for everything to work)
		GLFW.glfwWindowHint( GLFW.GLFW_VISIBLE, GLFW.GLFW_FALSE ); //This one is rather a formality, barely useful
		GLFW.glfwWindowHint( GLFW.GLFW_RESIZABLE, GLFW.GLFW_FALSE ); //This will make our window NOT resizable

		
		//For anti-aliasing
		GLFW.glfwWindowHint(GLFW.GLFW_SAMPLES, 4);
		//GLFW.glEnable(  ); //GL_MULTISAMPLE
		
		//And now we're creating the window for real
		window = GLFW.glfwCreateWindow(width, height, title, 0, 0);            
		
		//Re-blindage
		if ( window == 0 ) {
			System.err.println("Error: Window couldn't be created");
			System.exit(-1);
		}
		
		GLFWVidMode videoMode = GLFW.glfwGetVideoMode( GLFW.glfwGetPrimaryMonitor() );
		//VideoMode is your computer screen
		
		GLFW.glfwSetWindowPos( window, (videoMode.width() - width)/2 , (videoMode.height() - height)/2 );
		//La fenetre est ainsi centree sur l'ecran peut importe sa dimension
		
		
		//Some more settings / formalities
		GLFW.glfwMakeContextCurrent(window); //Makes this the main window to render
		GL.createCapabilities(); //Formality ?
		
		GL11.glEnable( GL11.GL_DEPTH_TEST );
		
		if (cursorBuffer != null) {
			long cursor = GLFW.glfwCreateCursor(cursorBuffer, 0, 0);
			GLFW.glfwSetCursor(window, cursor);
		}
		if (iconBuffer != null) {
			GLFW.glfwSetWindowIcon(window, iconBuffer);
		}
	
		//Display the window onscreen
		GLFW.glfwShowWindow( window );
		
		//Window chronometer
		windowTime = System.currentTimeMillis();
	}
	
	
	
	
	
	public void update() {
		
		//Background
		GL11.glClearColor( backgroundColor.getX() , backgroundColor.getY() , backgroundColor.getZ() , 1.0f );
		GL11.glClear( GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
		
		
		GLFW.glfwPollEvents();
		//Allows events to actually happen (example: closing the window when you click close)
		
	}
	
	//Refresh screen
	public void swapBuffers() {
		GLFW.glfwSwapBuffers(window);
	}
	
	
	
	//FPS regulator
	//This function is meant to regulate the amount of times between each update
		//> returns TRUE if we're good to go for another update
		//> returns FALSE if we're still waiting
	public boolean FPS_regulator() {
		
		//If we've finished waiting for the delay
		if ( System.currentTimeMillis() - windowTime >= delayFPS * 1000 ) { // the "*1000" is important, so we're all in milliseconds
			windowTime = System.currentTimeMillis(); //Reinitialize time from which we're counting
			return true;
		}
		//If we reach this point, that means it's gonna be false
		return false;
	}
	
	
	//Check if the window should be closed (i.e when you click close)
	public boolean closed() {
		return GLFW.glfwWindowShouldClose(window);
	}
	
	//Force close window
	public void setClose() {
		GLFW.glfwSetWindowShouldClose(window, true);
		//If that's not enough, uncomment this
		//GLFW.glfwTerminate();
	}
	
	
	//======================================================
	// 		GRAPHICS STUFF
	//=======================================================
	
	public void setBackgroundColor( float R, float G, float B ) {
		backgroundColor = new Vector3f(R, G, B);
	}
	
	
	
	//======================================================
	// 		EVENT HANDLING
	//=======================================================
	
	//Verifier si une touche du clavier a ete enfoncee
	public boolean isKeyDown(int keyCode) {
		return (GLFW.glfwGetKey( window, keyCode) == 1);
	}
	
	
	//Verifier si le bouton de la souris a ete appuye
	public boolean isMouseDown( int mouseButton ) {
		return (GLFW.glfwGetMouseButton(window, mouseButton) == 1);
	}
	
	public double getMouseX() {
		DoubleBuffer buffer = BufferUtils.createDoubleBuffer(1);
		GLFW.glfwGetCursorPos(window, buffer, null);
		return buffer.get(0);
	}
	
	public double getMouseY() {
		DoubleBuffer buffer = BufferUtils.createDoubleBuffer(1);
		GLFW.glfwGetCursorPos(window, null, buffer);
		return buffer.get(0);
	}
	
	//Icon and cursor ===================
	
	public void setIcon(String path) {
		Image icon = Loader.loadImage("res/textures/" + path);
		GLFWImage iconImage = GLFWImage.malloc();
		iconBuffer = GLFWImage.malloc(1);
		iconImage.set( icon.getWidth(), icon.getHeight(), icon.getImage() );
		iconBuffer.put( 0 , iconImage );
	}
	
	public void setCursor(String path) {
		Image cursor = Loader.loadImage("res/textures/" + path);
		cursorBuffer = GLFWImage.malloc();
		cursorBuffer.set( cursor.getWidth(), cursor.getHeight(), cursor.getImage() );
	}
	
	
	
	//GETTERS ====================================
	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}
	
	
	
	//Miscellaneous
	public void lockMouse() {
		GLFW.glfwSetInputMode(window, GLFW.GLFW_CURSOR, GLFW.GLFW_CURSOR_DISABLED);
	}
	public void unlockMouse() {
		GLFW.glfwSetInputMode(window, GLFW.GLFW_CURSOR, GLFW.GLFW_CURSOR_NORMAL);
	}
	
	
}
