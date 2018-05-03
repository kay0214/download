package com.sandman.download.configuration.startup;

import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextRefreshedEvent;

/**
 * Created by renhuiyu on 2018/1/16.
 */
@Configuration
public class InitAfterStartup implements ApplicationListener<ContextRefreshedEvent> {
    // tomcat启动后执行的操作
    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {

    }
}
