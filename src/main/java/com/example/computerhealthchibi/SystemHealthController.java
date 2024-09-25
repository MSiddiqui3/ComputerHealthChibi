package com.example.computerhealthchibi;

import com.sun.management.OperatingSystemMXBean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.management.ManagementFactory;

@RestController
public class SystemHealthController {

    @GetMapping("/ram-usage")
    public String getRAMUsage() {
        OperatingSystemMXBean osBean = ManagementFactory.getPlatformMXBean(OperatingSystemMXBean.class);

        long totalMemory = osBean.getTotalPhysicalMemorySize();
        long freeMemory = osBean.getFreePhysicalMemorySize();
        long usedMemory = totalMemory - freeMemory;

        long usedMemoryInMB = usedMemory / (1024 * 1024);
        long totalMemoryInMB = totalMemory / (1024 * 1024);

        return "Total RAM: " + totalMemoryInMB + " MB, Used RAM: " + usedMemoryInMB + " MB, Free RAM: " + (totalMemoryInMB - usedMemoryInMB) + " MB";
    }
}
