package com.example.Library.Resource.Checkout.System.repositories;

import com.example.Library.Resource.Checkout.System.enums.CheckoutStatus;
import com.example.Library.Resource.Checkout.System.model.Checkout;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CheckoutRepository extends JpaRepository<Checkout, Long> {
    List<Checkout> findByUserId(Long userId);

    List<Checkout> findByCheckoutStatus(CheckoutStatus checkoutStatus);
}
