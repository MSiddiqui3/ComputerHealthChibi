import java.lang.management.ManagementFactory;
import com.sun.management.OperatingSystemMXBean;

public class RAMMonitor {

    public static void main(String[] args) {
        // Get the OperatingSystemMXBean from ManagementFactory
        OperatingSystemMXBean osBean = ManagementFactory.getPlatformMXBean(OperatingSystemMXBean.class);

        // Get the total physical memory size (RAM) in bytes
        long totalMemory = osBean.getTotalPhysicalMemorySize();

        // Get the available/free physical memory size in bytes
        long freeMemory = osBean.getFreePhysicalMemorySize();

        // Calculate used memory
        long usedMemory = totalMemory - freeMemory;

        // Convert bytes to megabytes
        long usedMemoryInMB = usedMemory / (1024 * 1024);
        long totalMemoryInMB = totalMemory / (1024 * 1024);

        // Display the results
        System.out.println("Total RAM: " + totalMemoryInMB + " MB");
        System.out.println("Used RAM: " + usedMemoryInMB + " MB");
        System.out.println("Free RAM: " + (totalMemoryInMB - usedMemoryInMB) + " MB");
    }
}
