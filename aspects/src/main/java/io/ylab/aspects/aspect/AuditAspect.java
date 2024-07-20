package io.ylab.aspects.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.context.annotation.Configuration;

@Aspect
@Configuration
@Slf4j
public class AuditAspect {

    @Before("execution(* com.ylab.*Service.*(..)) && args(username,..)")
    public void auditUserAction(String username) {
        log.info("User action by: {}", username);
    }
}
