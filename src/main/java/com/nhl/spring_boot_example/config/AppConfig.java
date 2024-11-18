package com.nhl.spring_boot_example.config;

import com.nhl.spring_boot_example.util.AnotherBean;
import com.nhl.spring_boot_example.util.ExampleBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Methodes in deze klasse met de annotatie @Bean worden door Spring uitgevoerd.
 * De return values komen in de Application Context.
 * <p>
 * De @EnableScheduling zorgt ervoor dat we de @Scheduled annotatie kunnen gebruiken.
 */
@Configuration
@EnableScheduling
public class AppConfig {

    @Bean
    public ExampleBean exampleBean() {
        ExampleBean exampleBean = new ExampleBean();
        // Some elaborate configuration
        return exampleBean;
    }

    @Bean
    public AnotherBean anotherBean(ExampleBean exampleBean) {
        AnotherBean anotherBean = new AnotherBean(exampleBean);
        // Another elaborate configuration
        return anotherBean;
    }

}




