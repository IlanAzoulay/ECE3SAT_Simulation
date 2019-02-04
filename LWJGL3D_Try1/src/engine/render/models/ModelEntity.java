package engine.render.models;

import engine.maths.Matrix4f;
import engine.maths.Vector3f;
import engine.render.Transformations;
//import org.joml.Vector3f;



public class ModelEntity {

	private TexturedModel model;
	//private Vector3f position, angle, scale; 
	//State of the model
	
	private Transformations transformation;
	
	//Constructor
	public ModelEntity(TexturedModel model, Vector3f position, Vector3f angle, Vector3f scale) {
		this.model = model;
		transformation = new Transformations(position, angle, scale);
	}
	
	//Constructor
	/*public ModelEntity(TexturedModel model, Vector3f position, Vector3f angle, Vector3f scale) {
		this.model = model;
		this.position = position;
		this.angle = angle;
		this.scale = scale;
	}*/
	
	
	// GETTERS and SETTERS ======================================
	//Except more complicated because each vector is divided in 3
	

	
	//IMPORTANT METHODS --------------------
	public void addPosition(float x, float y, float z) {
		transformation.setTranslation(transformation.getTranslation().add(new Vector3f(x, y, z)));
	}
	
	public void addRotation(float x, float y, float z) {
		transformation.setRotation(transformation.getRotation().add(new Vector3f(x, y, z)));
	}
	
	public void addRotation(Vector3f newRotation ) {
		transformation.setRotation(transformation.getRotation().add( newRotation ));
	}
	
	public void addScale(float x, float y, float z) {
		transformation.setScale(transformation.getScale().add(new Vector3f(x, y, z)));
	}
	
	public void setPosition(Vector3f vector) {
		transformation.setTranslation(vector);
	}
	
	public void setRotation(Vector3f vector) {
		transformation.setRotation(vector);
	}
	
	public void setScale(Vector3f vector) {
		transformation.setScale(vector);
	}
	
	
	
	public Matrix4f getTransformationMatrix() {
		return transformation.getTransformation();
	}
	public TexturedModel getModel() {
		return model;
	}
	public void setModel(TexturedModel model) {
		this.model = model;
	}
	public Vector3f getRotation() {
		return transformation.getRotation();
	}	
	public Vector3f getPosition() {
		return transformation.getTranslation();
	}	
	public Vector3f getScale() {
		return transformation.getScale();
	}
	
	
	
}
