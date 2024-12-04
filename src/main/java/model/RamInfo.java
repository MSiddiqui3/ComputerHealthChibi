package model;

import java.lang.management.ManagementFactory;


import com.sun.management.OperatingSystemMXBean;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.LongBinding;
import javafx.beans.property.ReadOnlyLongProperty;
import javafx.beans.property.ReadOnlyLongWrapper;
import oshi.hardware.GlobalMemory;

public class RamInfo  {

    // SHARED OBJECTS
    private static final OperatingSystemMXBean osBean = Shared.osBean;


    // JAVAFX PROPERTIES
    private static final ReadOnlyLongWrapper totalMemory = new ReadOnlyLongWrapper();
    private static final ReadOnlyLongWrapper freeMemory = new ReadOnlyLongWrapper();
    private static final ReadOnlyLongWrapper usedMemory = new ReadOnlyLongWrapper();


    // JAVAFX PROPERTY GETTERS
    public static ReadOnlyLongProperty totalMemoryProperty() {
        return totalMemory.getReadOnlyProperty();
    }

    public static ReadOnlyLongProperty freeMemoryProperty() {
        return freeMemory.getReadOnlyProperty();
    }

    public static ReadOnlyLongProperty usedMemoryProperty() {
        return usedMemory.getReadOnlyProperty();
    }


    // BIND usedMemory TO CALCULATE FROM totalMemory AND freeMemory
    static {
        LongBinding usedMemoryBinding = Bindings.createLongBinding(
                () -> totalMemory.get() - freeMemory.get(),
                totalMemory, freeMemory
        );
        usedMemory.bind(usedMemoryBinding);
    }


    // UPDATE JAVAFX PROPERTIES
    public static void update() {
        long totalMemory = osBean.getTotalPhysicalMemorySize();
        long freeMemory = osBean.getFreePhysicalMemorySize();

        RamInfo.totalMemory.set(totalMemory);
        RamInfo.freeMemory.set(freeMemory);
    }

//    public static String getRamInfo() {
//        long totalMemory = osBean.getTotalPhysicalMemorySize();
//        long freeMemory = osBean.getFreePhysicalMemorySize();
//        long usedMemory = totalMemory - freeMemory;
//
//
//        return "RAM Information:\n" +
//                "Total RAM: " + (totalMemory / (1024 * 1024)) + " MB\n" +
//                "Used RAM: " + (usedMemory / (1024 * 1024)) + " MB\n" +
//                "Free RAM: " + (freeMemory / (1024 * 1024)) + " MB";
//    }

    public static long getTotalMemory() {
        return osBean.getTotalPhysicalMemorySize(); // Total system memory in bytes
    }

    public static long getAvailableMemory() {

        OperatingSystemMXBean osBean = ManagementFactory.getPlatformMXBean(OperatingSystemMXBean.class);
        long totalMemory = osBean.getTotalPhysicalMemorySize();
        long freeMemory = osBean.getFreePhysicalMemorySize();
        long usedMemory = totalMemory - freeMemory;

        return usedMemory; // Free system memory in bytes
    }

    public static long getFreeRam() {
        OperatingSystemMXBean osBean = ManagementFactory.getPlatformMXBean(OperatingSystemMXBean.class);
        long freeMemory2 = osBean.getFreePhysicalMemorySize();

        return (freeMemory2 / (1024 * 1024));
    }
}

