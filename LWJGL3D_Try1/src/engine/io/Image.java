package engine.io;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import org.lwjgl.stb.STBImage;
import org.lwjgl.system.MemoryStack;

public class Image {
	
	private ByteBuffer image;
	private int width, height;
	
	//GETTERS =================
	public ByteBuffer getImage() {
		return image;
	}
	public int getWidth() {
		return width;
	}
	public int getHeight() {
		return height;
	}
	

	//Constructor
	Image(int width, int height, ByteBuffer image){
		this.image = image;
		this.height = height;
		this.width = width;
	}
	
	/*
	 * THIS METHOD HAS BEEN MOVED TO engine.io.Loader
	 * 
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
	}*/
	
}
