package com.example.recetas.recetas;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class })
public class RecetasApplication {

	public static void main(String[] args) {
		SpringApplication.run(RecetasApplication.class, args);
	}

}
