package kr.mj.gollaba.common;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthCheckController {

    @GetMapping(value = "/health-check")
    public String healthCheck() {
        return "SUCCESS";
    }

}
