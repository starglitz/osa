package com.ftn.osa;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class OsaApplication {
	static Logger log = Logger.getLogger(OsaApplication.class.getName());

	public static void main(String[] args) {
		BasicConfigurator.configure();
		SpringApplication.run(OsaApplication.class, args);
		log.info("Started server");
	}

}
