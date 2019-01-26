package engine.io;

//=== LOADER ======================================

//Cette classe permet de charger:
//	- images
//	- modeles 3D (.obj)

//=========================================

import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import org.lwjgl.stb.STBImage;
import org.lwjgl.system.MemoryStack;

import engine.io.obj.ModelData;
import engine.io.obj.OBJLoader;
import engine.render.models.TexturedModel;

public class Loader {
	
	//Using STB to load image from path
	public static Image loadImage(String path) {
		ByteBuffer image;
		int width, height;
		
		//Stack so we can delete image when we want
		try (MemoryStack stack = MemoryStack.stackPush() ){
			IntBuffer  comp = stack.mallocInt(1); //channels in file
			IntBuffer  w = stack.mallocInt(1); //Width
			IntBuffer  h = stack.mallocInt(1); //height
			
			image = STBImage.stbi_load(path, w, h, comp, 4);  
			//4: 4 channels (red, green, blue, alpha)
			
			//Prevent error
			if (image == null) {
				System.err.println("Couldn't load " + path);
			}
			width = w.get();
			height = h.get();
		}
		return new Image(width, height, image);
	}
	
	
	
	
	public static TexturedModel loadModel(String objPath, String texturePath) {
		ModelData data = OBJLoader.loadOBJ(objPath);
		return new TexturedModel( data.getVertices(), data.getTextureCoords(), data.getNormals(), data.getIndices(), texturePath );         
	}
	
	
	

}
