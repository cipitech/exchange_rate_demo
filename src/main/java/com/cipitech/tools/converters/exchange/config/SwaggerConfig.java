package com.cipitech.tools.converters.exchange.config;

import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.License;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springdoc.core.properties.SwaggerUiConfigProperties;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.util.ArrayList;
import java.util.List;

@Configuration
@ConfigurationProperties(prefix = "swagger")
public class SwaggerConfig {
    private static final Logger log = LoggerFactory.getLogger(SwaggerConfig.class);

    private Boolean displayRequestDuration;
    private String version;
    private String title;
    private String description;
    private ContactInfo contact;

    @Value("${server.servlet.context-path}")
    private String contextPath;

    public SwaggerConfig() {
        log.debug("SwaggerConfig Loaded...");
    }

    @Bean
    public OpenAPI cipitechOpenAPI() {
        return new OpenAPI()
                .info(apiInfo())
                .servers(servers());
    }

    private List<Server> servers() {
        List<Server> serverList = new ArrayList<>();

        serverList.add(new Server()
                .url(contextPath)
                .description("Default Server URL"));

        return serverList;
    }

    private Info apiInfo() {
        return new Info()
                .title(getTitle())
                .description(getDescription())
                .version(getVersion())
                .license(new License().name("Apache 2.0 License").url("https://www.apache.org/licenses/LICENSE-2.0.html"))
                .contact(new Contact()
                        .name(getContact().getName())
                        .email(getContact().getEmail())
                        .url(getContact().getWebsite()));
    }

    @Primary
    @Bean
    public SwaggerUiConfigProperties swaggerUiConfig(SwaggerUiConfigProperties config) {
        config.setDisplayRequestDuration(getDisplayRequestDuration());

        return config;
    }

    public Boolean getDisplayRequestDuration() {
        return displayRequestDuration;
    }

    public void setDisplayRequestDuration(Boolean displayRequestDuration) {
        this.displayRequestDuration = displayRequestDuration;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ContactInfo getContact() {
        return contact;
    }

    public void setContact(ContactInfo contact) {
        this.contact = contact;
    }

    public static class ContactInfo {
        private String name;
        private String email;
        private String website;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getWebsite() {
            return website;
        }

        public void setWebsite(String website) {
            this.website = website;
        }
    }
}
