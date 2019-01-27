package myModels;

//=== CUBESAT ===================

//Dans cette classe, on va recuperer toutes les donnees du CubeSat
//Pour pouvoir controler son etat

//==============================

import engine.io.Loader;
import engine.maths.Vector3f;
import engine.render.models.ModelEntity;

public class CubeSat extends ModelByMe {
	
	private static final float SIZE = 1f; //random size at this point
	//Real size is too small for the simulation, compared to Earth
	
	private float speedOrbit = 7.66f, // ISS: 7.66 Km/s 
		speedAngular = 0f, //Will be set later
		altitude = 4087.73f, // 408 Km (made higher for purely esthetic reasons)
		earthSize,
		angleOrbit = 0f; //Angle around Earth's orbit
	
	  private float mass = 1.330f ; //in Kg

	private int FPS; //Frames per second (useful for controlling speed onscreen)

	
	
	//Constructor
	public CubeSat( int FPS, float earthSize ) {
		this.FPS = FPS;
		this.earthSize = earthSize;
		create();
	}

	@Override
	public void create() {

		//Create 3D model
		model = Loader.loadModel("CubeUV.obj", "CubeTexture.png");
		entity = new ModelEntity( model,
				//Position, rotation, scale
				new Vector3f(0, 0, -1* (earthSize + altitude) ), new Vector3f(90, 0, 0), new Vector3f(1, 1, 1)
				); //X = horizontal // Y = vertical // Z = vers la profondeur
		
		Vector3f originalSize = model.getSize(); 
		//Size of the original OBJ model
		
		//Now that we know the original scale, we can put the right scale
		entity.setScale( new Vector3f(
				( SIZE )/originalSize.getX(), 
				( SIZE )/originalSize.getY(), 
				( SIZE )/originalSize.getZ()				
				) );
		
	}

	@Override
	public void update() {
		
		entity.setPosition( trajectory( altitude + earthSize ) );
		//Move the Cube around the Earth

	}
	
	
	//Trajectoire circulaire autour de la terre
	public Vector3f trajectory( float orbitRadius ) {
	//GARDER LES COMMENTAIRES. C'EST TOUT LE RAISONNEMENT. J'ai juste deplace les operations.
		
		//Que je me souvienne de la fonction d'un cercle...
		// (x - centreX)^2 + (y - centreY)^2 = R^2 
		//Pas une bonne idee en fait ?
		
		//En passant par la vitesse angulaire et pythagore ?
		
		float orbitPerimeter = (float) (Math.PI * 2 * orbitRadius);
		//Distance totale que le cube fait en un tour complet
		
		float orbitTime = orbitPerimeter / speedOrbit;
		//Temps que le cube prend pour faire un tour complet
		
		//Avec cette information, je devrais etre capable de calculer l'angle parcourue en 1 frame
		angleOrbit += (float) Math.toRadians( 360 / ( orbitTime * FPS ) );
		
		//Connaissant donc l'angle parcouru, et le rayon, on devrait pouvoir calculer les nouveaux x et y
		//entity.setPosition(  new Vector3f( (float)(orbitRadius * Math.cos(angle)) , 0, (float)(orbitRadius * Math.sin(angle)) ) );
		//L'orbite se fait autour de l'axe Y, donc on travaille sur X et Z
		
		float X = (float)(orbitRadius * Math.sin(angleOrbit));
		float Z = (float)(orbitRadius * Math.cos(angleOrbit));
		
		//TRIGONOMETRIE BIAAATCH
		return new Vector3f( X, 0, Z );
	}

	
	//GETTERS and SETTERS ==========================
	@Override
	public void setPosition(float x, float y, float z) {
		entity.setPosition( new Vector3f(x, y, z) );
	}
	public float getPositionX() {
		return entity.getPosition().getX();
	}
	public float getPositionY() {
		return entity.getPosition().getY();
	}
	public float getPositionZ() {
		return entity.getPosition().getZ();
	}
	public float getRotationX() {
		return entity.getRotation().getX();
	}
	public float getRotationY() {
		return entity.getRotation().getY();
	}
	public float getRotationZ() {
		return entity.getRotation().getZ();
	}
	public float getAltitude() {
		return altitude;
	}
	public float getSize() {
		return SIZE;
	}
	public Vector3f getPosition() {
		return entity.getPosition();
	}
	public float getAngleOrbit() {
		return (float) Math.toDegrees(angleOrbit);
	}
	public float getSpeedOrbit() {
		return speedOrbit;
	}
	


	
}
