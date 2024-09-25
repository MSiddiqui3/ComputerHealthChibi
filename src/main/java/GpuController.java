import com.example.computerhealthchibi.GpuMonitor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.InputStreamReader;

@RestController
public class GpuController {

    @GetMapping("/gpu")
    public String getGpuInfo() {
        return GpuMonitor.getGpuUsage();
    }
}