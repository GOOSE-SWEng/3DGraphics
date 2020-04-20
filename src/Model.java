import java.util.ArrayList;

import com.interactivemesh.jfx.importer.ModelImporter;
import com.interactivemesh.jfx.importer.stl.StlMeshImporter;
import com.interactivemesh.jfx.importer.tds.TdsModelImporter;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.Camera;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.PerspectiveCamera;
import javafx.scene.SceneAntialiasing;
import javafx.scene.SubScene;
import javafx.scene.input.PickResult;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.MeshView;
import javafx.scene.shape.Sphere;
import javafx.scene.shape.TriangleMesh;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Scale;
import javafx.scene.transform.Translate;
import javafx.util.Duration;

public class Model {
	
	/*
	 * 3D Model from interactive mesh - InteractiveMesh.org
	 * @author - Tom Pound
	 * @date - 9/4/20
	 * @Version - 1.2
	 */
	
	SubScene modelScene; //Scene of the model
	int width; //Width of SubScene
	int height; //Height of SubScene
	int paneWidth;
	int paneHeight;
	int xStart;
	int yStart;
	Group modelGroup; //Group containing all 3D Elements
	Camera camera;
	ArrayList<InteractivePoints> points = new ArrayList<InteractivePoints>(); //Arraylist of interactive points
	ArrayList<Sphere> spheres = new ArrayList<Sphere>(); //Arraylist of Sphere buttons
	
	public Model(String url, int modelWidth, int modelHeight, int paneWidth, int paneHeight, int xStart, int yStart){
		this.width = modelWidth; //Width of SubScene
		this.height = modelHeight; //Height of SubScene
		this.paneWidth = paneWidth;
		this.paneHeight = paneHeight;
		this.xStart = xStart;
		this.yStart = yStart;
		modelScene = createModel(url); //Create the and store scene
	}
	
