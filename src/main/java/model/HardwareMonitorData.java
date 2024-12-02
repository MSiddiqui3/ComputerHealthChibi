package model;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;


public class HardwareMonitorData {
    private JsonObject jsonData;

    //METHOD TO GRAB THE INFORMATION FROM HARDWARE MONITOR AS A JSON
    public void HardwareMonitorJson() {
        try {
            URL url = new URL("http://localhost:8085/data.json");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");


            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String inputLine;
            StringBuilder content = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            in.close();
            conn.disconnect();

            //DEBUGGING PRINTS THE JSON IN THE CONSOLE JUST IN CASE
            System.out.println("Raw JSON Data: " + content.toString());


            JsonElement jsonElement = JsonParser.parseString(content.toString());
            this.jsonData = jsonElement.getAsJsonObject();

            //CHECKS IF THE JSON HAS BEEN MADE CORRECTLY
            if (this.jsonData != null) {
                System.out.println("JSON Data initialized successfully.");
            } else {
                System.out.println("Failed to initialize JSON Data.");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //METHOD WHICH IS CALLED BY THE MAIN CLASS
    public String getGpuTemperature() {
        HardwareMonitorJson();
        if (jsonData == null) {
            System.out.println("Error: jsonData is null. Unable to retrieve GPU temperature.");
            return "N/A";
        }

        try {
            return searchForInfoInCategory(jsonData, "Temperatures", "GPU Core");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "N/A";
    }

    public String getGpuFanSpeed() {
        HardwareMonitorJson();
        if (jsonData == null) {
            System.out.println("Error: jsonData is null. Unable to retrieve GPU temperature.");
            return "N/A";
        }

        try {
            return searchForInfoInCategory(jsonData, "Fans", "GPU");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "N/A";
    }

    public String getCpuVoltage() {
        HardwareMonitorJson();
        if (jsonData == null) {
            System.out.println("Error: jsonData is null. Unable to retrieve GPU temperature.");
            return "N/A";
        }

        try {
            return searchForInfoInCategory(jsonData, "Voltages", "CPU VCore");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "N/A";
    }

    public String getCpuUsage() {
        HardwareMonitorJson();
        if (jsonData == null) {
            System.out.println("Error: jsonData is null. Unable to retrieve GPU temperature.");
            return "N/A";
        }

        try {
            return searchForInfoInCategory(jsonData, "Load", "CPU Total");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "N/A";
    }


  //SEARCHES FOR GPU CORE TEMPERATURES INSIDE THE JSON
    private String searchForInfoInCategory(JsonObject jsonObject, String category, String keyword) {
        //LOOK FOR THE CATEGORY FIRST
        if (jsonObject.has("Text") && jsonObject.get("Text").getAsString().contains(category)) {
            for (JsonElement child : jsonObject.getAsJsonArray("Children")) {
                JsonObject childObj = child.getAsJsonObject();
                if (childObj.get("Text").getAsString().contains(keyword) && childObj.has("Value")) {
                    return childObj.get("Value").getAsString();
                }
            }
        }

        if (jsonObject.has("Children")) {
            for (JsonElement child : jsonObject.getAsJsonArray("Children")) {
                JsonObject childObj = child.getAsJsonObject();
                String result = searchForInfoInCategory(childObj, category, keyword);
                if (!result.equals("N/A")) {
                    return result;
                }
            }
        }

        return "N/A";
    }

}