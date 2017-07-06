package ar.com.bank.services;

import java.util.Locale;

import org.springframework.boot.Banner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

@SpringBootApplication
@EnableCaching
public class Application extends WebMvcConfigurerAdapter {

	public static void main(String[] args) {
		new SpringApplicationBuilder().bannerMode(Banner.Mode.OFF).sources(Application.class).run(args);
	}

	@Bean
	public LocaleResolver localeResolver() {
		SessionLocaleResolver sessionLocaleResolver = new SessionLocaleResolver();
		// Set default Locale as AR
		sessionLocaleResolver.setDefaultLocale(new Locale.Builder().setLanguage("es").setRegion("AR").build());
		return sessionLocaleResolver;
	}

	// To intercept all outgoing REST calls to the bank @see
	// AuthTemplateCustomizer.java
	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
		return builder.build();
	}

	// To support custom properties on application.properties and read them
	// using the @Value annotation
	@Bean
	public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
		return new PropertySourcesPlaceholderConfigurer();
	}

	@Bean
	public ResourceBundleMessageSource messageSource() {
		ResourceBundleMessageSource resourceBundleMessageSource = new ResourceBundleMessageSource();
		resourceBundleMessageSource.setBasenames("i18n/messages");
		// name of the resource bundle
		resourceBundleMessageSource.setUseCodeAsDefaultMessage(true);
		return resourceBundleMessageSource;
	}

	// To support URL's with /getFile/filename.extension
	@Override
	public void configurePathMatch(PathMatchConfigurer configurer) {
		configurer.setUseRegisteredSuffixPatternMatch(true);
	}

}