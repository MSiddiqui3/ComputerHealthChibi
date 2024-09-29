package com.example.computerhealthchibi;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import oshi.SystemInfo;
import oshi.hardware.GraphicsCard;

import java.util.List;

@RestController
public class GpuController {

    @GetMapping("/gpu")
    public String getGpuInfo() {
        SystemInfo si = new SystemInfo();
        List<GraphicsCard> graphicsCards = si.getHardware().getGraphicsCards();

        StringBuilder gpuInfo = new StringBuilder("GPU Information:\n");
        for (GraphicsCard gpu : graphicsCards) {
            gpuInfo.append("Name: ").append(gpu.getName()).append("\n")
                    .append("Vendor: ").append(gpu.getVendor()).append("\n")
                    .append("VRAM: ").append(gpu.getVRam()).append(" bytes\n\n");
        }
        return gpuInfo.toString();
    }
}
