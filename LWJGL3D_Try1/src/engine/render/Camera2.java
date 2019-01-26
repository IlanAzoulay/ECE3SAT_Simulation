package engine.render;

import org.lwjgl.glfw.GLFW;

import engine.io.Window;
import engine.maths.Matrix4f;
import engine.maths.Vector3f;
import engine.render.models.ModelEntity;
import myModels.ModelByMe;

public class Camera2 {
	
	private float oldMouseX = 0, oldMouseY = 0,
			newMouseX = 0, newMouseY = 0,
			mouseSensitivity = 0.15f,
			movingSpeed = 100;

	private Vector3f position, rotation;
	
	//3rd person stuff
	private float distance = 2.0f, //distance to target
			angle = 0.0f;
	
	
	//THINMATRIX
	private ModelByMe target;
	private float distanceFromTarget = 50;
	private float angleAroundTarget = 0;
	private float pitch = 20; //Angle up and down
	private float yaw = 0; //Angle left-right
	
	//Constructor - Default
	public Camera2( ModelByMe target ) {
		
		this.target = target;
		position = target.getPosition();
		rotation = new Vector3f(0, 0, 0);
	}
	
	//Constructor overloading
	public Camera2(Vector3f position, Vector3f rotation) {
		this.position = position;
		this.rotation = rotation;
	}
	
	
	
	//ThinMatrix
	public void move( Window window ) {
		
		
		// KEYBOARD CONTROLS ==============================
		if ( window.isKeyDown(GLFW.GLFW_KEY_RIGHT ) ) {
			calculateAngleAroundTarget( 20 ) ;
		}
		if ( window.isKeyDown(GLFW.GLFW_KEY_LEFT ) ) {
			calculateAngleAroundTarget( -20 ) ;
		}
		if ( window.isKeyDown(GLFW.GLFW_KEY_UP ) ) {
			calculatePitch( 20 );
		}
		if ( window.isKeyDown(GLFW.GLFW_KEY_DOWN ) ) {
			calculatePitch( -20 );
		}
		if ( window.isKeyDown(GLFW.GLFW_KEY_W ) ) {
			calculateZoom( movingSpeed );
		}
		if ( window.isKeyDown(GLFW.GLFW_KEY_S ) ) {
			calculateZoom( -movingSpeed );
		}
		if ( window.isKeyDown(GLFW.GLFW_KEY_D ) ) {
			this.addPosition( movingSpeed, 0, 0);
		}
		if ( window.isKeyDown(GLFW.GLFW_KEY_A ) ) {
			this.addPosition(-movingSpeed, 0, 0);
		}
		
		// MOUSE ROTATION =================================
		if ( window.isMouseDown(0)) {
			newMouseX = (float) window.getMouseX();
			newMouseY = (float) window.getMouseY();
			
			float dx = newMouseX - oldMouseX;
			float dy = newMouseY - oldMouseY;
			
			this.addRotation( -dy * mouseSensitivity , -dx * mouseSensitivity , 0);
			//x -> dy ; y -> dx
			//Switched on purpose
			
			oldMouseX = newMouseX;
			oldMouseY = newMouseY;
		}
		
		float horizontalDistance = calculateHorizontalDistance();
		float verticalDistance = calculateVerticalDistance();
		
		calculateCameraPosition( horizontalDistance, verticalDistance );
	
		this.yaw = 180 - ( angleAroundTarget );
	
	}
	
	//1st person update
	public void update(Window window) {
		
		//Keys - mouvement (QWERTY)
		if ( window.isKeyDown(GLFW.GLFW_KEY_D ) ) {
			this.addPosition(movingSpeed, 0, 0); 
		}
		if ( window.isKeyDown(GLFW.GLFW_KEY_A ) ) {
			this.addPosition(-movingSpeed, 0, 0);
		}
		if ( window.isKeyDown(GLFW.GLFW_KEY_W ) ) {
			this.addPosition(0, 0, movingSpeed);
		}
		if ( window.isKeyDown(GLFW.GLFW_KEY_S ) ) {
			this.addPosition(0, 0, -movingSpeed);
		}
		
		//Keys - mouvement
		if ( window.isKeyDown(GLFW.GLFW_KEY_RIGHT ) ) {
			this.addRotation(0, 2, 0); 
			}
		if ( window.isKeyDown(GLFW.GLFW_KEY_LEFT ) ) {
			this.addRotation(0, -2, 0);
		}
		if ( window.isKeyDown(GLFW.GLFW_KEY_UP ) ) {
			this.addRotation(2, 0, 0);
		}
		if ( window.isKeyDown(GLFW.GLFW_KEY_DOWN ) ) {
			this.addRotation(-2, 0, 0);
		}
		
		//Souris - rotation
		if ( window.isMouseDown(0)) {
			newMouseX = (float) window.getMouseX();
			newMouseY = (float) window.getMouseY();
			
			float dx = newMouseX - oldMouseX;
			float dy = newMouseY - oldMouseY;
			
			this.addRotation( -dy * mouseSensitivity , -dx * mouseSensitivity , 0);
			//x -> dy ; y -> dx
			//Switched on purpose
			
			oldMouseX = newMouseX;
			oldMouseY = newMouseY;
		}
	}
	
	
	
	
	//ThinMatrix
	public void calculateZoom( float zoom ) {
		distanceFromTarget -= zoom;
	}
	public void calculatePitch( float pitchChange ) {
		pitch -= pitchChange;
	}
	public void calculateAngleAroundTarget( float angleChange ) {
		angleAroundTarget -= angleChange;
	}
	
	private float calculateHorizontalDistance() {
		return (float) (distanceFromTarget * Math.cos( Math.toRadians(pitch) ));
	}
	private float calculateVerticalDistance() {
		return (float) (distanceFromTarget * Math.sin( Math.toRadians(pitch) ));
	}
	
	public void calculateCameraPosition(float horDist, float verDist){
		
		float offsetX = (float) (horDist * Math.cos(Math.toRadians(angleAroundTarget)));
		float offsetZ = (float) (horDist * Math.sin(Math.toRadians(angleAroundTarget)));
		
		position.setX( target.getPosition().getY() - offsetX );
		position.setY( target.getPosition().getY() + verDist );
		position.setZ( target.getPosition().getY() - offsetZ );
	
	}
	
	
	//I added this method otherwise rotation would always start from one specific point, not the current one
	public void InitializeMovement(Window window, ModelEntity entity) {
		oldMouseX = (float) window.getMouseX();
		oldMouseY = (float) window.getMouseY();
		
		//Norme de la distance entre la camera et le cubesat
		distance = (float) Math.sqrt(  Math.pow( position.getX() - entity.getPosition().getX(), 2  )
				+ Math.pow( position.getY() - entity.getPosition().getY(), 2  )
						+ Math.pow( position.getZ() - entity.getPosition().getZ(), 2  )
				);
		
		//System.out.println( "Distance : " + distance);
		angle = 0;
	}
		
		
		
	
	//When the camera moves, everything else must move the other way
	public Matrix4f getViewMatrix() {
		
		//ThinMatrix
		Matrix4f viewMatrix = new Matrix4f();
		viewMatrix.identity();
		
		viewMatrix.rotate( new Vector3f((float)(Math.toRadians(pitch)), 0, 0) );
		viewMatrix.rotate( new Vector3f(0, (float)(Math.toRadians(yaw)) , 0) );
		
		viewMatrix.translate( new Vector3f( - position.getX(), -position.getY(), -position.getZ() ) );
		
		return viewMatrix;
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
