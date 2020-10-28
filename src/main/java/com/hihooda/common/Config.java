package com.hihooda.common;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration
public class Config {
    @Bean
    public Lisence apache(Environment env) {
        String[] profiles = env.getActiveProfiles();
        boolean isProd = false;
        for(String p : profiles) {
            if(p.equalsIgnoreCase("prod")) {
                isProd = true;
                break;
            }
        }
        if(isProd) {
            return  new ApacheProd();
        } else {
            return  new Apache();
        }
    }
}
