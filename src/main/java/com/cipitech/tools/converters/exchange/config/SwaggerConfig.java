package com.cipitech.tools.converters.exchange.config;

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

@Slf4j
@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "swagger")
public class SwaggerConfig
{
	private Boolean     displayRequestDuration;
	private String      version;
	private String      title;
	private String      description;
	private ContactInfo contact;

	@Value("${server.servlet.context-path}")
	private String contextPath;

	public SwaggerConfig()
	{
		log.debug("SwaggerConfig Loaded...");
	}

	@Bean
	public OpenAPI cipitechOpenAPI()
	{
		return new OpenAPI()
				.info(apiInfo())
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
		Contact contact = new Contact().name(getContact().getName());

		if (getContact().getWebsite() != null)
		{
			contact.url(getContact().getWebsite());
		}
		else
		{
			contact.email(getContact().getEmail());
		}

		return contact;
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
