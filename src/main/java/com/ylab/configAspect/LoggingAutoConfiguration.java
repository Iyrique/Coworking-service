package com.ylab.configAspect;

import com.ylab.aspect.LoggingAspect;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class LoggingAutoConfiguration {

    @Autowired
    private final LoggingAspect loggingAspect;

}
