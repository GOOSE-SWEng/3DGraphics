import java.util.ArrayList;

import javafx.animation.Animation.Status;
import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class Graphics3D extends Application{
	///////////////////// TEST BENCH //////////////////////////
	Stage primaryStage;
	int width = 1280;
	int height = 720;
	int slideNo = 0;
	static String url = "src/3D_Models/HST-3DS/hst.3ds";
	Graphics3DLayer g3dLayer; //Would be arraylist is actual program but only one layer is required for this test
	ArrayList<Model> models = new ArrayList<Model>();
	
	@Override
	public void start(Stage stage) throws Exception {
		System.out.println("Start Running...");
		primaryStage = stage;
		primaryStage.setTitle("3D Test");
		GridPane gp = new GridPane();
		//Create the buttons for interacting with the model
		Button rotateModelLeft = new Button("rotateModelLeft");
		Button rotateModelRight = new Button("rotateModelRight");
		Button modelZoomIn = new Button("Zoom in");
		Button modelZoomOut = new Button("Zoom out");
		Button play = new Button("Pause");
		
		//Setup the actions from the buttons
		rotateModelLeft.setOnMouseClicked(e-> rml());
		rotateModelRight.setOnMouseClicked(e-> rmr());
		modelZoomIn.setOnMouseClicked(e-> mzi());
		modelZoomOut.setOnMouseClicked(e-> mzo());
		play.setOnMouseClicked(e-> pause(play));
		
		//Create new Layer for this slide
		g3dLayer = new Graphics3DLayer(width, (int) (0.9*height), models);
		//Add new model to this layer
		g3dLayer.add(url, 640, (int)(0.9*height), 200, 200);
		
		///////////////////// TESTING BUTTONS //////////////////////
		gp.add(g3dLayer.get(), 1, 0);
		gp.setColumnSpan(g3dLayer.get(), 2);
		gp.add(rotateModelLeft, 0, 1);
		gp.add(rotateModelRight, 1, 1);
		gp.add(play, 2, 1);
		gp.add(modelZoomIn, 3, 1);
		gp.add(modelZoomOut, 4, 1);
		gp.setAlignment(Pos.CENTER);
		
		//Centralise all elements in the gridpane
		gp.setHalignment(modelZoomOut, HPos.CENTER);
		gp.setHalignment(modelZoomIn, HPos.CENTER);
		gp.setHalignment(rotateModelLeft, HPos.CENTER);
		gp.setHalignment(rotateModelRight, HPos.CENTER);
		gp.setHalignment(play, HPos.CENTER);
		gp.setHalignment(g3dLayer.get(), HPos.CENTER);
		
		//Create the new scene
		Scene scene = new Scene(gp, width, height);
		primaryStage.setScene(scene); //Show the scene
		primaryStage.show(); //Show the stage
	}
	
	//Rotate Model Left
	public void rml() {
		models.get(slideNo).rotate(0, 0, -20);
	}
	public void rmr() {
	//Rotate Model Right
		models.get(slideNo).rotate(0, 0, 20);
	}
	//Model Zoom in
	public void mzi() {
		models.get(slideNo).scale(1.1, 1.1, 1.1);
	}
	//Model Zoom out
	public void mzo() {
		models.get(slideNo).scale(0.9, 0.9, 0.9);
	}
	//Play and pause the animation
	public void pause(Button play) {
		if(models.get(slideNo).timeline.getStatus() == Status.RUNNING) {
			models.get(slideNo).timeline.pause();
			play.setText("Play");
		}else {
			play.setText("Pause");
			models.get(slideNo).timeline.play();
		}
	}
	
	public static void main(String[] args) {
		System.out.println("App Running...");
		launch();
		System.out.println("App Finished...");
	}
}
