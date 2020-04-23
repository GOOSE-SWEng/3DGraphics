import java.util.ArrayList;

import javafx.animation.Animation.Status;
import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Graphics3D extends Application{
	///////////////////// TEST BENCH //////////////////////////
	Stage primaryStage;
	int width = 1280;
	int height = 720;
	static String url = "src/3D_Models/HST-3DS/hst.3ds";
	Graphics3DLayer g3dLayer;
	ArrayList<Model> models = new ArrayList<Model>();
	
	@Override
	public void start(Stage stage) throws Exception {
		System.out.println("Start Running...");
		primaryStage = stage;
		primaryStage.setTitle("3D Test");
		GridPane gp = new GridPane();
		Button rotateModelLeft = new Button("rotateModelLeft");
		Button rotateModelRight = new Button("rotateModelRight");
		Button modelZoomIn = new Button("Zoom in");
		Button modelZoomOut = new Button("Zoom out");
		Button play = new Button("Pause");
		
		rotateModelLeft.setOnMouseClicked(e-> rml());
		rotateModelRight.setOnMouseClicked(e-> rmr());
		modelZoomIn.setOnMouseClicked(e-> mzi());
		modelZoomOut.setOnMouseClicked(e-> mzo());
		play.setOnMouseClicked(e-> pause(play));
		
		//SubScene modelScene;
		
		g3dLayer = new Graphics3DLayer(width, (int) (0.9*height), models);
		g3dLayer.add(url, 640, (int)(0.9*height), 200, 200);
		g3dLayer.models.get(models.size());
		
		///////////////////// TESTING BUTTONS //////////////////////
		gp.add(g3dLayer.get(), 1, 0);
		gp.setColumnSpan(g3dLayer.get(), 2);
		gp.add(rotateModelLeft, 0, 1);
		gp.add(rotateModelRight, 1, 1);
		gp.add(play, 2, 1);
		gp.add(modelZoomIn, 3, 1);
		gp.add(modelZoomOut, 4, 1);
		gp.setAlignment(Pos.CENTER);
		
		gp.setHalignment(modelZoomOut, HPos.CENTER);
		gp.setHalignment(modelZoomIn, HPos.CENTER);
		gp.setHalignment(rotateModelLeft, HPos.CENTER);
		gp.setHalignment(rotateModelRight, HPos.CENTER);
		gp.setHalignment(play, HPos.CENTER);
		gp.setHalignment(g3dLayer.get(), HPos.CENTER);
		
		Scene scene = new Scene(gp, width, height);
		scene.setFill(Color.AQUA);
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	
	public void rml() {
		g3dLayer.models.get(models.size()).rotate(0, 0, -20);
	}
	public void rmr() {
		g3dLayer.models.get(models.size()).rotate(0, 0, 20);
	}
	public void mzi() {
		g3dLayer.models.get(models.size()).scale(1.1, 1.1, 1.1);
	}
	public void mzo() {
		g3dLayer.models.get(models.size()).scale(0.9, 0.9, 0.9);
	}
	public void pause(Button play) {
		if(g3dLayer.models.get(models.size()).timeline.getStatus() == Status.RUNNING) {
			g3dLayer.models.get(models.size()).timeline.pause();
			play.setText("Play");
		}else {
			play.setText("Pause");
			g3dLayer.models.get(models.size()).timeline.play();
		}
	}
	
	
	public static void main(String[] args) {
		System.out.println("App Running...");
		launch();
		System.out.println("App Finished...");
	}
}
