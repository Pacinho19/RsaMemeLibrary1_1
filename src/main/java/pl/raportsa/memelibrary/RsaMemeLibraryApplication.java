package pl.raportsa.memelibrary;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class RsaMemeLibraryApplication {

	public static void main(String[] args) {
		SpringApplication.run(RsaMemeLibraryApplication.class, args);
	}

}
