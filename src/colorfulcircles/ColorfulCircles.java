package colorfulcircles;


import static java.lang.Math.random;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.effect.BlendMode;
import javafx.scene.effect.BoxBlur;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * Created with this tutorial:
 * <a href=http://docs.oracle.com/javafx/2.0/get_started/jfxpub-get_started.htm>Tutorial</a>
 * @author Michael Koppen - koppen@fh-brandenburg.de
 */
public class ColorfulCircles extends Application {
	private Scene scene;
	private Stage primaryStage;

	/**
	 * @param args the command line arguments
	 */
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStageParam) {
		primaryStage = primaryStageParam;
		//Set up Stage
		Group root = new Group();
		Screen screen = Screen.getPrimary();
		Rectangle2D bounds = screen.getVisualBounds();
		scene = new Scene(root, bounds.getWidth(), bounds.getHeight(), Color.BLACK);
		primaryStage.setScene(scene);

		//Background gradient
		Rectangle colors = new Rectangle(scene.getWidth(), scene.getHeight(),
				new LinearGradient(0f, 1f, 1f, 0f, true, CycleMethod.NO_CYCLE, new Stop[]{
					new Stop(0, Color.web("#f8bd55")),
					new Stop(0.14, Color.web("#c0fe56")),
					new Stop(0.28, Color.web("#5dfbc1")),
					new Stop(0.43, Color.web("#64c2f8")),
					new Stop(0.57, Color.web("#be4af7")),
					new Stop(0.71, Color.web("#ed5fc2")),
					new Stop(0.85, Color.web("#ef504c")),
					new Stop(1, Color.web("#f2660f")),}));

		
		//Set up 30 Circles
		Group circles = new Group();
		for (int i = 0; i < 30; i++) {
			Circle circle = new Circle(150, Color.web("white", 0.05));
			circle.setStrokeType(StrokeType.OUTSIDE);
			circle.setStroke(Color.web("white", 0.16));
			circle.setStrokeWidth(4);
			circles.getChildren().add(circle);
		}
		
		//Set up Effects (Blur)
		circles.setEffect(new BoxBlur(10, 10, 3));
		
		Group blendModeGroup =
				new Group(new Group(new Rectangle(scene.getWidth(), scene.getHeight(),
				Color.BLACK), circles), colors);
		
		colors.setBlendMode(BlendMode.OVERLAY);
		root.getChildren().add(blendModeGroup);

		//Set up Movement
		Timeline timeline = new Timeline();
		for (Node circle : circles.getChildren()) {
			timeline.getKeyFrames().addAll(
					new KeyFrame(Duration.ZERO, // set start position at 0
						new KeyValue(circle.translateXProperty(), random() * scene.getWidth()),
						new KeyValue(circle.translateYProperty(), random() * scene.getHeight())),
					new KeyFrame(Duration.INDEFINITE, // set end position at 40s
						new KeyValue(circle.translateXProperty(), random() * scene.getWidth()),
						new KeyValue(circle.translateYProperty(), random() * scene.getHeight())));
		}
		// play 40s of animation
		timeline.play();

		//adding button
		Button btn = new Button();
		btn.setText("Fullscreen");
		btn.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				toggleFullscreen();
			}
		});
		
		root.getChildren().add(btn);

		//Show Stage
		primaryStage.setFullScreen(true);
		primaryStage.show();
	}
	private void toggleFullscreen(){
		if (primaryStage.isFullScreen()) {
			primaryStage.setFullScreen(false);
		}else{
			primaryStage.setFullScreen(true);
		}
	}
}
