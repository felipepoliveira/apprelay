package io.felipepoliveira.opensource.apprelay.restful.config;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.annotation.PostConstruct;

import org.springframework.boot.web.server.ConfigurableWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.context.annotation.Configuration;

import io.felipepoliveira.opensource.apprelay.AppRelayLauncher;
import io.felipepoliveira.opensource.apprelay.utils.FileUtils;

@Configuration
public class ServletContainerConfigurations implements WebServerFactoryCustomizer<ConfigurableWebServerFactory>{
	
	private Properties configurations;
	
	private InputStream getServerConfigurationFileInputStream() throws FileNotFoundException {
		switch (AppRelayLauncher.getContext()) {
		case DEBUG:
			return ServletContainerConfigurations.class.getResourceAsStream("/debug/conf/server.properties");
		case PRODUCTION:
			return new FileInputStream(FileUtils.getFileRelativeToJarLocation("../conf/server.properties"));
		default:
			throw new IllegalArgumentException("Unknown application state: " + AppRelayLauncher.getContext());
		}
	}
	
	@PostConstruct
	void loadConfigurations() throws FileNotFoundException, IOException {
		configurations = new Properties();
		configurations.load(getServerConfigurationFileInputStream());
		System.err.println("Successfully loaded server.properties");
		System.out.println(configurations);
	}
	
	/**
	 * Return the 'http.port' value from server.properties configuration file
	 * @return
	 */
	public int getPort() {
		var conf = configurations.get("http.port");
		if (conf == null) {
			throw new RuntimeException("Could not find required configuration 'http.port' on server.properties file");
		}
		return Integer.parseInt(conf.toString());
	}

	@Override
	public void customize(ConfigurableWebServerFactory factory) {
		factory.setPort(getPort());
	}

}
