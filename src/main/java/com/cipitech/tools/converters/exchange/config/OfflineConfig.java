package com.cipitech.tools.converters.exchange.config;

import com.cipitech.tools.converters.exchange.utils.Globals;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Slf4j
@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = Globals.Profiles.OFFLINE)
@Profile(Globals.Profiles.OFFLINE)
public class OfflineConfig
{
	public OfflineConfig()
	{
		log.debug("OfflineConfig Loaded...");
	}
}
