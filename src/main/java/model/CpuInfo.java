package model;

import javafx.beans.property.*;
import oshi.SystemInfo;
import oshi.hardware.CentralProcessor;
import oshi.hardware.Sensors;




public class CpuInfo extends SysObj {

//    SHARED OBJECTS
    private static final SystemInfo SYS_INFO = Shared.SYSTEM_INFO;
    private static final Sensors SENSORS = Shared.SENSORS;
    private static final CentralProcessor CPU = Shared.CENTRAL_PROCESSOR;


//    JAVAFX PROPERTIES
    private static final ReadOnlyStringWrapper name = new ReadOnlyStringWrapper();
    private static final ReadOnlyIntegerWrapper physicalCores = new ReadOnlyIntegerWrapper();
    private static final ReadOnlyIntegerWrapper logicalCores = new ReadOnlyIntegerWrapper();
    private static final ReadOnlyDoubleWrapper temperature = new ReadOnlyDoubleWrapper();


//    JAVAFX PROPERTY GETTERS
    public static ReadOnlyStringProperty nameProperty() {
        return name.getReadOnlyProperty();
    }

    public static ReadOnlyIntegerProperty physicalCoresProperty() {
        return physicalCores.getReadOnlyProperty();
    }

    public static ReadOnlyIntegerProperty logicalCoresProperty() {
        return logicalCores.getReadOnlyProperty();
    }

    public static ReadOnlyDoubleProperty temperatureProperty() {
        return temperature.getReadOnlyProperty();
    }

//    UPDATE JAVAFX PROPERTIES
    public static void update() {
        name.set(CPU.getProcessorIdentifier().getName());
        physicalCores.set(CPU.getPhysicalProcessorCount());
        logicalCores.set(CPU.getLogicalProcessorCount());
        temperature.set(SENSORS.getCpuTemperature());
    }

//    public static String getCpuInfo() {
//
//
//        StringBuilder cpuInfo = new StringBuilder();
//        cpuInfo.append("CPU Information:\n");
//        cpuInfo.append("Name: ").append(CPU.getProcessorIdentifier().getName()).append("\n");
//        cpuInfo.append("Physical Cores: ").append(CPU.getPhysicalProcessorCount()).append("\n");
//        cpuInfo.append("Logical Cores: ").append(CPU.getLogicalProcessorCount()).append("\n");
//
//        double temperature = SENSORS.getCpuTemperature();
//        if (temperature > 0) {
//            cpuInfo.append("CPU Temperature: ").append(temperature).append(" °C\n");
//        } else {
//            cpuInfo.append("CPU Temperature: Data not available\n");
//        }
//
//
//
//        return cpuInfo.toString();
//    }
}
