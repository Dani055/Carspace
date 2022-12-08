package fontys.s3.carspacebackend;

import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;
import java.util.Date;
import java.util.TimeZone;
import org.slf4j.Logger;


@SpringBootApplication
public class CarspaceBackendApplication {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	@PostConstruct
	void started() {
		TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
		logger.info("Spring boot application running in timezone {}:", new Date());
	}
	public static void main(String[] args) {
		SpringApplication.run(CarspaceBackendApplication.class, args);
	}

}

