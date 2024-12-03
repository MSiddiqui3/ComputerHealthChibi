package view;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import model.GpuInfo;
import model.RamInfo;
import oshi.SystemInfo;
import oshi.hardware.CentralProcessor;
import oshi.hardware.GlobalMemory;
import oshi.hardware.HardwareAbstractionLayer;

public class Metrics {
    private static final int WINDOW_SIZE = 30; // Number of data points shown on the graph
    private final HardwareAbstractionLayer hardware;
    private final CentralProcessor processor;
    private final GlobalMemory memory;

    private XYChart.Series<Number, Number> activeSeries;
    private int timeCounter = 0; // Keeps track of elapsed time
    private long[] prevTicks;

    public Metrics() {
        SystemInfo systemInfo = new SystemInfo();
        hardware = systemInfo.getHardware();
        processor = hardware.getProcessor();
        memory = hardware.getMemory();
        prevTicks = processor.getSystemCpuLoadTicks();
        GpuInfo gpuInfo = new GpuInfo();
    }

    public VBox createMetricsChart() {
        // Create axes
        NumberAxis xAxis = new NumberAxis(0, WINDOW_SIZE, 1);
        xAxis.setLabel("Time (s)");

        NumberAxis yAxis = new NumberAxis(0, 100, 10);
        yAxis.setLabel("Usage (%)");

        // Create the LineChart
        LineChart<Number, Number> lineChart = new LineChart<>(xAxis, yAxis);
        lineChart.setTitle("System Metrics");
        lineChart.setAnimated(false);

        // Create the initial data series
        activeSeries = new XYChart.Series<>();
        activeSeries.setName("CPU Usage"); // Default metric
        lineChart.getData().add(activeSeries);

        // Create a ComboBox for selecting the metric
        ComboBox<String> metricSelector = new ComboBox<>();
        metricSelector.getItems().addAll("CPU Usage", "RAM Usage");
        metricSelector.setValue("CPU Usage"); // Default selection
        metricSelector.setOnAction(event -> updateMetric(metricSelector.getValue()));

        // Start timeline to update the chart
        startTimeline();

        // Layout
        return new VBox(10, metricSelector, lineChart);
    }

    private void updateMetric(String metric) {
        // Clear the current data
        activeSeries.getData().clear();

        // Update the series name based on the selected metric
        activeSeries.setName(metric);
    }

    private void startTimeline() {
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
            double value = 0.0;
            switch (activeSeries.getName()) {
                case "CPU Usage":
                    value = processor.getSystemCpuLoadBetweenTicks(prevTicks) * 100;
                    prevTicks = processor.getSystemCpuLoadTicks();
                    break;
                case "RAM Usage":
                    long totalRam = RamInfo.getTotalMemory();
                    long freeRam = RamInfo.getAvailableMemory();
                    value = ((double) (totalRam - freeRam) / totalRam) * 100;
                    break;

            }

            double finalValue = value;
            javafx.application.Platform.runLater(() -> {
                activeSeries.getData().add(new XYChart.Data<>(timeCounter, finalValue));

                //Limit data points to avoid performance issues
                if (activeSeries.getData().size() > WINDOW_SIZE) {
                    activeSeries.getData().clear(); // Clear old data points
                    timeCounter = 0; // Reset time counter
                }
            });

            timeCounter++;
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }
}
