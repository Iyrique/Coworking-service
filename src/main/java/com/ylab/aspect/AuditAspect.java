package com.ylab.aspect;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class AuditAspect {

    private static final Logger logger = LoggerFactory.getLogger(AuditAspect.class);

    @Before("execution(* com.ylab.*Service.*(..)) && args(username,..)")
    public void auditUserAction(String username) {
        logger.info("User action by: {}", username);
    }
}
