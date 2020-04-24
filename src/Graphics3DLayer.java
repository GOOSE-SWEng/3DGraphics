import java.util.ArrayList;
import javafx.scene.SubScene;
import javafx.scene.layout.StackPane;

public class Graphics3DLayer {
	int paneHeight;
	int paneWidth;
	StackPane sp = new StackPane(); //Stack pane for all models
	ArrayList<Model> models;
	SubScene window;
	
	public Graphics3DLayer(int width,int height, ArrayList<Model> models){
		this.paneHeight = height;
		this.paneWidth = width;
		this.models = models;
		sp.setMinSize(width, height);
		window = new SubScene(sp, width, height);
	}
	//Create new model and add to stackpane on this layer
	public void add(String url, int modelWidth, int modelHeight, int xStart, int yStart) {
		Model model =  new Model(url, modelWidth, modelHeight, xStart, yStart);
		models.add(model); //Add models to arraylist of all models
		sp.getChildren().add(model.getModelScene()); //Add to stackpane
	}
	
	public void remove(Graphics3D object) {
		sp.getChildren().remove(object);
	}
	//Return stackpane (This layer of 3D models)
	public StackPane get() {
		return (sp);
	}
}