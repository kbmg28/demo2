package com.example.demo2;

import java.time.Duration;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.github.bucket4j.*;

@RestController
@RequestMapping()
@CrossOrigin(origins = "*")

public class InfoController {

    @GetMapping("/test")
    public String getTest() {
        return "test";
    }
    
    @GetMapping("/j_security_check")
    public String getInfo() {
        return "ok";
    }
}
