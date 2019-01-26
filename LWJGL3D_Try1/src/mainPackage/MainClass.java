package mainPackage;

import java.util.ArrayList;

//=============================================================

//SIMULATION ECE3SAT ADCS 
//	> par Ilan Azoulay, PFE 2018-2019

//L'objectif de cette simulation est de modeliser les forces auxquelles le cubesat sera soumis
//Ensuite, l'idee serait de communiquer ces situations simulees a une Arduino en temps reel
//Et faire tourner le cubesat selon les instructions renvoyees par l'Arduino
//Et ainsi, verifier si le programme du cubesat sera capable de garder son orientation (la meme face doit pointer vers la Terre en tout temps)

//POUR LES INFORMATIONS COMPLETES, VOIR LE README SUR GITHUB
//> https://github.com/IlanAzoulay/ECE3SAT_Simulation

//================================================================


import org.lwjgl.glfw.GLFW;

import engine.io.Loader;
import engine.io.Window;
import engine.maths.Vector3f;
import engine.render.Camera;
import engine.render.Camera2;
import engine.render.Renderer;
import engine.render.models.ModelEntity;
import engine.render.models.TexturedModel;
import engine.render.models.CameraMe;
import engine.render.models.Model;
import engine.render.models.UntexturedModel;
import engine.shaders.BasicShader;
import myModels.CubeSat;
import myModels.Earth;
import myModels.ModelByMe;


//==== MAINCLASS =================

//Ce fichier est le MAIN, le cerveau central, de toute la simulation
//---- Que fait ce fichier ? -------
//	- Boucle principal
//	- Creer les modeles

//================================


public class MainClass {
	
	static double startTime = System.currentTimeMillis();
	//Garder en memoire le temps de debut du programme
	
	private static final int SizeX = 800, SizeY = 600, FPS = 30;
	//Window constant settings
	
	private static Window window = new Window(SizeX, SizeY, FPS, "Simulation ADCS");
	
	
	private static BasicShader shader = new BasicShader();
	private static Renderer renderer = new Renderer( window, shader );
	//To render models
	
	
	
	
	//==================================================================================
	//          M A I N
	//==================================================================================
	public static void main(String[] args) {
		
		
		//Create window with settings
		//window.setIcon("ESPACE logo.png");
		window.setBackgroundColor(0.0f, 0.0f, 0.0f); //Simple black background, nothin special
		//(Red, Green, Blue)
		window.create(); 
		
		shader.create();
		renderer.create();
		
		
		// INITIALIZE EVENT HANDLING =======================
		
		boolean mouseDown = window.isMouseDown(0);
		
		
		// INITIALIZE ELEMENTS OF THE SIMULATION ==========
		
		//Camera
		Camera camera = new Camera(); //Point of View of the simulation
		
		Earth earth = new Earth(FPS); 
		CubeSat cubeSat = new CubeSat( FPS, earth.getSize() );
		
		renderer.processEntity( earth.getEntity() );
		renderer.processEntity( cubeSat.getEntity() );
		
		
		// PUT EVERYTHING IN PLACE =========================

		//Making sure the camera is getting the right angle on the Cubesat
		cubeSat.update();
		camera.setPosition( cubeSat.getPosition().mul(-1) ); 
		camera.setRotation( new Vector3f(0, 180, 0) ); 
		camera.addPosition( new Vector3f(0, 0, -5*cubeSat.getSize() ));
		
		//Correction en fonction de la vitesse pour que le cube soit bien centre
		camera.addPosition( (33.5f/1000f)*cubeSat.getSpeedOrbit() -1.5f * cubeSat.getSize() , 0, 0);
		//Vitesse = 0 ->   correctionX = -2
		//Vitesse = 1000 -> correctionX = 34
		//On deduit donc la fonction de correction: correctionX = (16/500) * vitesse
		//Avec un petit offset en moins pour pas etre trop centre sur le cubesat, ce qui serait un peu moche
		
		boolean running = true;
		
		//MAIN LOOP ==============================================================================
		while ( running ) {
			
			//Regulating time so it matches the FPS delay time
			if ( window.FPS_regulator() ) {
				
				window.update();
				
				//Mouse events
				if ( (window.isMouseDown(0) || window.isMouseDown(1) ) && !mouseDown ) {
					System.out.println("CAMERA: " + camera.getPositionX() + " , " + camera.getPositionY() + " , " + camera.getPositionZ() );
					System.out.println("CUBE  : " + cubeSat.getPositionX() + " , " + cubeSat.getPositionY() + " , " + cubeSat.getPositionZ() );
					
					window.lockMouse();
					mouseDown = true;
				}
				if ( !(window.isMouseDown(0) || window.isMouseDown(1) ) && mouseDown ) {
					window.unlockMouse();
					mouseDown = false;
				}
				
				
				//== PUT ALL STUFF TO DO HERE ==============================
				
				//Update all elements
				earth.update();
				cubeSat.update();
				
				camera.setRotation(0, 180 - cubeSat.getAngleOrbit() , 0);
				//Rotation that allows the Camera to follow the cube
				
				
				//Speed = 760 -> Correction = 
				
				camera.update(window, cubeSat);
				//Pilot the cube yourself
				
				
				
				
				
				//== END OF STUFF TO DO ======================================
				
				renderer.loadCamera(camera);
				//For the camera
				
				shader.bind(); //Formality
				shader.useMatrices(); //Formality
				renderer.render( ); //render the models
				shader.unbind(); //Formality
				
				window.swapBuffers(); //END - Refresh screen
		
				//END WHILE CONDITION =================================================================
				if ( window.closed() ) { running = false; }
				//Close window if you press Esc or F4
				if ( window.isKeyDown(GLFW.GLFW_KEY_ESCAPE) || window.isKeyDown(GLFW.GLFW_KEY_F4 ) ) { 
					running = false;
					window.setClose();
				}
			}
		}
		//END OF MAIN LOOP ================================================================
	
		//Delete all models
		earth.getModel().remove();
		cubeSat.getModel().remove();
		
		shader.remove();
		
	
	
	}
	//END OF MAIN FUNCTION ==================
	
	
	
	//Return time since spent since specific starting point 
	static public double getTime( double start ) {
		return System.currentTimeMillis() - start;
	}
	

}
