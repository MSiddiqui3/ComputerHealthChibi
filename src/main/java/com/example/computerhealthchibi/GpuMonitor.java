package com.example.computerhealthchibi;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class GpuMonitor {

    public static String getGpuUsage() {
        StringBuilder output = new StringBuilder();
        try {
            Process process = Runtime.getRuntime().exec("nvidia-smi --query-gpu=utilization.gpu,memory.total,memory.used --format=csv,noheader");
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                output.append(line).append("\n");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return output.toString();
    }

    public static void main(String[] args) {
        System.out.println("GPU Usage:\n" + getGpuUsage());
    }
}
