package engine.render;



//==== TEXTURES =======================


//======================================

import org.lwjgl.opengl.GL11;

import engine.io.Image;
import engine.io.Loader;

public class Material {
	
	private int textureID;
	
	//Constructor
	public Material(String file) {
		textureID = GL11.glGenTextures();
		
		Image texture = Loader.loadImage("res/textures/" + file);
		//Load image/tex from path
		
		GL11.glBindTexture( GL11.GL_TEXTURE_2D , textureID);
		GL11.glTexParameteri( GL11.GL_TEXTURE_2D , GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR );           
		GL11.glTexParameteri( GL11.GL_TEXTURE_2D , GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR ); 
		
		GL11.glTexImage2D( GL11.GL_TEXTURE_2D , 0, GL11.GL_RGBA, texture.getWidth(), texture.getHeight() , 0, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, texture.getImage() );      
	
		GL11.glBindTexture( GL11.GL_TEXTURE_2D , 0);
	}
	
	public void remove() {
		GL11.glDeleteTextures(textureID);
	}

	//Getter
	public int getTextureID() {
		return textureID;
	}
	
}

