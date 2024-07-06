package com.ylab.aspect;

import org.aspectj.lang.Aspects;
import org.aspectj.lang.annotation.Aspect;

@Aspect
public class AspectConfig {
    public AspectConfig() {
        Aspects.aspectOf(AuditAspect.class);
        Aspects.aspectOf(LoggingAspect.class);
    }
}
