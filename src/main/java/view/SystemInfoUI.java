package view;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.beans.Observable;
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
import viewmodel.DiscWrapper;
import viewmodel.viewModel;

import java.util.stream.Stream;

public class SystemInfoUI extends Application {
    viewModel vModel = new viewModel();

    // Labels to display system information
    Label gpuLabel = new Label();
    Label ramLabel = new Label();
    Label cpuLabel = new Label();
    Label networkLabel = new Label();
    Label storageLabel = new Label();
    Label batteryLabel = new Label();

    private boolean isDarkMode = true; // Track current theme

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

        // Integrate Metrics tab
        Metrics metrics = new Metrics(); // Instance of the Metrics class
        Tab metricsTab = new Tab("Metrics", metrics.createMetricsChart());
        metricsTab.setClosable(false);

        String gpuTemperature = hardwareMonitorData.getGpuTemperature();
        String gpuFanSpeed = hardwareMonitorData.getGpuFanSpeed();
        String cpuVoltage = hardwareMonitorData.getCpuVoltage();



        // CREATE STRING BINDINGS FROM RAW DATA PROPERTIES

        // CPU string binding
        StringBinding formattedCpuInfo = Bindings.createStringBinding(
                () -> String.format(
                        "CPU Information:\nName: %s\nPhysical Cores: %d\nLogical Cores: %d\nCPU Temperature: %.2f °C",
                        vModel.cpuNameProperty().get(),
                        vModel.cpuPhysicalCoresProperty().get(),
                        vModel.cpuLogicalCoresProperty().get(),
                        vModel.cpuTemperatureProperty().get()
                ),
                vModel.cpuNameProperty(),
                vModel.cpuPhysicalCoresProperty(),
                vModel.cpuLogicalCoresProperty(),
                vModel.cpuTemperatureProperty()
        );

        // RAM string binding
        StringBinding formattedRamInfo = Bindings.createStringBinding(
                () -> String.format(
                        "RAM Information:\nTotal RAM: %d MB\nUsed RAM: %d MB\nFree RAM: %d MB",
                        vModel.ramTotalProperty().get() / (1024 * 1024),
                        vModel.ramUsedProperty().get()  / (1024 * 1024),
                        vModel.ramFreeProperty().get()  / (1024 * 1024)
                        ),
                vModel.ramTotalProperty(),
                vModel.ramUsedProperty(),
                vModel.ramFreeProperty()
        );

        // STORAGE DISCS string binding
        StringBinding cumulativeDiscInfo = Bindings.createStringBinding(() -> {

            // Stringbuilder, concatenates formatted data for all drives.
            StringBuilder sb = new StringBuilder();
            for (DiscWrapper discWrap : vModel.DISC_WRAP_LIST) {
                sb.append(String.format(
                        "Drive: %s\nSerial: %s\nSize: %d GB\nReads: %d\nWrites: %d\nFree Space: %d GB\n-----------------------------------\n",
                        discWrap.nameProperty().get(),
                        discWrap.serialProperty().get(),
                        discWrap.sizeProperty().get() / (1024 * 1024 * 1024),
                        discWrap.readsProperty().get(),
                        discWrap.writesProperty().get(),
                        discWrap.freeSpaceProperty().get() / (1024 * 1024 * 1024)
                ));
            }
            return sb.toString();

            // For each discWrapper in the list, this exposes
            // then binds their properties to the string binding.
        }, vModel.DISC_WRAP_LIST.stream()
                .flatMap(discWrap -> Stream.of(
                        discWrap.nameProperty(),
                        discWrap.serialProperty(),
                        discWrap.sizeProperty(),
                        discWrap.readsProperty(),
                        discWrap.writesProperty(),
                        discWrap.freeSpaceProperty()
                ))
                .toArray(Observable[]::new));

