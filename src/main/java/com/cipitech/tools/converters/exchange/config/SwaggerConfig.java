package com.cipitech.tools.converters.exchange.config;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.properties.SwaggerUiConfigProperties;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.util.ArrayList;
import java.util.List;

/**
 * Here we perform the initialization and configuration of the Swagger UI.
 * It represents also the Spring boot properties that are needed for swagger to work.
 */

@Slf4j
@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "swagger")
public class SwaggerConfig
{
	private Boolean     displayRequestDuration; // Whether to enable or disable the functionality of swagger UI that shows how many milliseconds the API took to respond.
	private String      version; // The version of our application
	private String      title; // The name of our Rest API
	private String      description; // A brief description of our Rest API
	private ContactInfo contact; // The contact information displayed in the "Contact" section of Swagger UI

	@Value("${server.servlet.context-path}")
	private String contextPath; // Our web applications' context path in tomcat. It is needed by swagger so that it can create the rest calls.

	public SwaggerConfig()
	{
		log.debug("SwaggerConfig Loaded...");
	}

	@Bean
	public OpenAPI cipitechOpenAPI()
	{
		return new OpenAPI()
				.info(apiInfo())
				.externalDocs(externalDocs())
				.servers(servers());
	}

	private List<Server> servers()
	{
		List<Server> serverList = new ArrayList<>();

		serverList.add(new Server()
				.url(getContextPath())
				.description("Default Server URL"));

		return serverList;
	}

	private Info apiInfo()
	{
		return new Info()
				.title(getTitle())
				.description(getDescription())
				.version(getVersion())
				.license(new License().name("Apache 2.0 License").url("https://www.apache.org/licenses/LICENSE-2.0.html"))
				.contact(contactInfo());
	}

	private Contact contactInfo()
	{
		return new Contact()
				.name("the developer")
				.email(getContact().getEmail());
	}

	private ExternalDocumentation externalDocs()
	{
		return new ExternalDocumentation().description(getContact().getName()).url(getContact().getWebsite());
	}

	@Primary
	@Bean
	public SwaggerUiConfigProperties swaggerUiConfig(SwaggerUiConfigProperties config)
	{
		config.setDisplayRequestDuration(getDisplayRequestDuration());

		return config;
	}

	@Getter
	@Setter
	public static class ContactInfo
	{
		private String name;
		private String email;
		private String website;
	}
}
