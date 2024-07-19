package io.ylab.aspects.configAspect;


import io.ylab.aspects.aspect.LoggingAspect;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class LoggingAutoConfiguration {

    @Autowired
    private final LoggingAspect loggingAspect;

}
