package com.vaibhav.saas_billing_api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
@SpringBootApplication
@CrossOrigin(origins = "http://localhost:5173")
@RestController // Ye banata hai isko ek Web API
public class SaasBillingApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(SaasBillingApiApplication.class, args);
	}

	// Tera pehla Live API Endpoint

// 1. Apne Database Manager (Munshi) ko class mein bulaya
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private EmailService emailService;
	// 2. Database mein data SAVE karne ka endpoint (Test karne ke liye)
	@GetMapping("/api/add")
	public String addTestUser() {
		// Naya user banaya
		User nayaUser = new User("Vaibhav Boss", "boss@saas.com", "Enterprise Tier");

		// Munshi ko bola isko godaam (Postgres) mein save kar do!
		userRepository.save(nayaUser);

		return "Badhai ho bhai! Tera pehla data PostgreSQL godaam mein permanently save ho gaya!";
	}

	// 3. Database se saara data FETCH karne ka endpoint
	@GetMapping("/api/users")
	public List<User> getRealUsers() {
		// Munshi ko bola godaam se saara data nikal kar laao
		return userRepository.findAll();
	}
	/**
	 * Creates a new user in the system and triggers a welcome email notification.
	 *
	 * @param nayaUser The user payload from the incoming HTTP request.
	 * @return The persisted User entity.
	 */
	@PostMapping("/api/users")
	public User createRealUser(@RequestBody User nayaUser) {

		// Persist the user data to PostgreSQL
		User savedUser = userRepository.save(nayaUser);

		// Dispatch welcome email notification to the newly registered user
		emailService.sendWelcomeEmail(savedUser.getEmail(), savedUser.getName());

		return savedUser;
	}
	// 6. User ko godaam se hamesha ke liye hatana (Delete)
	@DeleteMapping("/api/users/{id}")
	public String deleteUser(@PathVariable Long id) {
		// Munshi ko bolo is id wale user ko delete kar de
		userRepository.deleteById(id);
		return "User jiska ID " + id + " tha, wo successfully delete ho gaya!";
	}

	@PutMapping("/api/users/{id}")
	public User updateUser(@PathVariable Long id, @RequestBody User userDetails) {
		// Safely fetch the existing user; throw a runtime exception if the record is absent
		User existingUser = userRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("User not found with id: " + id));

		// Update the entity fields with the incoming request payload
		existingUser.setName(userDetails.getName());
		existingUser.setEmail(userDetails.getEmail());
		existingUser.setActivePlan(userDetails.getActivePlan());

		// Persist the updated entity state to the PostgreSQL database
		return userRepository.save(existingUser);
	}
}
