package com.vaibhav.saas_billing_api;
import jakarta.persistence.*;

@Entity
@Table(name = "saas_users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String email;
    private String activePlan;

    // 1. Khali Constructor (Database ko table padhne ke liye ye chahiye hi hota hai)
    public User() {
    }

    // 2. Tera purana Constructor (Isme humne id hata di hai kyunki database wo khud banayega)
    public User(String name, String email, String activePlan) {
        this.name = name;
        this.email = email;
        this.activePlan = activePlan;
    }

    // --- Getters & Setters ---
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getActivePlan() { return activePlan; }
    public void setActivePlan(String activePlan) { this.activePlan = activePlan; }
}
