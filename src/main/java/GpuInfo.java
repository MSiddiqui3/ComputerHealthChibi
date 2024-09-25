import oshi.SystemInfo;
import oshi.hardware.GraphicsCard;
import oshi.hardware.HardwareAbstractionLayer;

import java.util.List;

public class GpuInfo {

    public static void main(String[] args) {
        // Initialize SystemInfo object
        SystemInfo systemInfo = new SystemInfo();

        // Get hardware abstraction layer
        HardwareAbstractionLayer hardware = systemInfo.getHardware();

        // Get a list of all graphics cards (GPUs)
        List<GraphicsCard> graphicsCards = hardware.getGraphicsCards();

        // Loop through each GPU and print its details
        for (GraphicsCard gpu : graphicsCards) {
            System.out.println("GPU Name: " + gpu.getName());
            System.out.println("GPU Vendor: " + gpu.getVendor());
            System.out.println("GPU Version: " + gpu.getVersionInfo());
            System.out.println("GPU VRAM: " + gpu.getVRam() / (1024 * 1024) + " MB");
            System.out.println("-----------------------------");
        }
    }
}
