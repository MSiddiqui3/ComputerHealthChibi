package model;

import oshi.SystemInfo;
import oshi.hardware.GraphicsCard;
import oshi.hardware.HardwareAbstractionLayer;
import oshi.hardware.Sensors;

import java.util.List;


public class GpuInfo {

    public static String getGpuInfo() {
        SystemInfo systemInfo = new SystemInfo();
        List<GraphicsCard> gpus = systemInfo.getHardware().getGraphicsCards();
        StringBuilder gpuInfo = new StringBuilder("GPU Information:\n");
        for (GraphicsCard gpu : gpus) {
            gpuInfo.append("GPU Name: ").append(gpu.getName()).append("\n");
            gpuInfo.append("VRAM: ").append(gpu.getVRam() / (1024 * 1024)).append(" MB\n");
        }
        return gpuInfo.toString();
    }
}
