package com.scaler.bookmyshow;

import com.scaler.bookmyshow.controller.UserController;
import com.scaler.bookmyshow.dtos.SignUpRequestDTO;
import com.scaler.bookmyshow.models.BaseModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class BookmyshowApplication implements CommandLineRunner {

	@Autowired
	private UserController userController;

	public static void main(String[] args) {
		SpringApplication.run(BookmyshowApplication.class, args);
	}


	@Override
	public void run(String... args) throws Exception {
		SignUpRequestDTO signUpRequestDTO = new SignUpRequestDTO();
		signUpRequestDTO.setEmail("anshu@scaler.com");
		signUpRequestDTO.setPassword("anshu@123");
		userController.signUp(signUpRequestDTO);
	}


}
