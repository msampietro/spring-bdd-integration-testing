package com.msampietro.springbddintegrationtesting.misc;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
@Getter
public class ApplicationProperties {

    private final Environment environment;
    @Value("${custom.identifier}")
    private String apiIdentifier;

    public ApplicationProperties(Environment environment) {
        this.environment = environment;
    }

    public String getActiveProfile() {
        return environment.getActiveProfiles()[0];
    }

}
