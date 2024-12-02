package view;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.StringBinding;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.*;
import viewmodel.viewModel;

public class SystemInfoUI extends Application {
    viewModel vModel = new viewModel();

    // Labels to display system information
    Label gpuLabel = new Label();
    Label ramLabel = new Label();
    Label cpuLabel = new Label();
    Label networkLabel = new Label();
    Label storageLabel = new Label();
    Label batteryLabel = new Label();

    HardwareMonitorData hardwareMonitorData = new HardwareMonitorData();

    // Chibi Avatar
    ImageView imageView = new ImageView();
    ChibiManager chibiManager; // Chibi manager to handle all chibi logic

    // SCROLLPANE
    ScrollPane networkScrollPane = new ScrollPane();

    @Override
    public void start(Stage primaryStage) {
        // Initialize view.ChibiManager with imageView and ramStatusLabel
        Label ramStatusLabel = new Label("");
        Label cpuStatusLabel = new Label("");
        Label gpuStatusLabel = new Label("");
        chibiManager = new ChibiManager(imageView, ramStatusLabel, cpuStatusLabel, gpuStatusLabel);
        chibiManager.addSwayingAnimation();  // Add swaying animation for chibi

        chibiManager.startRamMonitoring();  // Start RAM monitoring

        // SETUP TABS FOR THE MAIN COMPONENTS
        TabPane tabPane = new TabPane();

        // GPU, CPU, RAM, Storage Tabs
        Tab gpuTab = new Tab("GPU", new VBox(gpuLabel));
        gpuTab.setClosable(false);
        Tab cpuTab = new Tab("CPU", new VBox(cpuLabel));
        cpuTab.setClosable(false);
        Tab ramTab = new Tab("RAM", new VBox(ramLabel));
        ramTab.setClosable(false);
        Tab storageTab = new Tab("STORAGE", new VBox(storageLabel));
        storageTab.setClosable(false);
        Tab batteryTab = new Tab("BATTERY", new VBox(batteryLabel));
        batteryTab.setClosable(false);

        String gpuTemperature = hardwareMonitorData.getGpuTemperature();
        String gpuFanSpeed = hardwareMonitorData.getGpuFanSpeed();
        String cpuVoltage = hardwareMonitorData.getCpuVoltage();

        // CREATE STRING BINDINGS FROM RAW DATA PROPERTIES
        StringBinding formattedCpuInfo = Bindings.createStringBinding(
                () -> String.format(
                        "CPU Information:\nName: %s\nPhysical Cores: %d\nLogical Cores: %d\nCPU Temperature: %.2f °C",
                        vModel.cpuNameProperty().get(),
                        vModel.cpuPhysicalCoresProperty().get(),
                        vModel.cpuLogicalCoresProperty().get(),
                        vModel.cpuTemperatureProperty().get()
                )
        );

        // INITIAL SYSTEM INFO
        gpuLabel.setText(GpuInfo.getGpuInfo() + "Temperature: " + gpuTemperature + "\n" + "Fan Speed: " + gpuFanSpeed);
        ramLabel.setText(RamInfo.getRamInfo());
//        cpuLabel.setText(CpuInfo.getCpuInfo() + "CPU Voltage: " + cpuVoltage);
        cpuLabel.textProperty().bind(formattedCpuInfo);
        storageLabel.setText(StorageInfo.getStorageInfo());
        networkLabel.setText(NetworkInfo.getNetworkInfo());
        batteryLabel.setText(BatteryInfo.getBatteryInfo());

        // SETUP NETWORK TAB WITH SCROLL TAB
        networkScrollPane.setContent(networkLabel);
        networkScrollPane.setFitToWidth(true);
        networkScrollPane.setPrefHeight(300);
        Tab networkTab = new Tab("NETWORK", networkScrollPane);

        networkTab.setClosable(false);
        tabPane.getTabs().addAll(cpuTab, gpuTab, ramTab, storageTab, networkTab, batteryTab);

        // SETTINGS TAB
        Tab settingsTab = new Tab("Settings");
        ComboBox<String> chibiComboBox = new ComboBox<>();
        chibiComboBox.getItems().addAll("Chibi 1", "Chibi 2", "Chibi 3");
        chibiComboBox.setValue("Chibi 1");

        ComboBox<String> trackerDropdown = new ComboBox<>();
        trackerDropdown.getItems().addAll("RAM Usage", "CPU Temp", "GPU Temp");

        //DISCLAIMER TEXT BOX
        Label disclaimerLabel = new Label("DISCLAIMER: This application works best in conjunction with Open Hardware Monitor "
                + "a free to use open source monitor that allows Computer Health Chibi to monitor certain extra aspects of your system. ");
        disclaimerLabel.setWrapText(true);
        disclaimerLabel.setMaxWidth(750);
        disclaimerLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: red;");



        chibiComboBox.setOnAction(event -> {
            String selectedChibi = chibiComboBox.getValue();
            chibiManager.changeChibiAppearance(selectedChibi);  // Change chibi appearances
        });

        trackerDropdown.setOnAction(event -> {
            String selectedTracker = trackerDropdown.getValue();
            chibiManager.setMonitoringTracker(selectedTracker);

            cpuStatusLabel.setVisible(selectedTracker.equals("CPU Temp"));
            ramStatusLabel.setVisible(selectedTracker.equals("RAM Usage"));
            gpuStatusLabel.setVisible(selectedTracker.equals("GPU Temp"));
        });


        settingsTab.setContent(new VBox(
                new Label("Select Chibi Appearance: "), chibiComboBox,
                new Label("Select Chibi Tracker: "), trackerDropdown,
                disclaimerLabel
                ));
        settingsTab.setClosable(false);

        tabPane.getTabs().add(settingsTab);

        // SETUP LAYOUT
        BorderPane root = new BorderPane();
        root.setTop(tabPane);
        root.setCenter(imageView);  // Chibi will be displayed in the center
        VBox layout = new VBox(10, imageView, ramStatusLabel , cpuStatusLabel, gpuStatusLabel);
        root.setCenter(layout);  // Set VBox layout including image and status label

        // On/Off Buttons
        Button onButton = new Button("On");
        Button offButton = new Button("Off");

        // Add action to "On" button
        onButton.setOnAction(event -> {
            gpuLabel.setText(GpuInfo.getGpuInfo() + "Temperature: " + gpuTemperature + "\n" + "Fan Speed: " + gpuFanSpeed);
            ramLabel.setText(RamInfo.getRamInfo());
            cpuLabel.setText(CpuInfo.getCpuInfo() + "CPU Voltage: " + cpuVoltage);
            storageLabel.setText(StorageInfo.getStorageInfo());
            networkLabel.setText(NetworkInfo.getNetworkInfo());
            batteryLabel.setText(BatteryInfo.getBatteryInfo());
            chibiManager.setMonitoringTracker(trackerDropdown.getValue());
        });

        // Add action to "Off" button
        offButton.setOnAction(event -> {
            gpuLabel.setText("GPU System Monitoring Off");
            ramLabel.setText("RAM System Monitoring Off");
            cpuLabel.setText("CPU System Monitoring Off");
            storageLabel.setText("Storage Monitoring Off");
            networkLabel.setText("Network Monitoring Off");
            batteryLabel.setText("Battery Monitoring Off");
            //chibiManager.stopAllMonitoring();  // Stop RAM monitoring
        });

        // Add buttons to the bottom
        root.setBottom(new VBox(10, onButton, offButton));

        // SCENE SETUP
        Scene scene = new Scene(root, 800, 600);
        scene.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm());
        primaryStage.setTitle("Computer Health Chibi");
        primaryStage.setScene(scene);
        primaryStage.show();



        // REALTIME UPDATES (2-second interval)
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(2), event -> {
            gpuLabel.setText(GpuInfo.getGpuInfo() + "Temperature: " + gpuTemperature + "\n" + "Fan Speed: " + gpuFanSpeed);
            ramLabel.setText(RamInfo.getRamInfo());
//            cpuLabel.setText(CpuInfo.getCpuInfo() + "CPU Voltage: " + cpuVoltage);
            vModel.update();
            storageLabel.setText(StorageInfo.getStorageInfo());
            networkLabel.setText(NetworkInfo.getNetworkInfo());
            batteryLabel.setText(BatteryInfo.getBatteryInfo());
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
