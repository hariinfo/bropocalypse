package com.statement.commerce.context;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

@Configuration
@Profile("LocalEnv")
@SuppressWarnings("UtilityClassWithoutPrivateConstructor")
public class LocalProfile
{
  private static final String FILE_NAME = "local-env.properties";

  // needed for spring
  public LocalProfile() {}

  @Bean
  public static PropertySourcesPlaceholderConfigurer properties()
  {
    PropertySourcesPlaceholderConfigurer pspc      = new PropertySourcesPlaceholderConfigurer();
    Resource[]                           resources = { new ClassPathResource(FILE_NAME) };

    pspc.setLocations(resources);
    pspc.setIgnoreUnresolvablePlaceholders(true);

    return pspc;
  }

}
