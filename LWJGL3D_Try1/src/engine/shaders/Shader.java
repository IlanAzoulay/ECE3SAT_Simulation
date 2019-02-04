package engine.shaders;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.FloatBuffer;

//import org.joml.Matrix4f;
//import org.joml.Vector3f;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;

import engine.maths.Matrix4f;
import engine.maths.Vector3f;

public abstract class Shader {

	private int vertexShaderID, fragmentShaderID, programID;
	
	private String vertexFile, fragmentFile;
	
	//Constructor
	public Shader( String vertexFile, String fragmentFile ) {
		this.vertexFile = vertexFile;
		this.fragmentFile = fragmentFile;
	}
	
	
	public void create() {
		programID = GL20.glCreateProgram();
		
		
		//For Vertex ------ ( Vertex (EN) = Sommet (FR) )
		vertexShaderID = GL20.glCreateShader( GL20.GL_VERTEX_SHADER );        
		GL20.glShaderSource( vertexShaderID , readFile( vertexFile ) );
		GL20.glCompileShader( vertexShaderID );
		
		//Prevent ID error with shaderID 
		if ( GL20.glGetShaderi( vertexShaderID , GL20.GL_COMPILE_STATUS ) == GL11.GL_FALSE  ) {
			System.err.println("ERROR: Vertex Shader - " + GL20.glGetShaderInfoLog(vertexShaderID) );
		}
		
		//Repeat for Fragment ----
		fragmentShaderID = GL20.glCreateShader( GL20.GL_FRAGMENT_SHADER );        
		GL20.glShaderSource( fragmentShaderID , readFile( fragmentFile ) );
		GL20.glCompileShader( fragmentShaderID );
		
		//Prevent ID error  
		if ( GL20.glGetShaderi( fragmentShaderID , GL20.GL_COMPILE_STATUS ) == GL11.GL_FALSE  ) {
			System.err.println("ERROR: Fragment Shader - " + GL20.glGetShaderInfoLog(fragmentShaderID) );
		}
		
		
		//Now, we wanna attach it all to our program
		GL20.glAttachShader( programID , vertexShaderID );
		GL20.glAttachShader( programID , fragmentShaderID );
		
		GL20.glLinkProgram( programID );
		
		//Preventing errors again, BECAUSE THAT'S WHAT HEROES DO
		if ( GL20.glGetProgrami( programID , GL20.GL_LINK_STATUS ) == GL11.GL_FALSE  ) {
			System.err.println("ERROR: Program Linking - " + GL20.glGetShaderInfoLog( programID ) );
		}
		
		GL20.glValidateProgram( programID );
		
		//The 4th one nobody asked for
		if ( GL20.glGetProgrami( programID , GL20.GL_VALIDATE_STATUS ) == GL11.GL_FALSE  ) {
			System.err.println("ERROR: Program Validation - " + GL20.glGetShaderInfoLog( programID ) );
		}
		
		getAllUniforms();
	}
	
	
	protected abstract void bindAttributes();
	//Takes all attributes from your model and allows shader to access them
	
	protected void bindAttribute(int attribute, String variableName) {
        GL20.glBindAttribLocation(programID, attribute, variableName);
    }
	
	public void bind() {
		GL20.glUseProgram( programID );
	}
	public void unbind() {
    	GL20.glUseProgram(0);
    }
	
	public void remove() {
		GL20.glDetachShader( programID , vertexShaderID );
		GL20.glDetachShader( programID , fragmentShaderID );
		GL20.glDeleteShader( vertexShaderID );
		GL20.glDeleteShader( fragmentShaderID );
		GL20.glDeleteProgram(programID);
	}
	
	
	
	//=== UNIFORM STUFF ================
	
	protected abstract void getAllUniforms();
	//Load all the uniform variable from BasicShader
	
	protected int getUniform( String name ) {
		return GL20.glGetUniformLocation(programID, name);
	}
	protected void loadFloatUniform( int location, float value ) {
		GL20.glUniform1f(location, value); //1 float
	}
	protected void loadIntUniform( int location, int value ) {
		GL20.glUniform1i(location, value); //1 int
	}
	protected void loadVectorUniform(int location, Vector3f value) {
    	GL20.glUniform3f(location, value.getX(), value.getY(), value.getZ());
    }
	protected void loadMatrixUniform(int location, Matrix4f value) {
    	FloatBuffer buffer = BufferUtils.createFloatBuffer( 4*4 ); //4*4 matrix
    	for (int i = 0; i < 4; i++) {
    		for (int j = 0; j < 4; j++) {
    			buffer.put(value.get(i, j));
    		}
    	}
    	buffer.flip();
    	GL20.glUniformMatrix4fv(location, true, buffer);
    }
	
	//== END OF UNIFORM STUFF ====================
	
	
	//All in the title
	private String readFile( String file ) {
		
		BufferedReader reader = null;
		StringBuilder string = new StringBuilder();
		
		try {
			reader = new BufferedReader( new FileReader(file) );
			String line;
			//While there's still lines to be read in the file
			while ( (line = reader.readLine()) != null ) {
				string.append(line).append("\n");
			}
		} catch(IOException e) {
			System.err.println("ERROR: Couldn't find file");
		}
		
		return string.toString();
	}
	
	
	
}
