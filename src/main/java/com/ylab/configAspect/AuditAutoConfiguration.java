package com.ylab.configAspect;

import com.ylab.aspect.AuditAspect;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

@Configuration
public class AuditAutoConfiguration {

    @Bean
    @Lazy
    public AuditAspect auditAspect() {
        return new AuditAspect();
    }

}
