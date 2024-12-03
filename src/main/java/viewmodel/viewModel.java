package viewmodel;

import javafx.beans.property.*;
import model.CpuInfo;
import model.RamInfo;

public class viewModel {

    // CPU PROPERTIES
    private final ReadOnlyStringWrapper cpuName = new ReadOnlyStringWrapper();
    private final ReadOnlyIntegerWrapper cpuPhysicalCores = new ReadOnlyIntegerWrapper();
    private final ReadOnlyIntegerWrapper cpuLogicalCores = new ReadOnlyIntegerWrapper();
    private final ReadOnlyDoubleWrapper cpuTemperature = new ReadOnlyDoubleWrapper();

    // RAM PROPERTIES
    private final ReadOnlyLongWrapper ramTotal = new ReadOnlyLongWrapper();
    private final ReadOnlyLongWrapper ramFree = new ReadOnlyLongWrapper();
    private final ReadOnlyLongWrapper ramUsed = new ReadOnlyLongWrapper();


    // CONSTRUCTOR
    public viewModel() {
        CpuInfo.update();
        RamInfo.update();
        bind();
    }


    // BIND PROPERTIES
    public void bind() {
        cpuName.bind(CpuInfo.nameProperty());
        cpuPhysicalCores.bind(CpuInfo.physicalCoresProperty());
        cpuLogicalCores.bind(CpuInfo.logicalCoresProperty());
        cpuTemperature.bind(CpuInfo.temperatureProperty());

        ramTotal.bind(RamInfo.totalMemoryProperty());
        ramFree.bind(RamInfo.freeMemoryProperty());
        ramUsed.bind(RamInfo.usedMemoryProperty());
    }


    // UPDATE ENTIRE MODEL
    public void update() {
        CpuInfo.update();
        RamInfo.update();
    }


    // CPU PROPERTY GETTERS
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


    // RAM PROPERTY GETTERS
    public ReadOnlyLongProperty ramTotalProperty() {
        return ramTotal.getReadOnlyProperty();
    }

    public ReadOnlyLongProperty ramFreeProperty() {
        return ramFree.getReadOnlyProperty();
    }

    public ReadOnlyLongProperty ramUsedProperty() {
        return ramUsed.getReadOnlyProperty();
    }
}
