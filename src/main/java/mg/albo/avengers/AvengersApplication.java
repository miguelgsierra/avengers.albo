package mg.albo.avengers;

import java.time.Duration;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class AvengersApplication {

	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
		return builder.setConnectTimeout(Duration.ofMillis(10000)).setReadTimeout(Duration.ofMillis(10000)).build();
	}

	public static void main(String[] args) {
		SpringApplication.run(AvengersApplication.class, args);
	}

}