	//Method to create model scene
	public SubScene createModel(String url) {
		camera = new PerspectiveCamera(); //new camera for subscene
		
		//FOR 3DS MODELS
		if(url.endsWith(".3ds")) {
			ModelImporter tdsImporter = new TdsModelImporter(); //3DS importer
			tdsImporter.read(url);
			Node[] tdsMesh = (Node[]) tdsImporter.getImport(); //Store in a node array
			tdsImporter.close();
			modelGroup = new Group();
			modelGroup.getChildren().addAll(tdsMesh);
	        addPoints(); //Add clickable points
		}
		//FOR STL MODELS
		else if(url.endsWith(".stl")) {
			StlMeshImporter stlImporter = new StlMeshImporter(); //STL importer
	        stlImporter.read(url);
	        TriangleMesh cylinderHeadMesh = stlImporter.getImport(); //Store in a mesh
	        MeshView cylinderHeadMeshView = new MeshView(); //Creates new Mesh view
	        cylinderHeadMeshView.setMaterial(new PhongMaterial(Color.GRAY)); //Sets material of model
	        cylinderHeadMeshView.setMesh(cylinderHeadMesh); //Sets the mesh for the mesh view
	        stlImporter.close();
			modelGroup = new Group();
			modelGroup.getChildren().addAll(cylinderHeadMeshView);
	        //addPoints(); //Add clickable points
		}
		else if(url.endsWith(".obj")) {
			System.out.print("The OBJ file type is not supported right now.");
		}
		else if(url.endsWith(".X3D")) {
			System.out.print("The X3D file type is not supported right now.");
		}
		else {
			System.out.println("Unable to open: " + url);
		}
		
        // Create Shape3D
		System.out.println("Model Imported");
		
		modelGroup.getTransforms().add(new Rotate(90, Rotate.X_AXIS));

        Translate pivot = new Translate(); //Create pivot
        //Setup rotates
        Rotate yRotate = new Rotate(0, Rotate.Y_AXIS);
        Rotate xRotate = new Rotate(0, Rotate.X_AXIS);
        Rotate zRotate = new Rotate(0, Rotate.Z_AXIS);
		camera.getTransforms().addAll(pivot, yRotate, zRotate, xRotate);
		camera.getTransforms().add(new Translate(-640,-360,-300));
		
		//Setup Animation
        Timeline timeline = new Timeline(
                new KeyFrame(
                        Duration.seconds(0), 
                        new KeyValue(yRotate.angleProperty(), 0) //Start with angle of 0
                ),
                new KeyFrame(
                        Duration.seconds(15), 
                        new KeyValue(yRotate.angleProperty(), 360) //Finish with angle of 360
                )
        );
        timeline.setCycleCount(Timeline.INDEFINITE); //Loop animation
        //timeline.play(); //Run animation
        
        //Return 3D mouse click point
        modelGroup.setOnMouseClicked(e->{
        	PickResult pr = e.getPickResult();
            System.out.println(pr.getIntersectedPoint());
        });
        
        //Pause when mouse is on model
        modelGroup.setOnMouseEntered(e->timeline.pause());
        //Play when mouse is off model
        modelGroup.setOnMouseExited(e->timeline.play());
        
        modelGroup.setOnScroll(e->{
        	if(e.getDeltaY() > 0) {
        		scale(1.1,1.1,1.1);
        	}else{
        		scale(0.9,0.9,0.9);
        	}
        });

        //Set pivot points
        pivot.setX(modelGroup.getTranslateX());
        pivot.setY(modelGroup.getTranslateY());
        pivot.setZ(modelGroup.getTranslateZ());
        
        //Create subscene
		SubScene modelSubScene = new SubScene(modelGroup, width, height, true, SceneAntialiasing.BALANCED);
		modelSubScene.setCamera(camera); //Apply the camera
		return modelSubScene;
	}
	
	
	public void addPoints() {
		for(int i=0;i<100;i++) {
			
		}
		for(int i=0;i<100;i++) {
			
		}
		points.add(new InteractivePoints(-160,-170,30));
		points.add(new InteractivePoints(-40,200,100));
		points.add(new InteractivePoints(60,-160,50));
		points.add(new InteractivePoints(180,-100,90));
		PhongMaterial mat = new PhongMaterial();
		mat.setDiffuseColor(Color.rgb(180, 180, 0, 0.75));
		Sphere point1 = new Sphere();
		Sphere point2 = new Sphere();
		Sphere point3 = new Sphere();
		Sphere point4 = new Sphere();
		point1.setMaterial(mat);
		point2.setMaterial(mat);
		point3.setMaterial(mat);
		point4.setMaterial(mat);
		
		point1.setRadius(40);
		point1.getTransforms().add(new Translate(points.get(0).getX(),points.get(0).getY(),points.get(0).getZ()));
		
		point2.setRadius(40);
		point2.getTransforms().add(new Translate(points.get(1).getX(),points.get(1).getY(),points.get(1).getZ()));

		point3.setRadius(40);
		point3.getTransforms().add(new Translate(points.get(2).getX(),points.get(2).getY(),points.get(2).getZ()));

		point4.setRadius(40);
		point4.getTransforms().add(new Translate(points.get(3).getX(),points.get(3).getY(),points.get(3).getZ()));
		modelGroup.getChildren().addAll(point1, point2,point3, point4);
	}
	
	
	
	
	public 
	
	//Rotate Camera function
	public void rotateCam(int Xangle, int Yangle, int Zangle) {
		modelScene.getCamera().getTransforms().add(new Rotate(Xangle, Rotate.X_AXIS));
		modelScene.getCamera().getTransforms().add(new Rotate(Yangle, Rotate.Y_AXIS));
		modelScene.getCamera().getTransforms().add(new Rotate(Zangle, Rotate.Z_AXIS));
	}
	//Move Camera function
	public void moveCam(int x,int y, int z) {
		modelScene.getCamera().getTransforms().add(new Translate(x,y,z));
	}
	
	//Rotate function
	public void rotate(int Xangle, int Yangle, int Zangle) {
		modelGroup.getTransforms().add(new Rotate(Xangle, Rotate.X_AXIS));
		modelGroup.getTransforms().add(new Rotate(Yangle, Rotate.Y_AXIS));
		modelGroup.getTransforms().add(new Rotate(Zangle, Rotate.Z_AXIS));
	}
	//Move function
	public void move(int x,int y, int z) {
		modelGroup.getTransforms().add(new Translate(x,y,z));
	}
	
	//Scale function
	public void scale(double x,double y, double z) {
		modelGroup.getTransforms().add(new Scale(x,y,z));
	}
	
	public SubScene getModelScene() {
		return modelScene;
	}

	public void setModelScene(SubScene modelScene) {
		this.modelScene = modelScene;
	}
	
	public int getxStart() {
		return xStart;
	}

	public void setxStart(int xStart) {
		this.xStart = xStart;
	}

	public int getyStart() {
		return yStart;
	}

	public void setyStart(int yStart) {
		this.yStart = yStart;
	}

	public void clickOnModel() {
        System.out.println();
	}
}
