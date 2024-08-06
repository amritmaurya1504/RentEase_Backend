package com.RentEase.RentEase_backend;

import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableEncryptableProperties
public class RentEaseBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(RentEaseBackendApplication.class, args);
	}

}
