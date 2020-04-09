import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class Graphics3D extends Application{
	Stage primaryStage;
	int width = 1280;
	int height = 720;
	static String url = "src/3D_Models/HST-3DS/hst.3ds";
	
	@Override
	public void start(Stage stage) throws Exception {
		System.out.println("Start Running...");
		primaryStage = stage;
		primaryStage.setTitle("3D Test");
		BorderPane bp = new BorderPane();
		//SubScene modelScene;
		Model telescope = new Model(url, width, height);

		bp.setCenter(telescope.getModelScene());
		telescope.rotate(0,0,0);
		Scene scene = new Scene(bp, width, height);
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	
	public static void main(String[] args) {
		System.out.println("App Running...");
		launch();
		System.out.println("App Finished...");
	}
}
