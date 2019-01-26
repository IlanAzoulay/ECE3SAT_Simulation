package engine.render;

//==============================================
//-- QUE FAIT CE FICHIER -----
//	- Afficher a l'ecran les modeles
//C'est ici qu'on definit LIMIT_NEAR et LIMIT_FAR
//==============================================

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import engine.io.Window;
import engine.maths.Matrix4f;
import engine.render.models.CameraMe;
import engine.render.models.ModelEntity;
import engine.render.models.TexturedModel;
//import engine.render.models.Model;
import engine.render.models.UntexturedModel;
import engine.shaders.BasicShader;

public class Renderer {
	
	private BasicShader shader;
	private Window window;
	
	private static final float LIMIT_NEAR = 0.01f, LIMIT_FAR = 100000f;
	//On ne voit que les objets dont la position est entre ces 2 limites
	
	
	private Map<TexturedModel, List<ModelEntity>> entities = new HashMap<>();
	//For Batch rendering
	
	//Constructor
	public Renderer( Window window, BasicShader shader ) {
		this.shader = shader;
		this.window = window;
	}
	
	
	public void create() {
		shader.loadProjectionMatrix( new Matrix4f().projection( 70.0f, (float) window.getWidth() / window.getHeight(), LIMIT_NEAR, LIMIT_FAR ) ); 
		//FOV: 70.0f -> 140 degrees field of view
		//zNear: 0.1f -> on voit rien plus proche que 0.1f
	}
	
	
	//Camera
	public void loadCamera( Camera cam ) {
		shader.loadViewMatrix( cam.getViewMatrix() );
	}
	
	
	//For Batch Rendering
	public void processEntity(ModelEntity entity) {
		TexturedModel model = entity.getModel();
		List<ModelEntity> entities = this.entities.get(model);
		
		if (entities != null) {
			entities.add(entity); //Add if it already exists
		} 
		else {
			//Otherwise, create new list from scratch
			List<ModelEntity> newList = new ArrayList<>();
			newList.add(entity);
			this.entities.put(model, newList);
		}
		
	}
	

	//Taking a model (untextured) as parameter and rendering it
	public void renderModel( UntexturedModel model ) {
		
		//I have no idea what any of this really means, still just following the tutorial
		GL30.glBindVertexArray( model.getVertexArrayID() );
		GL20.glEnableVertexAttribArray(0);
		
		GL11.glDrawElements( GL11.GL_TRIANGLES , model.getVertexCount() , GL11.GL_UNSIGNED_INT, 0 );         
		//Draw model on screen
		
		GL20.glDisableVertexAttribArray(0);
		GL30.glBindVertexArray( 0 );
	}
	
	//Taking a model (Textured) as parameter and rendering it
	public void renderTexturedModel( TexturedModel model ) {
		
		GL30.glBindVertexArray(model.getVertexArrayID());
        GL20.glEnableVertexAttribArray(0);
        GL20.glEnableVertexAttribArray(1);
        
        //Bind texture with model
        GL13.glActiveTexture(GL13.GL_TEXTURE0);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, model.getMaterial().getTextureID());
        
        //Display it onscreen
        GL11.glDrawElements(GL11.GL_TRIANGLES, model.getVertexCount(), GL11.GL_UNSIGNED_INT, 0);
        
        GL20.glDisableVertexAttribArray(0);
        GL20.glDisableVertexAttribArray(1);
        GL30.glBindVertexArray(0);
	}
	
	
	//Taking a model (ModelEntity) as parameter and rendering it
	public void renderModelEntity( ModelEntity entity ) {
		
		GL30.glBindVertexArray(entity.getModel().getVertexArrayID());
        GL20.glEnableVertexAttribArray(0);
        GL20.glEnableVertexAttribArray(1);
        
        shader.loadTransformationMatrix( entity.getTransformationMatrix() );
        shader.useMatrices();
        
        //Bind texture with model
        GL13.glActiveTexture(GL13.GL_TEXTURE0);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, entity.getModel().getMaterial().getTextureID());
        
        //Display it onscreen
        GL11.glDrawElements(GL11.GL_TRIANGLES, entity.getModel().getVertexCount(), GL11.GL_UNSIGNED_INT, 0);
        
        GL20.glDisableVertexAttribArray(0);
        GL20.glDisableVertexAttribArray(1);
        GL30.glBindVertexArray(0);
	}
	
	
	//Batch render
	public void render() {
		for (TexturedModel model : entities.keySet() ) {
			
			//1st thing to do: bind entities
			GL30.glBindVertexArray(model.getVertexArrayID());
	        GL20.glEnableVertexAttribArray(0);
	        GL20.glEnableVertexAttribArray(1);
	        
			List<ModelEntity> list = entities.get(model);
			
			for (ModelEntity entity : list ) {
				
				shader.loadTransformationMatrix( entity.getTransformationMatrix() );
		        shader.useMatrices();
				//Bind texture with model
		        GL13.glActiveTexture(GL13.GL_TEXTURE0);
		        GL11.glBindTexture(GL11.GL_TEXTURE_2D, entity.getModel().getMaterial().getTextureID());
		        //Display it onscreen
		        GL11.glDrawElements(GL11.GL_TRIANGLES, entity.getModel().getVertexCount(), GL11.GL_UNSIGNED_INT, 0);
		        
			}
			//Now, undo everything
			GL20.glDisableVertexAttribArray(0);
	        GL20.glDisableVertexAttribArray(1);
	        GL30.glBindVertexArray(0);
		}
	}
	
}
