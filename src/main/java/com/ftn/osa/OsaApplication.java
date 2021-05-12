package com.ftn.osa;

import com.ftn.osa.model.entity.Order;
import com.ftn.osa.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

@SpringBootApplication
public class OsaApplication {

	public static void main(String[] args) {
		SpringApplication.run(OsaApplication.class, args);
	}

}
