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

import comArduino.SerialTest;
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
import windowBuilder.InputWindow;


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
	public static void main(String[] args) throws Exception{
		
		
		//Create window with settings
		window.setIcon("Logo_ECE3SAT.png");
		window.setBackgroundColor(0.0f, 0.0f, 0.0f); //Simple black background, nothin special
		//(Red, Green, Blue)
		window.create(); 
		
		shader.create();
		renderer.create();
		
		
		//Create the other window (the one to enter the cube's rotation)
		InputWindow inputWindow = new InputWindow();
		
		
		// INITIALIZE EVENT HANDLING =======================
		
		boolean mouseDown = window.isMouseDown(0);
		
		
		// INITIALIZE ELEMENTS OF THE SIMULATION ==========
		
		//Camera
		Camera camera = new Camera(); //Point of View of the simulation
		
		Earth earth = new Earth(FPS); 
		CubeSat cubeSat = new CubeSat( FPS, earth.getSize() );
		
		renderer.processEntity( earth.getEntity() );
		renderer.processEntity( cubeSat.getEntity() );
		
		Vector3f destabilization = new Vector3f();
		
		
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
		SerialTest Serialtest = new SerialTest();
		
		//MAIN LOOP ==============================================================================
		while ( running ) {
			
			//Regulating time so it matches the FPS delay time
			if ( window.FPS_regulator() ) {
				
				window.update();
				
				//Mouse events
				if ( (window.isMouseDown(0) || window.isMouseDown(1) ) && !mouseDown ) {
					//If you want to do something with the mouse, insert it here
					window.lockMouse();
					mouseDown = true;
				}
				if ( !(window.isMouseDown(0) || window.isMouseDown(1) ) && mouseDown ) {
					window.unlockMouse();
					mouseDown = false;
				}
				
				
				//== PUT ALL STUFF TO DO HERE ==============================
				
				
				//See if we're receiving any data from the other window
				if ( inputWindow.SendingData() ) {
					//Alors on recupere la rotation demandee
					destabilization = inputWindow.getRotation().div( FPS )  ;
					//Je divise par le nombre de Frames per Second pour qu'on ait le bon nombre chaque seconde
					//Et non chaque frame, puisque la rotation se fait a chaque frame
				}				
				cubeSat.getEntity().addRotation( destabilization );
	
				//Et on l'applique au cubeSat
				// Attention rotation Y doit etre différent de 0, doit être égal à l'angle par rapport au référentiel terrestre
				
				//METTRE LA COM ARDUINO ICI
				if (inputWindow.getComStatus()) { //Si la com est activee
				
					if((cubeSat.getRotationX() != 90)&&(cubeSat.getRotationY() != 0)&&(cubeSat.getRotationZ()!= 0))
						{
							Serialtest.writeData("<X " + cubeSat.getRotationX() + " 0 0>");
							//Thread.sleep(500);
							Serialtest.writeData("<Y 0 " + cubeSat.getRotationY()+ " 0>");
							//Thread.sleep(500);
							Serialtest.writeData("<Z 0 0 " + cubeSat.getRotationZ() + ">");
							//Thread.sleep(500);
							Serialtest.writeData("<A 1 1 1>");
							//Thread.sleep(1500);
						}
					float X, Y, Z =0;
					
					if(cubeSat.getRotationX()>90)
					{
						//X = cubeSat.getRotationX()-Serialtest.getInputX();
						X= - Serialtest.getInputX();
						System.out.println("floX :" + X);
					}else { 
						//X = cubeSat.getRotationX()+Serialtest.getInputX();
						X=  Serialtest.getInputX();
						}
					
					if(cubeSat.getRotationY()>0)
					{
						//Y = cubeSat.getRotationY()-Serialtest.getInputY();
						Y = - Serialtest.getInputY();
						//System.out.println("floY :" + Y);
					}else { 
						//Y = cubeSat.getRotationY()+Serialtest.getInputY();
						Y =  Serialtest.getInputY();
						}
					
					if(cubeSat.getRotationZ()>0)
					{
						//Z = cubeSat.getRotationZ()-Serialtest.getInputZ();
						Z= - Serialtest.getInputZ();
						//System.out.println("floZ :" + Z);
					}else {
						//Z = cubeSat.getRotationZ()+Serialtest.getInputZ();
						Z=  Serialtest.getInputZ();
						}
					
					//cubeSat.getEntity().setRotation(new Vector3f(X,Y,Z));
					cubeSat.getEntity().addRotation(new Vector3f(X,Y,Z));
				}
				
				//Update all elements
				earth.update();
				cubeSat.update();

				camera.setRotation(0, 180 - cubeSat.getAngleOrbit() , 0);
				//Rotation that allows the Camera to follow the cube
				
				//camera.update(window, cubeSat);
				//Pilot the camera yourself
				
				//== END OF STUFF TO DO ======================================
				
				renderer.loadCamera(camera);
				//For the camera
				
				shader.bind(); //Formality
				shader.useMatrices(); //Formality
				renderer.render( ); //render the models
				shader.unbind(); //Formality
				
				window.swapBuffers(); //END - Refresh screen
		
				//END WHILE CONDITION =================================================================
				if ( window.closed() ) { running = false; inputWindow.close(); }
				//Close window if you press Esc or F4
				if ( window.isKeyDown(GLFW.GLFW_KEY_ESCAPE) || window.isKeyDown(GLFW.GLFW_KEY_F4 ) ) { 
					running = false;
					window.setClose();
					inputWindow.close();
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
