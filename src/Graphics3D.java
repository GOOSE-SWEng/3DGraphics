import com.interactivemesh.jfx.importer.ModelImporter;
import com.interactivemesh.jfx.importer.stl.StlMeshImporter;
import com.interactivemesh.jfx.importer.tds.TdsModelImporter;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Camera;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.SceneAntialiasing;
import javafx.scene.SubScene;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.MeshView;
import javafx.scene.shape.TriangleMesh;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Graphics3D extends Application{
	Stage primaryStage;
	int width = 1280;
	int height = 720;
	String url = "src/3D_Models/HST-3DS/hst.3ds";
	
	public Graphics3D(){
		createModel(url);
		
		
	}
	
	@Override
	public void start(Stage stage) throws Exception {
		primaryStage = stage;
		primaryStage.setTitle("3D Test");
		BorderPane bp = new BorderPane();
		SubScene modelScene = createModel(url);
		bp.setCenter(modelScene);
		Scene scene = new Scene(bp, width, height);
		
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	
	public SubScene createModel(String url) {
		TriangleMesh cylinderHeadMesh = null;
		Node[] tdsMesh = null;
		if(url.endsWith(".3ds")) {
			ModelImporter tdsImporter = new TdsModelImporter();
			tdsImporter.read(url);
			tdsMesh = (Node[]) tdsImporter.getImport();
			tdsImporter.close();
		}
		else if(url.endsWith(".stl")) {
			StlMeshImporter stlImporter = new StlMeshImporter();
	        stlImporter.read(url);
	        cylinderHeadMesh = stlImporter.getImport();
	        MeshView cylinderHeadMeshView = new MeshView();
	        cylinderHeadMeshView.setMaterial(new PhongMaterial(Color.GRAY));
	        cylinderHeadMeshView.setMesh(cylinderHeadMesh);
	        stlImporter.close();
		}
        // Create Shape3D
		System.out.println("Model Imported");
		
		Camera camera = new PerspectiveCamera();
		Group modelGroup = new Group();
		modelGroup.getChildren().addAll(tdsMesh);

        modelGroup.getTransforms().add(new Rotate(90, Rotate.X_AXIS));
		
        Translate pivot = new Translate();
        Rotate yRotate = new Rotate(0, Rotate.Y_AXIS);
        Rotate xRotate = new Rotate(0, Rotate.X_AXIS);
        Rotate zRotate = new Rotate(0, Rotate.Z_AXIS);

		camera.getTransforms().addAll(pivot, yRotate, zRotate, xRotate);
		camera.getTransforms().add(new Translate(-640,-360,-300));
		
        Timeline timeline = new Timeline(
                new KeyFrame(
                        Duration.seconds(0), 
                        new KeyValue(yRotate.angleProperty(), 0),
                        new KeyValue(zRotate.angleProperty(), 0),
                        new KeyValue(xRotate.angleProperty(), 0)
                ),
                new KeyFrame(
                        Duration.seconds(15), 
                        new KeyValue(yRotate.angleProperty(), 360),
                        new KeyValue(zRotate.angleProperty(), 360),
                        new KeyValue(xRotate.angleProperty(), 360)
                )
        );
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
        
        pivot.setX(modelGroup.getTranslateX());
        pivot.setY(modelGroup.getTranslateY());
        pivot.setZ(modelGroup.getTranslateZ());
        
		SubScene modelSubScene = new SubScene(modelGroup, width, height, true, SceneAntialiasing.BALANCED);
		modelSubScene.setCamera(camera);
		return modelSubScene;
	}
	
	public void rotate(int Xangle, int Yangle, int Zangle, SubScene modelScene) {
		modelScene.getCamera().getTransforms().add(new Rotate(Xangle, Rotate.X_AXIS));
		modelScene.getCamera().getTransforms().add(new Rotate(Yangle, Rotate.Y_AXIS));
		modelScene.getCamera().getTransforms().add(new Rotate(Zangle, Rotate.Z_AXIS));
	}
	
	public void move(int x,int y, int z, SubScene modelScene) {
		modelScene.getCamera().getTransforms().add(new Translate(x,y,z));
	}
	
	public static void main(String[] args) {
		new Graphics3D();
		System.out.println("App Running...");
		launch();
		System.out.println("App Finished...");
	}

}
