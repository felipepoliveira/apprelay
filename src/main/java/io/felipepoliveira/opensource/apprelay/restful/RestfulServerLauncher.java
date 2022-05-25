package io.felipepoliveira.opensource.apprelay.restful;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;

@SpringBootApplication
@ComponentScans({
	@ComponentScan("io.felipepoliveira.opensource.apprelay.app"),
	@ComponentScan("io.felipepoliveira.opensource.apprelay.restful"),
})
public class RestfulServerLauncher {

	public static void main(String[] args) {
		SpringApplication.run(RestfulServerLauncher.class, args);
	}

}
