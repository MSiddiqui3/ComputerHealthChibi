import java.lang.management.ManagementFactory;


import com.sun.management.OperatingSystemMXBean;
import oshi.hardware.GlobalMemory;

public class RamInfo  {

    public static String getRamInfo() {
        OperatingSystemMXBean osBean = ManagementFactory.getPlatformMXBean(OperatingSystemMXBean.class);
        long totalMemory = osBean.getTotalPhysicalMemorySize();
        long freeMemory = osBean.getFreePhysicalMemorySize();
        long usedMemory = totalMemory - freeMemory;


        return "RAM Information:\n" +
                "Total RAM: " + (totalMemory / (1024 * 1024)) + " MB\n" +
                "Used RAM: " + (usedMemory / (1024 * 1024)) + " MB\n" +
                "Free RAM: " + (freeMemory / (1024 * 1024)) + " MB";
    }

    private static final OperatingSystemMXBean osBean =
            (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();

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

