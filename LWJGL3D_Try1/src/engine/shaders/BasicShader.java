package engine.shaders;

import engine.maths.Matrix4f;

public class BasicShader extends Shader {
	
	private static final String VERTEX_FILE = "./src/engine/shaders/basicVertexShader.glsl", 
			FRAGMENT_FILE = "./src/engine/shaders/basicFragmentShader.glsl";
	//Filepath for the shader files
	
private int tvpMatrixLocation;
    
    private Matrix4f transformationMatrix = new Matrix4f().identity();
    private Matrix4f projectionMatrix = new Matrix4f().identity();
    private Matrix4f viewMatrix = new Matrix4f().identity();
    
    //Constructor
    public BasicShader() {
        super(VERTEX_FILE, FRAGMENT_FILE);
    }
 
    @Override
    protected void bindAttributes() {
        super.bindAttribute(0, "position");
        super.bindAttribute(1, "textCoords");
    }

	@Override
	protected void getAllUniforms() {
		tvpMatrixLocation = super.getUniform("tvpMatrix");
	}
	
	public void useMatrices() {
		super.loadMatrixUniform(tvpMatrixLocation, projectionMatrix.mul( viewMatrix.mul( transformationMatrix) ) );
		// .mul() method = matrix multiplication
	}
	
	public void loadTransformationMatrix(Matrix4f matrix) {
		transformationMatrix = matrix;
	}
	
	public void loadProjectionMatrix(Matrix4f matrix) {
		projectionMatrix = matrix;
	}
	
	public void loadViewMatrix( Matrix4f matrix ) {
		viewMatrix = matrix;
	}

}
