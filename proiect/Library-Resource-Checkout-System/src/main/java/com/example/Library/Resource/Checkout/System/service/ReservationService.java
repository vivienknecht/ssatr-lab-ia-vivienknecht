package com.example.Library.Resource.Checkout.System.service;

import com.example.Library.Resource.Checkout.System.enums.ReservationStatus;
import com.example.Library.Resource.Checkout.System.enums.Role;
import com.example.Library.Resource.Checkout.System.model.Book;
import com.example.Library.Resource.Checkout.System.model.Reservation;
import com.example.Library.Resource.Checkout.System.model.User;
import com.example.Library.Resource.Checkout.System.repositories.BookRepository;
import com.example.Library.Resource.Checkout.System.repositories.ReservationRepository;
import com.example.Library.Resource.Checkout.System.repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;

@Service
public class ReservationService {
    @Autowired
    ReservationRepository reservationRepository;
    @Autowired
    BookRepository bookRepository;
    @Autowired
    UserRepository userRepository;

    public List<Reservation> getAllReservations() {
        return reservationRepository.findAll();
    }

    public Reservation getReservation(Long id) {
        return reservationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("There is no reservation with the id: " + id));
    }

    public Reservation addReservation(Reservation reservation) {
        return reservationRepository.save(reservation);
    }

    public void deleteReservation(Long id) {
        Reservation existingReservation = reservationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("There is no reservation with the id: " + id));
        reservationRepository.delete(existingReservation);
    }

    @Transactional
    public Reservation createReservation(Long bookId, Long userId) {
        Book book = bookRepository.findById(bookId).orElseThrow();
        User user = userRepository.findById(userId).orElseThrow();

        Reservation reservation = Reservation.builder()
                .book(book)
                .user(user)
                .reservationStatus(ReservationStatus.WAITING)
                .priority(user.getRole() == Role.FACULTY ? "1" : "2")
                .createdAt(LocalDate.now())
                .build();

        return reservationRepository.save(reservation);
    }

    public List<Reservation> getReservationsForBook(Long bookId) {
        List<Reservation> reservations = reservationRepository.findByBookIdAndReservationStatus(bookId, ReservationStatus.WAITING);

        // Convert string priority to int for sorting
        reservations.sort(Comparator.comparingInt(r -> {
            try {
                return Integer.parseInt(r.getPriority());
            } catch (NumberFormatException e) {
                return Integer.MAX_VALUE; // put invalid strings at the end
            }
        }));

        return reservations;
    }

    @Transactional
    public void processNextReservation(Book book) {
        List<Reservation> waiting = getReservationsForBook(book.getId());
        if (!waiting.isEmpty()) {
            Reservation next = waiting.get(0);
            next.setReservationStatus(ReservationStatus.NOTIFIED);
            reservationRepository.save(next);
        }
    }
}
