import oshi.SystemInfo;
import oshi.hardware.GraphicsCard;
import oshi.hardware.HardwareAbstractionLayer;

import java.util.List;

public class GpuInfo {

    public static class GpuOshiMonitor {
        public static List<GraphicsCard> getGraphicsCards() {
            SystemInfo systemInfo = new SystemInfo();
            return systemInfo.getHardware().getGraphicsCards();
        }

        public static void main(String[] args) {
            List<GraphicsCard> gpus = getGraphicsCards();
            for (GraphicsCard gpu : gpus) {
                System.out.println("GPU Name: " + gpu.getName());
                System.out.println("GPU Vendor: " + gpu.getVendor());
                System.out.println("GPU VRAM: " + gpu.getVRam() / (1024 * 1024) + " MB");
            }
        }
    }
}
