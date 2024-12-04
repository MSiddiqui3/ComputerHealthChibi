package viewmodel;

import javafx.beans.property.*;
import model.CpuInfo;
import model.DiscObj;
import model.RamInfo;

import java.util.LinkedList;
import java.util.List;

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

    // STORAGE LIST
    // The List obj's contain storage properties
    public final List<DiscWrapper> DISC_WRAP_LIST = new LinkedList<DiscWrapper>();



    // CONSTRUCTOR
    public viewModel() {
        CpuInfo.update();
        RamInfo.update();

        // Create DiscWrapper objects
        for (DiscObj discObj : DiscObj.LIST) {
            DiscWrapper discWrap = new DiscWrapper(discObj);
            DISC_WRAP_LIST.add(discWrap);
            discWrap.update();
        }
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


        // Bind DiscWrappers to contained DiscObj properties
        for (DiscWrapper discWrap : DISC_WRAP_LIST) {
            discWrap.bind();
        }
    }


    // UPDATE ENTIRE MODEL
    public void update() {
        CpuInfo.update();
        RamInfo.update();

        // Update DiscWrappers
        for (DiscWrapper discWrp : DISC_WRAP_LIST) {
            discWrp.update();
        }
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
