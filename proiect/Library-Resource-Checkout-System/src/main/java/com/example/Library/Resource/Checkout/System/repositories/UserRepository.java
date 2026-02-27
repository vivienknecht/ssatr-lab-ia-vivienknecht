package com.example.Library.Resource.Checkout.System.repositories;

import com.example.Library.Resource.Checkout.System.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
