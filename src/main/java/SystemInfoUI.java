import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;
import oshi.SystemInfo;
import oshi.hardware.GraphicsCard;
import com.sun.management.OperatingSystemMXBean;
import oshi.hardware.HWDiskStore;
import oshi.hardware.HardwareAbstractionLayer;

import java.lang.management.ManagementFactory;
import java.util.List;

public class SystemInfoUI extends Application {

    // Labels to display system information
    Label gpuLabel = new Label();
    Label ramLabel = new Label();
    Label cpuLabel = new Label("CPU Usage:");

    Label networkLabel = new Label();

    Label storageLabel = new Label();

    HardwareMonitorData hardwareMonitorData = new HardwareMonitorData();

    // ImageView to display images at the bottom
    ImageView imageView = new ImageView();

    // Timeline for real-time monitoring
    Timeline timeline;

    ScrollPane scrollPane = new ScrollPane();

    ChibiSettings chibiSettings;

    @Override
    public void start(Stage primaryStage) {

        // Initialize ChibiSettings with the imageView
        chibiSettings = new ChibiSettings(imageView);
        chibiSettings.initializeChibi();
        chibiSettings.addSwayingAnimation();

        //SETUP TABS FOR THE MAIN COMPONENTS
        TabPane tabPane = new TabPane();

        Tab gpuTab = new Tab("GPU");
        gpuTab.setClosable(false);
        Tab cpuTab = new Tab("CPU");
        cpuTab.setClosable(false);
        Tab ramTab = new Tab("RAM");
        ramTab.setClosable(false);
        Tab storageTab= new Tab("STORAGE");
        storageTab.setClosable(false);
        Tab networkTab= new Tab("NETWORK" , scrollPane);
        networkTab.setClosable(false);




        VBox gpuBox = new VBox(10, gpuLabel);
        VBox cpuBox = new VBox(10, cpuLabel);
        VBox ramBox = new VBox(10, ramLabel);
        VBox storageBox = new VBox(10, storageLabel);
        VBox networkBox = new VBox(10, networkLabel);



        cpuTab.setContent(cpuBox);
        gpuTab.setContent(gpuBox);
        ramTab.setContent(ramBox);
        storageTab.setContent(storageBox);
        networkTab.setContent(networkBox);

        tabPane.getTabs().addAll(cpuTab, gpuTab, ramTab, storageTab, networkTab);

        String gpuTemperature = hardwareMonitorData.getGpuTemperature();
        String gpuFanSpeed = hardwareMonitorData.getGpuFanSpeed();
        String cpuVoltage = hardwareMonitorData.getCpuVoltage();
        initializeSystemInfo();

        gpuLabel.setText(GpuInfo.getGpuInfo() + gpuTemperature);
        ramLabel.setText(RamInfo.getRamInfo());
        cpuLabel.setText(CpuInfo.getCpuInfo());
        storageLabel.setText(StorageInfo.getStorageInfo());
       // networkLabel.setText(NetworkInfo.getNetworkInfo());

        //CREATING ON AND OFF BUTTONS
        Button onButton = new Button("On");
        Button offButton = new Button("Off");

        onButton.setOnAction(event -> {
            gpuLabel.setText(GpuInfo.getGpuInfo() +"Temperature: " + gpuTemperature +"\n" + "Fan Speed: " + gpuFanSpeed);
            ramLabel.setText(RamInfo.getRamInfo());
            cpuLabel.setText(CpuInfo.getCpuInfo()  + "CPU Voltage: " + cpuVoltage);
            storageLabel.setText(StorageInfo.getStorageInfo());
            //networkLabel.setText(NetworkInfo.getNetworkInfo());
            timeline.play();
        });

        offButton.setOnAction(event -> {
            gpuLabel.setText("GPU System Monitoring Off");
            ramLabel.setText("RAM System Monitoring Off");
            cpuLabel.setText("CPU System Monitoring Off");
            storageLabel.setText("Storage Monitoring Off");
            networkLabel.setText("Network Monitoring Off");
            timeline.pause();
        });

        //SETTINGS TAB

        Tab settingsTab = new Tab("⚙ Settings");

        //BOX WITH OPTIONS OF APPEARANCE
        ComboBox<String> chibiComboBox = new ComboBox<>();
        chibiComboBox.getItems().addAll("Chibi 1", "Chibi 2", "Chibi 3");  // List of chibi options
        chibiComboBox.setValue("Chibi 1");


        chibiComboBox.setOnAction(event -> {
            String selectedChibi = chibiComboBox.getValue();
            chibiSettings.changeChibiAppearance(selectedChibi);  // Update chibi appearance
        });


        VBox settingsBox = new VBox(10, new Label("Select Chibi Appearance:"), chibiComboBox);
        settingsTab.setContent(settingsBox);
        settingsTab.setClosable(false);
        settingsTab.getStyleClass().add("settings-tab");
        tabPane.getTabs().add(settingsTab);


        //SETUP LAYOUT
        BorderPane root = new BorderPane();
        root.setTop(tabPane);
        root.setCenter(imageView);
        root.setBottom(new VBox(10, onButton, offButton));

        //CREATES SCENE
        Scene scene = new Scene(root, 800, 600);
        scene.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm());
        primaryStage.setTitle("Computer Health Chibi");
        primaryStage.setScene(scene);
        primaryStage.show();

        //BEGINS SYSTEM MONITORING
        initializeSystemInfo();


        //REALTIME UPDATES
        timeline = new Timeline(new KeyFrame(Duration.seconds(2), event -> {
            gpuLabel.setText(GpuInfo.getGpuInfo() +"Temperature: " + gpuTemperature +"\n" + "Fan Speed: " + gpuFanSpeed);
            ramLabel.setText(RamInfo.getRamInfo());
            cpuLabel.setText(CpuInfo.getCpuInfo()  + "CPU Voltage: " + cpuVoltage);
            storageLabel.setText(StorageInfo.getStorageInfo());
           // networkLabel.setText(NetworkInfo.getNetworkInfo());



        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }


    private void initializeSystemInfo() {
        ramLabel.setText(RamInfo.getRamInfo());
        cpuLabel.setText(CpuInfo.getCpuInfo());
        gpuLabel.setText(GpuInfo.getGpuInfo());
        storageLabel.setText(StorageInfo.getStorageInfo());
      //  networkLabel.setText(NetworkInfo.getNetworkInfo());
    }




    public static void main(String[] args) {
        launch(args);
    }
}
