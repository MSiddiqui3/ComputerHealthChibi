import oshi.SystemInfo;
import oshi.hardware.CentralProcessor;
import oshi.hardware.Sensors;




public class CpuInfo {
    public static String getCpuInfo() {
        SystemInfo si = new SystemInfo();
        CentralProcessor cpu = si.getHardware().getProcessor();
        Sensors sensors = si.getHardware().getSensors();


        StringBuilder cpuInfo = new StringBuilder();
        cpuInfo.append("CPU Information:\n");
        cpuInfo.append("Name: ").append(cpu.getProcessorIdentifier().getName()).append("\n");
        cpuInfo.append("Physical Cores: ").append(cpu.getPhysicalProcessorCount()).append("\n");
        cpuInfo.append("Logical Cores: ").append(cpu.getLogicalProcessorCount()).append("\n");

        double temperature = sensors.getCpuTemperature();
        if (temperature > 0) {
            cpuInfo.append("CPU Temperature: ").append(temperature).append(" °C\n");
        } else {
            cpuInfo.append("CPU Temperature: Data not available\n");
        }



        return cpuInfo.toString();
    }
}
