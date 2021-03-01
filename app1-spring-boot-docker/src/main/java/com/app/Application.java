package com.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@EnableCaching
public class Application {

	
	/**
	 * Essa aplicação disponibiliza Spring Boot Actuator
	 * Para acessar: localhost:8080/actuator/info, localhost:8080/actuator/health 
	 * Para monitor por uma interface, pode ser utilizado o Spring Boot Admin (https://codecentric.github.io/spring-boot-admin/current/)
	 * Curso: https://cursos.alura.com.br/course/spring-boot-seguranca-cache-monitoramento/
	 */
	
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

}
