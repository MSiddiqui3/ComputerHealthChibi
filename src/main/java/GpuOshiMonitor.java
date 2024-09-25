import oshi.SystemInfo;
import oshi.hardware.GraphicsCard;

import java.util.List;

public class GpuOshiMonitor {

    public static void main(String[] args) {
        SystemInfo systemInfo = new SystemInfo();
        List<GraphicsCard> gpus = systemInfo.getHardware().getGraphicsCards();
        for (GraphicsCard gpu : gpus) {
            System.out.println("GPU: " + gpu.getName());
            System.out.println("VRAM: " + gpu.getVRam());
        }
    }
}