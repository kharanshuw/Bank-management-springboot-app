package com.bankapplication.config;

import com.bankapplication.auditconfig.ApplicationAudit;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;

@Configuration
public class ApplicationConfig {


    @Bean
    public AuditorAware<Integer> auditorAware() {
        return new ApplicationAudit();
    }

}
