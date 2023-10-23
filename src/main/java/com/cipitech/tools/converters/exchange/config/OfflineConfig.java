package com.cipitech.tools.converters.exchange.config;

import com.cipitech.tools.converters.exchange.utils.Globals;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

/**
 * Spring boot properties that are used only when the "offline" profile is activated.
 */

@Slf4j
@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = Globals.Profiles.OFFLINE)
@Profile(Globals.Profiles.OFFLINE)
public class OfflineConfig
{
	// The absolute path of the folder that contains the currency file and the exchange rate files (one for each currency).
	// The prefix should be the path where this project is located followed by "/docker/offline_data".
	private String dataPath;
	// The absolute path of the file that contains all the currency information. In our case the file is named currencies.json
	// So the absolute path should be: "this.dataPath" + "/currencies.json"
	private String currenciesFile;
	// The files that contain the exchange rate information should be on a separate folder. In our case this folder is named "rates".
	// The absolute path of the rated folder: "this.dataPath" + "/rates/"
	// Note that we must include the trailing "/" character.
	private String ratesFolder;
	// The suffix of the exchange rates files. Each file should have as name the currency code.
	// The files must be JSON files so the suffix must be .json
	private String ratesFileSuffix;

	public OfflineConfig()
	{
		log.debug("OfflineConfig Loaded...");
	}
}
