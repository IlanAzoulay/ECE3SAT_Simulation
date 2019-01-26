package engine.render.models;

import org.lwjgl.glfw.GLFW;

import engine.io.Window;
import engine.maths.Matrix4f;
import engine.maths.Vector3f;
import myModels.ModelByMe;

public class CameraMe {

	
	private Vector3f position;
	//X, Y, Z. Classique
	
	private Vector3f rotation;
	//Angle autour de X, angle autour de Y, angle autour de Z
	//(L'angle autour de Z sera logiquement toujours 0)
	
	
	//Constructor
	public CameraMe() {
		this.position = new Vector3f(0, 0, 0);
		this.rotation = new Vector3f(0, 0, 0);
	}
	
	
	
	public void update( Window window ) {
		
		float movingSpeed = 200;
		float angleSpeed = 5;
		
		// KEYBOARD CONTROLS ==============================
		
		//Rotation
		if ( window.isKeyDown(GLFW.GLFW_KEY_RIGHT ) ) {
			rotation.add( new Vector3f( 0, angleSpeed, 0 ) ) ;
		}
		if ( window.isKeyDown(GLFW.GLFW_KEY_LEFT ) ) {
			rotation.add( new Vector3f( 0, angleSpeed, 0 ) ) ;
		}
		if ( window.isKeyDown(GLFW.GLFW_KEY_UP ) ) {
			rotation.add( new Vector3f( angleSpeed, 0, 0 ) ) ;
		}
		if ( window.isKeyDown(GLFW.GLFW_KEY_DOWN ) ) {
			rotation.add( new Vector3f( -angleSpeed, 0, 0 ) ) ;
		}
		
		//Translation
		if ( window.isKeyDown(GLFW.GLFW_KEY_W ) ) {
			position.add( new Vector3f( 0, 0, movingSpeed ) ) ;
		}
		if ( window.isKeyDown(GLFW.GLFW_KEY_S ) ) {
			position.add( new Vector3f( 0, 0, -movingSpeed ) ) ;
		}
		if ( window.isKeyDown(GLFW.GLFW_KEY_D ) ) {
			position.add( new Vector3f( movingSpeed, 0, 0 ) ) ;
		}
		if ( window.isKeyDown(GLFW.GLFW_KEY_A ) ) {
			position.add( new Vector3f( -movingSpeed, 0, 0 ) ) ;
		}
		
	}
	
	
	
	//When the camera moves, everything else must move the other way
	public Matrix4f getViewMatrix() {
		
		//ThinMatrix
		Matrix4f viewMatrix = new Matrix4f();
		viewMatrix.identity();
		
		viewMatrix.rotate( rotation );
		
		viewMatrix.translate( new Vector3f( - position.getX(), -position.getY(), -position.getZ() ) );
		
		return viewMatrix;
	}
	
	
	
	//GETTERS AND SETTERS
	public Vector3f getPosition() {
		return position;
	}
	public Vector3f getRotation() {
		return rotation;
	}
	
}