        // INITIAL SYSTEM INFO
        gpuLabel.setText(GpuInfo.getGpuInfo() + "Temperature: " + gpuTemperature + "\n" + "Fan Speed: " + gpuFanSpeed);
//        ramLabel.setText(RamInfo.getRamInfo());
        ramLabel.textProperty().bind(formattedRamInfo);
//        cpuLabel.setText(CpuInfo.getCpuInfo() + "CPU Voltage: " + cpuVoltage);
        cpuLabel.textProperty().bind(formattedCpuInfo);
//        storageLabel.setText(StorageInfo.getStorageInfo());
        storageLabel.textProperty().bind(cumulativeDiscInfo);
        networkLabel.setText(NetworkInfo.getNetworkInfo());
        batteryLabel.setText(BatteryInfo.getBatteryInfo());

        // SETUP NETWORK TAB WITH SCROLL TAB
        networkScrollPane.setContent(networkLabel);
        networkScrollPane.setFitToWidth(true);
        networkScrollPane.setPrefHeight(300);
        Tab networkTab = new Tab("NETWORK", networkScrollPane);

        networkTab.setClosable(false);
        tabPane.getTabs().addAll(cpuTab, gpuTab, ramTab, storageTab, networkTab, batteryTab, metricsTab);

        // SETTINGS TAB
        Tab settingsTab = new Tab("Settings");
        ComboBox<String> chibiComboBox = new ComboBox<>();
        chibiComboBox.getItems().addAll("Chibi 1", "Chibi 2", "Chibi 3");
        chibiComboBox.setValue("Chibi 1");

        ComboBox<String> trackerDropdown = new ComboBox<>();
        trackerDropdown.getItems().addAll("RAM Usage", "CPU Temp", "GPU Temp");

        ComboBox<String> themeDropdown = new ComboBox<>();
        themeDropdown.getItems().addAll("Dark Mode", "Light Mode");


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

        ToggleButton themeToggle = new ToggleButton("Switch to Light Mode");
        themeDropdown.setOnAction(event -> {
            String selectedTheme = themeDropdown.getValue();
            if (selectedTheme.equals("Dark Mode")) {
                Scene scene = primaryStage.getScene();
                scene.getStylesheets().remove(getClass().getResource("/light-theme.css").toExternalForm());
                scene.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm());
                themeToggle.setText("Switch to Light Mode");
            } else {
                Scene scene = primaryStage.getScene();
                scene.getStylesheets().remove(getClass().getResource("/styles.css").toExternalForm());
                scene.getStylesheets().add(getClass().getResource("/light-theme.css").toExternalForm());
                themeToggle.setText("Switch to Dark Mode");
            }
            isDarkMode = !isDarkMode; // Toggle the mode
        });


        settingsTab.setContent(new VBox(
                new Label ("Select Theme: "), themeDropdown,
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
            // RE-BIND LABELS
            cpuLabel.textProperty().bind(formattedCpuInfo);
            ramLabel.textProperty().bind(formattedRamInfo);
            storageLabel.textProperty().bind(cumulativeDiscInfo);

            // SET LABELS TO ON
            gpuLabel.setText(GpuInfo.getGpuInfo() + "Temperature: " + gpuTemperature + "\n" + "Fan Speed: " + gpuFanSpeed);
//            ramLabel.setText(RamInfo.getRamInfo());
//            cpuLabel.setText(CpuInfo.getCpuInfo() + "CPU Voltage: " + cpuVoltage);
//            storageLabel.setText(StorageInfo.getStorageInfo());
            networkLabel.setText(NetworkInfo.getNetworkInfo());
            batteryLabel.setText(BatteryInfo.getBatteryInfo());
            chibiManager.setMonitoringTracker(trackerDropdown.getValue());
        });

        // Add action to "Off" button
        offButton.setOnAction(event -> {
            // UNBIND LABELS
            cpuLabel.textProperty().unbind();
            ramLabel.textProperty().unbind();
            storageLabel.textProperty().unbind();

            // SET LABELS TO OFF
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
            vModel.update();
            gpuLabel.setText(GpuInfo.getGpuInfo() + "Temperature: " + gpuTemperature + "\n" + "Fan Speed: " + gpuFanSpeed);
//            ramLabel.setText(RamInfo.getRamInfo());
//            cpuLabel.setText(CpuInfo.getCpuInfo() + "CPU Voltage: " + cpuVoltage);
//            storageLabel.setText(StorageInfo.getStorageInfo());
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
