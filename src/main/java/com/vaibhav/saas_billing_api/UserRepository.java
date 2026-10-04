package com.vaibhav.saas_billing_api;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// Ye hamara Database Manager hai
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

}
