package br.com.mounts;

import br.com.mounts.infrascruture.YamlPropertySourceFactory;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories
@ComponentScan(basePackages = {"br.com.mounts"})
@EntityScan
@PropertySource(value = ModuleLoader.DEFAULT_PROPERTIES, factory = YamlPropertySourceFactory.class)
@PropertySource(
    value = ModuleLoader.PROFILE_PROPERTIES,
    ignoreResourceNotFound = true,
    factory = YamlPropertySourceFactory.class)
public class ModuleLoader {

  /** Nome padrão do arquivo properties. */
  static final String DEFAULT_PROPERTIES = "classpath:application_core.yml";

  /** Nome customizado do arquivo properties. Conforme o profile do Spring. */
  static final String PROFILE_PROPERTIES =
      "classpath:application_core-${spring.profiles.active}.yml";
}
