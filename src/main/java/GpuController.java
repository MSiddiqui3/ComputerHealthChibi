import com.example.computerhealthchibi.GpuMonitor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

@RestController
public class GpuController {

    @GetMapping("/gpu")
    public ResponseEntity<Map<String, Object>> getGpuInfo() {
        try {
            String gpuInfo = GpuMonitor.getGpuUsage();
            Map<String, Object> response = new HashMap<>();
            response.put("gpuUsage", gpuInfo);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}