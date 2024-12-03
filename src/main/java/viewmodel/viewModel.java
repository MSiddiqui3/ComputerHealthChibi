package viewmodel;

import javafx.beans.property.*;
import model.CpuInfo;

public class viewModel {
    private final ReadOnlyStringWrapper cpuName = new ReadOnlyStringWrapper();
    private final ReadOnlyIntegerWrapper cpuPhysicalCores = new ReadOnlyIntegerWrapper();
    private final ReadOnlyIntegerWrapper cpuLogicalCores = new ReadOnlyIntegerWrapper();
    private final ReadOnlyDoubleWrapper cpuTemperature = new ReadOnlyDoubleWrapper();

    public viewModel() {
        CpuInfo.update();
        bind();
    }

    public void update() {
        CpuInfo.update();
    }

    public ReadOnlyStringProperty cpuNameProperty() {
        return cpuName.getReadOnlyProperty();
    }

    public ReadOnlyIntegerProperty cpuPhysicalCoresProperty() {
        return cpuPhysicalCores.getReadOnlyProperty();
    }

    public ReadOnlyIntegerProperty cpuLogicalCoresProperty() {
        return cpuLogicalCores.getReadOnlyProperty();
    }

    public ReadOnlyDoubleProperty cpuTemperatureProperty() {
        return cpuTemperature.getReadOnlyProperty();
    }

    public void bind() {
        cpuName.bind(CpuInfo.nameProperty());
        cpuPhysicalCores.bind(CpuInfo.physicalCoresProperty());
        cpuLogicalCores.bind(CpuInfo.logicalCoresProperty());
        cpuTemperature.bind(CpuInfo.temperatureProperty());
    }

}
