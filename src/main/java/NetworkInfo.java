import oshi.SystemInfo;
import oshi.hardware.HardwareAbstractionLayer;
import oshi.hardware.NetworkIF;


import java.util.List;

public class NetworkInfo {

    public static String getNetworkInfo() {
        SystemInfo systemInfo = new SystemInfo();
        HardwareAbstractionLayer hal = systemInfo.getHardware();
        StringBuilder networkDetails = new StringBuilder();
        List<NetworkIF> networkIFs = hal.getNetworkIFs();

        for (NetworkIF net : networkIFs) {
            net.updateAttributes();
            networkDetails.append("Interface: ").append(net.getDisplayName()).append("\n")
                    .append("Bytes Received: ").append(net.getBytesRecv()).append(" bytes\n")
                    .append("Bytes Sent: ").append(net.getBytesSent()).append(" bytes\n")
                    .append("Packets Received: ").append(net.getPacketsRecv()).append("\n")
                    .append("Packets Sent: ").append(net.getPacketsSent()).append("\n")
                    .append("-----------------------------------\n");
        }

        return networkDetails.toString();
    }
}
