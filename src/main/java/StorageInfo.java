import oshi.SystemInfo;
import oshi.hardware.HWDiskStore;
import oshi.hardware.HardwareAbstractionLayer;

import java.util.List;

public class StorageInfo {
    public static String getStorageInfo() {
        SystemInfo systemInfo = new SystemInfo();
        HardwareAbstractionLayer hal = systemInfo.getHardware();
        StringBuilder storageDetails = new StringBuilder();
        List<HWDiskStore> diskStores = hal.getDiskStores();

        for (HWDiskStore disk : diskStores) {
            storageDetails.append("Drive: ").append(disk.getModel()).append("\n")
                    .append("Serial: ").append(disk.getSerial()).append("\n")
                    .append("Size: ").append(disk.getSize() / (1024 * 1024 * 1024)).append(" GB\n")
                    .append("Reads: ").append(disk.getReads()).append("\n")
                    .append("Writes: ").append(disk.getWrites()).append("\n")
                    .append("Free Space: ").append(disk.getWriteBytes() / (1024 * 1024 * 1024)).append(" GB\n")
                    .append("-----------------------------------\n");
        }
        return storageDetails.toString();

    }
}
