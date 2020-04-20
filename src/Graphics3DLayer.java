import java.util.ArrayList;
import javafx.scene.SubScene;
import javafx.scene.layout.StackPane;

public class Graphics3DLayer {
	int paneHeight;
	int paneWidth;
	StackPane sp = new StackPane();
	ArrayList<Model> models = new ArrayList<Model>();
	SubScene window;
	
	public Graphics3DLayer(int width,int height, ArrayList<Model> models){
		this.paneHeight = height;
		this.paneWidth = width;
		//sp.getChildren().add(canvas);
		sp.setMinSize(width, height);
		window = new SubScene(sp, width, height);
	}
	
	public void add(String url, int modelWidth, int modelHeight, int xStart, int yStart) {
		Model model =  new Model(url, modelWidth, modelHeight, paneWidth, paneHeight, xStart, yStart);
		models.add(model);
		sp.getChildren().add(models.get(index))
	}
	
	public void remove(Graphics3D object) {
		sp.getChildren().remove(object);
	}
	
	public SubScene get() {
		return (window);
	}
}