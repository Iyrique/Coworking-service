package com.ylab.aspect;

import org.aspectj.lang.Aspects;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class AspectConfig {
    public AspectConfig() {
        Aspects.aspectOf(AuditAspect.class);
        Aspects.aspectOf(LoggingAspect.class);
    }
}
