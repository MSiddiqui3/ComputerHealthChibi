package model;

import com.sun.management.OperatingSystemMXBean;
import oshi.SystemInfo;
import oshi.hardware.CentralProcessor;
import oshi.hardware.HardwareAbstractionLayer;
import oshi.hardware.Sensors;

import java.lang.management.ManagementFactory;

public class Shared {
    public static final SystemInfo SYSTEM_INFO = new SystemInfo();
    public static final HardwareAbstractionLayer HARDWARE_ABSTRACTION_LAYER = SYSTEM_INFO.getHardware();
    public static final Sensors SENSORS = SYSTEM_INFO.getHardware().getSensors();
    public static final CentralProcessor CENTRAL_PROCESSOR = SYSTEM_INFO.getHardware().getProcessor();

    public static final OperatingSystemMXBean osBean = ManagementFactory.getPlatformMXBean(OperatingSystemMXBean.class);
}
