package com.example.Library.Resource.Checkout.System.repositories;

import com.example.Library.Resource.Checkout.System.enums.ReservationStatus;
import com.example.Library.Resource.Checkout.System.model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    List<Reservation> findByBookIdAndReservationStatus(Long bookId, ReservationStatus reservationStatus);
}
