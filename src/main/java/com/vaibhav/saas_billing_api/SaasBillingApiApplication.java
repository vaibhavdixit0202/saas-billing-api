package com.vaibhav.saas_billing_api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
@SpringBootApplication
@RestController // Ye banata hai isko ek Web API
public class SaasBillingApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(SaasBillingApiApplication.class, args);
	}

	// Tera pehla Live API Endpoint

// 1. Apne Database Manager (Munshi) ko class mein bulaya
	@Autowired
	private UserRepository userRepository;

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
	// 4. Asli Professional Tarika (Frontend/Bahar se data receive karne ke liye)
	@PostMapping("/api/users")
	public User createRealUser(@RequestBody User nayaUser) {
		// Jo naya data bahar se aayega, usko direct Postgres godaam mein save kar dega
		return userRepository.save(nayaUser);
	}
	// 5. Kisi existing user ka data UPDATE karne ke liye (Plan Upgrade)
	@PutMapping("/api/users/{id}")
	public User updateUserPlan(@PathVariable Long id, @RequestBody User nayaData) {
		// 1. Pehle id ke hisaab se purane user ko godaam mein dhundo
		User puranaUser = userRepository.findById(id).get();

		// 2. Uska plan naye data se update kar do
		puranaUser.setActivePlan(nayaData.getActivePlan());

		// 3. Wapas godaam mein save kar do
		return userRepository.save(puranaUser);
	}
	// 6. User ko godaam se hamesha ke liye hatana (Delete)
	@DeleteMapping("/api/users/{id}")
	public String deleteUser(@PathVariable Long id) {
		// Munshi ko bolo is id wale user ko delete kar de
		userRepository.deleteById(id);
		return "User jiska ID " + id + " tha, wo successfully delete ho gaya!";
	}
}
