package model;

import oshi.SystemInfo;
import oshi.hardware.CentralProcessor;
import oshi.hardware.Sensors;

public class Shared {
    public static final SystemInfo SYSTEM_INFO = new SystemInfo();
    public static final Sensors SENSORS = SYSTEM_INFO.getHardware().getSensors();
    public static final CentralProcessor CENTRAL_PROCESSOR = SYSTEM_INFO.getHardware().getProcessor();
}
