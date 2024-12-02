package viewmodel;

import javafx.beans.property.*;
import model.CpuInfo;

public class viewModel {
    private final StringProperty cpuName = new SimpleStringProperty();
    private final IntegerProperty cpuPhysicalCores = new SimpleIntegerProperty();
    private final IntegerProperty cpuLogicalCores = new SimpleIntegerProperty();
    private final DoubleProperty cpuTemperature = new SimpleDoubleProperty();

    public viewModel() {
        CpuInfo.update();
        bind();
    }

    public void update() {
        CpuInfo.update();
    }

    public StringProperty cpuNameProperty() {
        return cpuName;
    }

    public IntegerProperty cpuPhysicalCoresProperty() {
        return cpuPhysicalCores;
    }

    public IntegerProperty cpuLogicalCoresProperty() {
        return cpuLogicalCores;
    }

    public DoubleProperty cpuTemperatureProperty() {
        return cpuTemperature;
    }

    public void bind() {
        cpuName.bind(CpuInfo.nameProperty());
        cpuPhysicalCores.bind(CpuInfo.physicalCoresProperty());
        cpuLogicalCores.bind(CpuInfo.logicalCoresProperty());
        cpuTemperature.bind(CpuInfo.temperatureProperty());
    }

}
