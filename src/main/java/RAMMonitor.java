import java.lang.management.ManagementFactory;
import java.util.HashMap;
import java.util.Map;

import com.sun.management.OperatingSystemMXBean;

public class RAMMonitor {
    public static Map<String, Long> getMemoryUsage() {
        OperatingSystemMXBean osBean = ManagementFactory.getPlatformMXBean(OperatingSystemMXBean.class);
        long totalMemory = osBean.getTotalPhysicalMemorySize();
        long freeMemory = osBean.getFreePhysicalMemorySize();
        long usedMemory = totalMemory - freeMemory;

        Map<String, Long> memoryStats = new HashMap<>();
        memoryStats.put("totalMemoryMB", totalMemory / (1024 * 1024));
        memoryStats.put("usedMemoryMB", usedMemory / (1024 * 1024));
        memoryStats.put("freeMemoryMB", freeMemory / (1024 * 1024));
        return memoryStats;
    }

    public static void main(String[] args) {
        Map<String, Long> memoryStats = getMemoryUsage();
        System.out.println("Total RAM: " + memoryStats.get("totalMemoryMB") + " MB");
        System.out.println("Used RAM: " + memoryStats.get("usedMemoryMB") + " MB");
        System.out.println("Free RAM: " + memoryStats.get("freeMemoryMB") + " MB");
    }
}

