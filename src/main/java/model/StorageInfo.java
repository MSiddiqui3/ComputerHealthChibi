package model;

import oshi.hardware.HWDiskStore;
import oshi.hardware.HardwareAbstractionLayer;

import java.util.LinkedList;
import java.util.List;

public class StorageInfo {

    // SHARED OBJECTS
    private static final HardwareAbstractionLayer HAL = Shared.HARDWARE_ABSTRACTION_LAYER;


    // NO update() method needed, viewModel.update() updates DiscObj's


    public static String getStorageInfo() {
        StringBuilder storageDetails = new StringBuilder();
        List<HWDiskStore> diskStores = HAL.getDiskStores();

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
