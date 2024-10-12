import javafx.animation.TranslateTransition;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

public class ChibiSettings {
    private ImageView imageView;

    //CONSTRUCTOR FOR THE CHIBI
    public ChibiSettings(ImageView imageView) {
        this.imageView = imageView;
    }

    //INITIALIZE CHIBI
    public void initializeChibi() {
        setChibiImage("/images/chibi1.png");
    }

    //ALLOWS FOR CHANGING OF CHIBI APPEARANCE
    public void changeChibiAppearance(String chibiOption) {
        switch (chibiOption) {
            case "Chibi 1":
                setChibiImage("/images/chibi1.png");
                break;
            case "Chibi 2":
                setChibiImage("/images/chibi2.png");
                break;
            case "Chibi 3":
                setChibiImage("/images/chibi3.png");
                break;
            default:
                setChibiImage("/images/chibi1.png");
                break;
        }
    }

    // Method to set the image for the chibi character
    private void setChibiImage(String imagePath) {
        try {
            Image chibiImage = new Image(getClass().getResource(imagePath).toExternalForm());
            imageView.setImage(chibiImage);
            imageView.setFitHeight(150);
            imageView.setPreserveRatio(true);
        } catch (Exception e) {
            System.out.println("Error loading chibi image: " + e.getMessage());
        }
    }

    //BASIC ANIMATION
    public void addSwayingAnimation() {
        TranslateTransition sway = new TranslateTransition(Duration.millis(1000), imageView);
        sway.setFromX(-20);  // Move 20 pixels left
        sway.setToX(20);     // Move 20 pixels right
        sway.setAutoReverse(true);  // Automatically reverse after each cycle
        sway.setCycleCount(TranslateTransition.INDEFINITE);  // Repeat indefinitely
        sway.play();  // Start the animation
    }
}
