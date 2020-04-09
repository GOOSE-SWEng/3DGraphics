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
import javafx.scene.transform.Translate;
import javafx.util.Duration;

public class Model {
	SubScene modelScene;
	int width;
	int height;
	Group modelGroup;
	ArrayList<InteractivePoints> points = new ArrayList<InteractivePoints>();
	
	public Model(String url, int width, int height){
		this.width = width;
		this.height = height;
		modelScene = createModel(url);
	}
	
	//Method to create model scene
	public SubScene createModel(String url) {
		//TriangleMesh cylinderHeadMesh = null; //For 3DS models
		//Node[] tdsMesh = null; //For STL models
		
		Camera camera = new PerspectiveCamera();
		
		//FOR 3DS MODELS
		if(url.endsWith(".3ds")) {
			ModelImporter tdsImporter = new TdsModelImporter();
			tdsImporter.read(url);
			Node[] tdsMesh = (Node[]) tdsImporter.getImport();
			tdsImporter.close();
			modelGroup = new Group();
			modelGroup.getChildren().addAll(tdsMesh);
	        addPoints(); //Add clickable points
		}
		//FOR STl MODELS
		else if(url.endsWith(".stl")) {
			StlMeshImporter stlImporter = new StlMeshImporter();
	        stlImporter.read(url);
	        TriangleMesh cylinderHeadMesh = stlImporter.getImport();
	        MeshView cylinderHeadMeshView = new MeshView();
	        cylinderHeadMeshView.setMaterial(new PhongMaterial(Color.GRAY));
	        cylinderHeadMeshView.setMesh(cylinderHeadMesh);
	        stlImporter.close();
			modelGroup = new Group();
			modelGroup.getChildren().addAll(cylinderHeadMeshView);
	        addPoints(); //Add clickable points
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

        Translate pivot = new Translate();
        Rotate yRotate = new Rotate(0, Rotate.Y_AXIS);
        Rotate xRotate = new Rotate(0, Rotate.X_AXIS);
        Rotate zRotate = new Rotate(0, Rotate.Z_AXIS);

		camera.getTransforms().addAll(pivot, yRotate, zRotate, xRotate);
		camera.getTransforms().add(new Translate(-640,-360,-300));
		
		//Setup Animation
        Timeline timeline = new Timeline(
                new KeyFrame(
                        Duration.seconds(0), 
                        new KeyValue(yRotate.angleProperty(), 0)
                        //new KeyValue(zRotate.angleProperty(), 0),
                        //new KeyValue(xRotate.angleProperty(), 0)
                ),
                new KeyFrame(
                        Duration.seconds(15), 
                        new KeyValue(yRotate.angleProperty(), 360)
                        //new KeyValue(zRotate.angleProperty(), 360),
                        //new KeyValue(xRotate.angleProperty(), 360)
                )
        );
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
        
        modelGroup.setOnMouseClicked(e->{
        	PickResult pr = e.getPickResult();
            System.out.println(pr.getIntersectedPoint());
        });

        pivot.setX(modelGroup.getTranslateX());
        pivot.setY(modelGroup.getTranslateY());
        pivot.setZ(modelGroup.getTranslateZ());
		SubScene modelSubScene = new SubScene(modelGroup, width, height, true, SceneAntialiasing.BALANCED);
		modelSubScene.setCamera(camera); //Apply the camera
		return modelSubScene;
	}
	
	public void addPoints() {
		points.add(new InteractivePoints(-160,-170,30));
		points.add(new InteractivePoints(40,200,20));
		points.add(new InteractivePoints(60,20,20));
		points.add(new InteractivePoints(80,20,20));
		points.add(new InteractivePoints(100,20,20));
		points.add(new InteractivePoints(120,20,20));
		points.add(new InteractivePoints(140,20,20));
		points.add(new InteractivePoints(160,20,20));
		points.add(new InteractivePoints(180,20,20));
		points.add(new InteractivePoints(200,200,200));
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
	
	public void rotate(int Xangle, int Yangle, int Zangle) {
		modelScene.getCamera().getTransforms().add(new Rotate(Xangle, Rotate.X_AXIS));
		modelScene.getCamera().getTransforms().add(new Rotate(Yangle, Rotate.Y_AXIS));
		modelScene.getCamera().getTransforms().add(new Rotate(Zangle, Rotate.Z_AXIS));
	}
	
	public void move(int x,int y, int z) {
		modelScene.getCamera().getTransforms().add(new Translate(x,y,z));
	}
	
	public SubScene getModelScene() {
		return modelScene;
	}

	public void setModelScene(SubScene modelScene) {
		this.modelScene = modelScene;
	}
	
	public void clickOnModel() {
        System.out.println();
	}
}
