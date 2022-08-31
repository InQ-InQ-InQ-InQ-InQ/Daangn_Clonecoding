package team1.Daangn_Clonecoding;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class DaangnClonecodingApplication {

	public static void main(String[] args) {
		SpringApplication.run(DaangnClonecodingApplication.class, args);
	}

}
