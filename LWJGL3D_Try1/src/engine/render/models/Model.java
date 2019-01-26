package engine.render.models;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

//=======================================================
//---- MODEL - que fait ce fichier ? ------
	// - Bon la pour l'instant je fais que suivre le tuto
	//Au point ou j'en suis c'est peut etre mieux de meme pas essayer de comprendre. Juste se dire que ca marche

//UPDATE: Tous les commentaires que j'avais mis ne sont plus valables. C'est une question de tuto merdique. Desole.

//=======================================================



public abstract class Model {
	
	
	//ALL OF THE FOLLOWING IS COPY-PASTED BECAUSE THE TUTORIAL SUCKS
	protected int createVertexArray() {
        int vertexArrayID = GL30.glGenVertexArrays();
        GL30.glBindVertexArray(vertexArrayID);
        return vertexArrayID;
    }
     
    protected int storeData(int attributeNumber, int coordSize, float[] data) {
    	FloatBuffer buffer = BufferUtils.createFloatBuffer(data.length);
        buffer.put(data);
        buffer.flip();
        int bufferID = GL15.glGenBuffers();
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, bufferID);
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, buffer, GL15.GL_STATIC_DRAW);
        GL20.glVertexAttribPointer(attributeNumber, coordSize, GL11.GL_FLOAT, false, 0, 0);
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
        return bufferID;
    }
     
    protected int bindIndicesBuffer(int[] indices) {
    	IntBuffer buffer = BufferUtils.createIntBuffer(indices.length);
        buffer.put(indices);
        buffer.flip();
        int indicesBufferID = GL15.glGenBuffers();
        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, indicesBufferID);
        GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, buffer, GL15.GL_STATIC_DRAW);
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
        return indicesBufferID;
    }
	
	/*

	private int vertexArrayID, vertexBufferID, indicesBufferID, vertexCount;
	// Somethings that will help with arrays ?
	//TRANSLATION FR-EN: Vertex/vertices = Sommet/sommets
	
	private float[] sommets;
	//Vertices list
	
	private int[] indices;
	//Indices list. Let me explain:
	// Our models will be a bunch of triangles. For example, a rectangle will be 2 triangles.
	// Indices[] is here to explain the correct order in which the program must draw the triangles in order to get the correct shape
	
	//Constructor
	public Model( float[] sommets, int[] indices ) {
		this.sommets = sommets;
		this.indices = indices;
		vertexCount = indices.length;
	}
	
	
	public void create() {
		
		//Get ready for some shit. Flood of nonsense. Don't even try understanding it.

		FloatBuffer buffer = BufferUtils.createFloatBuffer(sommets.length);    
		buffer.put(sommets); //I think this is meant to insert the parameter into the array buffer
		buffer.flip(); //Toggle between reading and writing
		//Yeah that's weird I know
		
		IntBuffer indicesBuffer = BufferUtils.createIntBuffer( indices.length );
		indicesBuffer.put(indices);
		indicesBuffer.flip(); //GL likes it flipped instead of normal
		//Just repeating
		
		//Bind everything together - just following the tutorial here
		vertexArrayID = GL30.glGenVertexArrays();
		GL30.glBindVertexArray( vertexArrayID );
		
		vertexBufferID = GL15.glGenBuffers();
		GL15.glBindBuffer( GL15.GL_ARRAY_BUFFER ,  vertexBufferID  );
		GL15.glBufferData( GL15.GL_ARRAY_BUFFER , buffer , GL15.GL_STATIC_DRAW );
		
		indicesBufferID = GL15.glGenBuffers();
		GL15.glBindBuffer( GL15.GL_ELEMENT_ARRAY_BUFFER , indicesBufferID );
		GL15.glBufferData( GL15.GL_ELEMENT_ARRAY_BUFFER , indicesBuffer , GL15.GL_STATIC_DRAW );
		
		GL20.glEnableVertexAttribArray( 0 );
		GL20.glVertexAttribPointer(0, 3, GL11.GL_FLOAT , false, 0, 0);
		GL30.glBindVertexArray( 0 );
		GL20.glDisableVertexAttribArray( 0 );
	}
	
	//From what I understand:
	//G30: Array stuff
	//G15: Buffer stuff
	//G20: Attributes
	
	//Method DESTROYS model with FACTS and LOGIC
	public void remove() {
		GL30.glDeleteVertexArrays( vertexArrayID );
		GL15.glDeleteBuffers( vertexBufferID  );
		GL15.glDeleteBuffers( indicesBufferID  );
	}
	
	
//===================================================
//		GETTERS and SETTERS	
//===================================================
	public int getVertexArrayID() {
		return vertexArrayID;
	}
	public void setVertexArrayID(int vertexArrayID) {
		this.vertexArrayID = vertexArrayID;
	}
	public int getVertexBufferID() {
		return vertexBufferID;
	}
	public void setVertexBufferID(int vertexBufferID) {
		this.vertexBufferID = vertexBufferID;
	}
	public int getVertexCount() {
		return vertexCount;
	}
	public void setVertexCount(int vertexCount) {
		this.vertexCount = vertexCount;
	}
	*/
	
	
	
}
