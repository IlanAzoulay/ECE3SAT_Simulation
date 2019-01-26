package engine.io.obj;

//This class will hold all the information for the texture model
// plus information that will help for optimization

public class ModelData {
 
    private float[] vertices;
    private float[] textureCoords;
    private float[] normals;
    private int[] indices;
    private float furthestPoint;
 
    //Constructor
    public ModelData(float[] vertices, float[] textureCoords, float[] normals, int[] indices,
            float furthestPoint) {
        this.vertices = vertices;
        this.textureCoords = textureCoords;
        this.normals = normals;
        this.indices = indices;
        this.furthestPoint = furthestPoint;
    }
 
    
    //GETTERS =================================
    public float[] getVertices() {
        return vertices;
    }
    public float[] getTextureCoords() {
        return textureCoords;
    }
    public float[] getNormals() {
        return normals;
    }
    public int[] getIndices() {
        return indices;
    }
    public float getFurthestPoint() {
        return furthestPoint;
    }
}