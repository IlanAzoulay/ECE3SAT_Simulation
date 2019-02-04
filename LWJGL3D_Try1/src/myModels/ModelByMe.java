package myModels;

import engine.maths.Vector3f;
import engine.render.models.ModelEntity;
import engine.render.models.TexturedModel;

//== MODEL BY ME ==============================

//Cette classe sert juste a faire en sorte que tous les elements de la simulation aient une meme classe mere
//Comme ca on peut tous les mettre dans un tableau

//=============================================

public abstract class ModelByMe {
	
	//What do all elements of the simulation have in common ?
	
	//A 3D model I guess
	protected TexturedModel model;
	protected ModelEntity entity;
	
	//And a create method
	public abstract void create();
	
	//And an update method
	public abstract void update();

	//Getters ================================
	public ModelEntity getEntity() {
		return entity;
	}
	public TexturedModel getModel() {
		return model;
	}
	
	//abstract Getters and Setters ====================
	public abstract void setPosition(float x, float y, float z);
	
	public abstract float getSize();
	
	public abstract Vector3f getPosition();
	
	
}
