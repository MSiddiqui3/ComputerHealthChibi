package model;

import oshi.SystemInfo;
import oshi.hardware.HardwareAbstractionLayer;
import oshi.hardware.PowerSource;

import java.util.List;

public class BatteryInfo {


    public static String getBatteryInfo() {
        SystemInfo systemInfo = new SystemInfo();
        HardwareAbstractionLayer hal = systemInfo.getHardware();

        StringBuilder batteryDetails = new StringBuilder();
        List<PowerSource> powerSources = hal.getPowerSources();

        if (powerSources.isEmpty()) {
            System.out.println("No battery detected.");
            return("No battery detected");
        }

        for (PowerSource battery : powerSources) {
            batteryDetails.append("Battery Name: ").append(battery.getName()).append("\n")
                    .append("Remaining Capacity: ").append(String.format("%.2f", battery.getRemainingCapacityPercent())).append("%\n")
                    .append("Time Remaining: ").append(battery.getTimeRemainingEstimated() / 60).append(" minutes\n")
                    .append("Charging: ").append(battery.isCharging() ? "Yes" : "No").append("\n")
                    .append("Voltage: ").append(battery.getVoltage()).append(" V\n")
                    .append("-----------------------------------\n");
        }

        return batteryDetails.toString();
    }
}