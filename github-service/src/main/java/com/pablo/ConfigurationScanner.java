package com.pablo;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan({"com.pablo.config","com.pablo.controllers","com.pablo.service","com.pablo.repositories"})
@EntityScan("com.pablo.model")
public class ConfigurationScanner {
}
