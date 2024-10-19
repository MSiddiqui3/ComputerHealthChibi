import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;

public class ImageSlideshow extends Application {

    @Override
    public void start(Stage primaryStage) {
        // ImageView to display the images
        ImageView imageView = new ImageView();
        imageView.setFitHeight(300); // Adjust size
        imageView.setFitWidth(400);
        imageView.setPreserveRatio(true);

        // Array of image paths
        String[] images = {
                getClass().getResource("/images/chibi1.png").toExternalForm(),
                getClass().getResource("/images/chibi2.png").toExternalForm(),
                getClass().getResource("/images/chibi3.png").toExternalForm()
        };

        // Timeline to switch between images
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(2), event -> {
            // Get the current image index and switch to the next image
            int currentIndex = Integer.parseInt(imageView.getUserData() == null ? "0" : imageView.getUserData().toString());
            currentIndex = (currentIndex + 1) % images.length; // Loop around to the first image
            imageView.setImage(new Image(images[currentIndex]));
            imageView.setUserData(currentIndex); // Store the current index in the ImageView
        }));
        timeline.setCycleCount(Timeline.INDEFINITE); // Repeat indefinitely
        timeline.play(); // Start the slideshow

        // Layout setup
        StackPane root = new StackPane(imageView);
        Scene scene = new Scene(root, 400, 300);

        // Set up the stage
        primaryStage.setTitle("Image Slideshow");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
