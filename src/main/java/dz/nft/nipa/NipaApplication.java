package dz.nft.nipa;

import javax.servlet.http.HttpSessionListener;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import dz.nft.nipa.configuration.SessionListener;

@SpringBootApplication
public class NipaApplication {

	public static void main(String[] args) {
		SpringApplication.run(NipaApplication.class, args);
	}

	@Bean
	public HttpSessionListener httpSessionListener() {
		return new SessionListener();
	}

}
