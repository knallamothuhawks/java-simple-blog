package cc.blog.web;

import org.modelmapper.ModelMapper;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableAutoConfiguration
@ComponentScan(basePackages="cc.blog")
public class TestConfig {

	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}
}
