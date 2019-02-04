package myModels;

import engine.io.Loader;
import engine.maths.Vector3f;
import engine.render.models.ModelEntity;
import engine.render.models.TexturedModel;

//== EARTH =======================================

// Dans cette classe, nous allons ecrire les proprietes de la Terre

//================================================

public class Earth extends ModelByMe {
	
	//Proprietes
	private float radius = 6731f ; //in Km (the scale isn't coherent because otherwise size differences would be too much)
	private float angularSpeed = 360.0f / ((float)(24*60*60))  ; // in Degrees / second
	
	private int FPS;
	
	//Constructor
	public Earth( int FPS ) {
		this.FPS = FPS;
		create();
	}
	
	
	public void create() {
		//Create 3D model
		model = Loader.loadModel("Sphere.obj", "Texture_Earth.jpg");
		entity = new ModelEntity( model,
				//Position, rotation, scale
				new Vector3f(0, 0, 0 ), new Vector3f(0, 0, 0), new Vector3f(1, 1, 1)
				); //X = horizontal // Y = vertical // Z = vers la profondeur
		
		Vector3f originalSize = model.getSize(); 
		//Size of the original OBJ model
		
		//Now that we know the original scale, we can put the right scale
		entity.setScale( new Vector3f(
				(radius*2)/originalSize.getX(), 
				(radius*2)/originalSize.getY(), 
				(radius*2)/originalSize.getZ()				
				) );
		
		
	}	



	@Override
	public void update( ) {
		
		
		
		//In the case of the Earth, there's not really much of an update
		//Aside from rotating on itself, of course
		//Don't let Flat Earthers tell you any different
		
			
		entity.addRotation(0, (angularSpeed / ((float)FPS) ) , 0);

		
	}

	//GETTERS and SETTERS ===================================
	@Override
	public void setPosition(float x, float y, float z) {
		entity.setPosition( new Vector3f(x, y, z) );
	}
	public float getSize() {
		return radius;
	}
	public Vector3f getPosition() {
		return entity.getPosition();
	}

	

}
