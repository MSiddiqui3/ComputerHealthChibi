package model;

import javafx.beans.property.ReadOnlyLongProperty;
import javafx.beans.property.ReadOnlyLongWrapper;
import javafx.beans.property.ReadOnlyStringProperty;
import javafx.beans.property.ReadOnlyStringWrapper;
import oshi.hardware.HWDiskStore;

import java.util.LinkedList;
import java.util.List;

public class DiscObj {

    private HWDiskStore OshiDisk = null;


    // Disc Lists
    private static final List<HWDiskStore> diskStores = Shared.HARDWARE_ABSTRACTION_LAYER.getDiskStores();
    public static final List<DiscObj> LIST = new LinkedList<>();


    // CREATE DISC OBJECTS
    // runs once at class creation
    static {
        for (HWDiskStore OshiDisk : diskStores) {
            LIST.add(new DiscObj(OshiDisk));
        }
    }


    // JAVAFX PROPERTIES
    private final ReadOnlyStringWrapper name = new ReadOnlyStringWrapper();
    private final ReadOnlyStringWrapper serial = new ReadOnlyStringWrapper();
    private final ReadOnlyLongWrapper size = new ReadOnlyLongWrapper();
    private final ReadOnlyLongWrapper reads = new ReadOnlyLongWrapper();
    private final ReadOnlyLongWrapper writes = new ReadOnlyLongWrapper();
    private final ReadOnlyLongWrapper freeSpace = new ReadOnlyLongWrapper();


    // CONSTRUCTOR
    public DiscObj(HWDiskStore OshiDisk) {
        this.OshiDisk = OshiDisk;
        update();
    }


    // JAVAFX PROPERTY GETTERS
    public ReadOnlyStringProperty nameProperty() {
        return name.getReadOnlyProperty();
    }

    public ReadOnlyStringProperty serialProperty() {
        return serial.getReadOnlyProperty();
    }

    public ReadOnlyLongProperty sizeProperty() {
        return size.getReadOnlyProperty();
    }

    public ReadOnlyLongProperty readsProperty() {
        return reads.getReadOnlyProperty();
    }

    public ReadOnlyLongProperty writesProperty() {
        return writes.getReadOnlyProperty();
    }

    public ReadOnlyLongProperty freeSpaceProperty() {
        return freeSpace.getReadOnlyProperty();
    }


    // UPDATE JAVAFX PROPERTIES
    public void update() {
        OshiDisk.updateAttributes();
        String name = OshiDisk.getModel();
        String serial = OshiDisk.getSerial();
        long size = OshiDisk.getSize();
        long reads = OshiDisk.getReads();
        long writes = OshiDisk.getWrites();
        long freeSpace = OshiDisk.getWriteBytes();

        this.name.set(name);
        this.serial.set(serial);
        this.size.set(size);
        this.reads.set(reads);
        this.writes.set(writes);
        this.freeSpace.set(freeSpace);
    }

}
