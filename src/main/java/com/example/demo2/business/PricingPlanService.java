package com.example.demo2.business;

import java.time.Duration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Refill;

@Service
public class PricingPlanService {

    private final Map<String, Bucket> cache = new ConcurrentHashMap<>();

    public Bucket resolveBucket(HttpServletRequest request) {
        String ip = clientIp(request);
        return cache.computeIfAbsent(ip, this::newBucket);
    }
    
    public String clientIp(HttpServletRequest request)  {
        String remoteAddr = "";
        if (request != null) {
            remoteAddr = request.getHeader("X-FORWARDED-FOR");
            if (remoteAddr == null || "".equals(remoteAddr)) {
                remoteAddr = request.getRemoteAddr();
            }
        }
        return remoteAddr;
    }
    Bandwidth getLimit() {
        return Bandwidth.classic(1, Refill.intervally(1, Duration.ofMinutes(1)));
    }

    public Bucket newBucket(String clientIp) {
        //PricingPlan pricingPlan = PricingPlan.resolvePlanFromApiKey(ip);
        
        return Bucket.builder()
            .addLimit(getLimit())
            .build();
    }
}