package fontys.s3.Carspacebackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;
import java.util.Date;
import java.util.TimeZone;


@SpringBootApplication
public class CarspaceBackendApplication {
	@PostConstruct
	void started() {
		TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
		System.out.println("Spring boot application running in timezone :"+ new Date());
	}
	public static void main(String[] args) {
		SpringApplication.run(CarspaceBackendApplication.class, args);
	}

}

