package com.example.Library.Resource.Checkout.System.controller;

import com.example.Library.Resource.Checkout.System.model.Reservation;
import com.example.Library.Resource.Checkout.System.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reservations")
@CrossOrigin(origins = "http://localhost:5173")
public class ReservationController {
    private final ReservationService reservationService;

    @Autowired
    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @GetMapping("/getAllReservations")
    public ResponseEntity<List<Reservation>> getAllReservations() {
        List<Reservation> reservations = reservationService.getAllReservations();
        return ResponseEntity.ok(reservations);
    }

    @GetMapping("/getReservation/{id}")
    public ResponseEntity<Reservation> getReservation(@PathVariable Long id) {
        Reservation reservation = reservationService.getReservation(id);
        return ResponseEntity.ok(reservation);
    }

    @PostMapping("/addReservation")
    public ResponseEntity<Reservation> addReservation(@RequestBody Reservation reservation) {
        Reservation addedReservation = reservationService.addReservation(reservation);
        return ResponseEntity.ok(addedReservation);
    }

    @DeleteMapping("deleteReservation")
    public ResponseEntity<Reservation> deleteReservation(@PathVariable Long id) {
        reservationService.deleteReservation(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/createReservation/{bookId}/user/{userId}")
    public Reservation createReservation(@PathVariable Long bookId, @PathVariable Long userId) {
        return reservationService.createReservation(bookId, userId);
    }

    @GetMapping("/book/{bookId}")
    public List<Reservation> getReservationsForBook(@PathVariable Long bookId) {
        return reservationService.getReservationsForBook(bookId);
    }

    @GetMapping("/book/{bookId}/user/{userId}")
    public Reservation getUserReservation(@PathVariable Long bookId, @PathVariable Long userId) {
        return reservationService.getReservationsForBook(bookId)
                .stream()
                .filter(r -> r.getUser().getId().equals(userId))
                .findFirst()
                .orElse(null);
    }
}
