package engine.render;

import org.lwjgl.glfw.GLFW;

import engine.io.Window;
import engine.maths.Matrix4f;
import engine.maths.Vector3f;
import engine.render.models.ModelEntity;
import myModels.ModelByMe;

public class Camera {
	
	private float oldMouseX = 0, oldMouseY = 0,
			newMouseX = 0, newMouseY = 0,
			mouseSensitivity = 0.15f;

	private Vector3f position, rotation;	

	
	//Constructor - Default
	public Camera(  ) {
		position = new Vector3f(0, 0, 0);
		rotation = new Vector3f(0, 0, 0);
	}
	
	//Constructor overloading
	public Camera(Vector3f position, Vector3f rotation) {
		this.position = position;
		this.rotation = rotation;
	}
	
	
	//When the camera moves, everything else must move the other way
	public Matrix4f getViewMatrix() {
		
		Matrix4f rotateX = new Matrix4f().rotateAround( rotation.getX(), new Vector3f(1, 0, 0) );                 
		Matrix4f rotateY = new Matrix4f().rotateAround( rotation.getY(), new Vector3f(0, 1, 0) );
		Matrix4f rotateZ = new Matrix4f().rotateAround( rotation.getZ(), new Vector3f(0, 0, 1) );
		
		Matrix4f rotation = rotateX.mul( rotateZ.mul(rotateY) );
	
		Vector3f negativePosition = position.mul(-1);
		Matrix4f translation = new Matrix4f().translate(negativePosition);  
		
		return translation.mul(rotation);
	}
	
	
	
	//1st person update
	public void update(Window window, ModelByMe cubeSat) {
		
		float movingSpeed = cubeSat.getSize() ;//* 0.1f;
		
		//Keys - mouvement (QWERTY)
		if ( window.isKeyDown(GLFW.GLFW_KEY_RIGHT ) ) {
			this.addPosition(movingSpeed, 0, 0); 
			}
		if ( window.isKeyDown(GLFW.GLFW_KEY_LEFT ) ) {
			this.addPosition(-movingSpeed, 0, 0);
		}
		//Zoom in
		if ( window.isKeyDown(GLFW.GLFW_KEY_UP ) && - (position.getZ() + cubeSat.getPosition().getZ()) >= 2*cubeSat.getSize() ) {
			this.addPosition(0, 0, movingSpeed);
		}
		//Zoom out
		if ( window.isKeyDown(GLFW.GLFW_KEY_DOWN ) && - (position.getZ() + cubeSat.getPosition().getZ()) < 10*cubeSat.getSize() ) {
			this.addPosition(0, 0, -movingSpeed);
		}
		//Les conditions en Zoom sont une addition car la position de la camera est negative de celle du reste du monde
		
	}
	
	
	
	
	//MOVE THE CAMERA =========================
	public void addPosition( Vector3f value ) {
		position = position.add(value);
	}
	public void addPosition( float x, float y, float z) {
		position = position.add( new Vector3f(x, y, z) );
	}
	public void addRotation( Vector3f value ) {
		rotation = rotation.add(value);
	}
	public void addRotation( float x, float y, float z) {
		rotation = rotation.add( new Vector3f(x, y, z) );
	}
	
	public void setPosition( Vector3f value ) {
		position = value;
	}
	public void setPosition( float x, float y, float z) {
		position =  new Vector3f(x, y, z) ;
	}
	public void setRotation( Vector3f value ) {
		rotation = value;
	}
	public void setRotation( float x, float y, float z) {
		rotation =  new Vector3f(x, y, z) ;
	}
	
	
	//GETTERS
	public float getPositionX() {
		return position.getX();
	}
	public float getPositionY() {
		return position.getY();
	}
	public float getPositionZ() {
		return position.getZ();
	}
	public float getRotationX() {
		return rotation.getX();
	}
	public float getRotationY() {
		return rotation.getY();
	}
	public float getRotationZ() {
		return rotation.getZ();
	}
	
}
