package engine.render.models;

//COPY-PASTED FROM TUTORIAL - I CAN'T EXPLAIN THIS FILE

import org.lwjgl.opengl.GL30;

import engine.maths.Vector3f;
import engine.render.Material;
import org.lwjgl.opengl.GL15;

public class TexturedModel extends Model {

	private int vertexArrayID, vertexBufferID, textureCoordsBufferID, 
	normalsBufferID, indicesBufferID, vertexCount;
	
	private Vector3f size;
    
	private Material material;
	//The texture we're talking about
    
    public TexturedModel(float[] vertices, float[] textureCoords, float[] normals, int[] indices, String file) {
    	vertexArrayID = super.createVertexArray();
    	indicesBufferID = super.bindIndicesBuffer(indices);
        vertexBufferID = super.storeData(0, 3, vertices);
        textureCoordsBufferID = super.storeData(1, 2, textureCoords);
        normalsBufferID = super.storeData(2, 3, normals);
        vertexCount = indices.length;
        GL30.glBindVertexArray(0);
        GL15.glDeleteBuffers(normalsBufferID);
        material = new Material(file);
        
        calculateSize( vertices );
    }
    
    //Calculate the size of the object by finding the min and max on each axis
    //This is important, because OpenGL can only multiply a model's scale, not define it
    //So we have to know what size the model is before resizing it
    public void calculateSize( float[] vertices ) {

    	size = new Vector3f();
    	
    	if (vertices.length > 3){
	    	float minX = vertices[0], minY = vertices[1], minZ = vertices[2],
	    			maxX = vertices[0], maxY = vertices[1], maxZ = vertices[2];
	    	//MOI - will be useful to calculate OBJ size
	    	
	    	for (int i = 3; i < vertices.length ; i += 3) {
	    		//Find X extremum
	    		if ( vertices[i] < minX ) { minX = vertices[i]; }
	    		else if ( vertices[i] > maxX ) { maxX = vertices[i]; }
	    		//Find Y extremum
	    		if ( vertices[i+1] < minY ) { minY = vertices[i+1]; }
	    		else if ( vertices[i+1] > maxY ) { maxY = vertices[i+1]; }
	    		//Find Z-extremum
	    		if ( vertices[i+2] < minZ ) { minZ = vertices[i+2]; }
	    		else if ( vertices[i+2] > maxZ ) { maxZ = vertices[i+2]; }
	    	}
	    	
	    	size.setX( maxX - minX );
	    	size.setY( maxY - minY );
	    	size.setZ( maxZ - minZ );
    	}
    }
    
    //Getter
    public Vector3f getSize() {
    	return size;
    }
    
    
    public void remove() {
    	GL30.glDeleteVertexArrays(vertexArrayID);
    	GL15.glDeleteBuffers(vertexBufferID);
    	GL15.glDeleteBuffers(textureCoordsBufferID);
    	GL15.glDeleteBuffers(indicesBufferID);
    	material.remove();
    }
 
    
    //GETTERS =========================
    public int getVertexArrayID() {
        return vertexArrayID;
    }
 
    public int getVertexCount() {
        return vertexCount;
    }

	public Material getMaterial() {
		return material;
	}
	
}
