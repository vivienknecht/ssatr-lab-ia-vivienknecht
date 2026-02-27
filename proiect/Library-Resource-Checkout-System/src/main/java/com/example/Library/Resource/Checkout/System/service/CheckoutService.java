package com.example.Library.Resource.Checkout.System.service;

import com.example.Library.Resource.Checkout.System.enums.CheckoutStatus;
import com.example.Library.Resource.Checkout.System.enums.Role;
import com.example.Library.Resource.Checkout.System.model.Book;
import com.example.Library.Resource.Checkout.System.model.Checkout;
import com.example.Library.Resource.Checkout.System.model.User;
import com.example.Library.Resource.Checkout.System.repositories.BookRepository;
import com.example.Library.Resource.Checkout.System.repositories.CheckoutRepository;
import com.example.Library.Resource.Checkout.System.repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class CheckoutService {
    @Autowired
    CheckoutRepository checkoutRepository;
    @Autowired
    BookRepository bookRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    ReservationService reservationService;

    public List<Checkout> getAllCheckouts() {
        return checkoutRepository.findAll();
    }

    public Checkout getCheckout(Long id) {
        return checkoutRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("There is no book with the id: " + id));
    }

    public Checkout addCheckout(Checkout checkout) {
        return checkoutRepository.save(checkout);
    }

    public void deleteCheckout(Long id) {
        Checkout existingCheckout = checkoutRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("There is no book with the id: " + id));

        checkoutRepository.delete(existingCheckout);
    }

    public LocalDate calculateDueDate(Role role) {
        if (role == Role.FACULTY) {
            return LocalDate.now().plusDays(30);
        }
        return LocalDate.now().plusDays(14);
    }

    @Transactional
    public Checkout checkoutBook(Long bookId, Long userId) {
        Book book = bookRepository.findById(bookId).orElseThrow();
        User user = userRepository.findById(userId).orElseThrow();

        if (book.getAvailableCopies() <= 0) {
            throw new RuntimeException("Book not available");
        }

        book.setAvailableCopies(book.getAvailableCopies() - 1);

        Checkout checkout = Checkout.builder()
                .book(book)
                .user(user)
                .checkoutDate(LocalDate.now())
                .dueDate(calculateDueDate(user.getRole()))
                .checkoutStatus(CheckoutStatus.ACTIVE)
                .build();

        return checkoutRepository.save(checkout);
    }

    @Transactional
    public Checkout returnBook(Long checkoutId) {
        Checkout checkout = checkoutRepository.findById(checkoutId).orElseThrow();

        checkout.setReturnDate(LocalDate.now());

        if (LocalDate.now().isAfter(checkout.getDueDate())) {
            long daysLate = ChronoUnit.DAYS.between(
                    checkout.getDueDate(),
                    LocalDate.now()
            );

            double rate = checkout.getUser().getRole() == Role.FACULTY ? 0.5 : 1.0;
            checkout.setLateFee(BigDecimal.valueOf(daysLate * rate));
            checkout.setCheckoutStatus(CheckoutStatus.OVERDUE);
        } else {
            checkout.setCheckoutStatus(CheckoutStatus.RETURNED);
        }

        Book book = checkout.getBook();
        book.setAvailableCopies(book.getAvailableCopies() + 1);

        reservationService.processNextReservation(book);

        return checkout;
    }

    public List<Checkout> getUserCheckoutHistory(Long userId) {
        return checkoutRepository.findByUserId(userId);
    }

    public List<Checkout> getOverdueCheckouts() {
        return checkoutRepository.findByCheckoutStatus(CheckoutStatus.OVERDUE);
    }

    public void markOverdueCheckouts() {
        List<Checkout> activeCheckouts = checkoutRepository.findByCheckoutStatus(CheckoutStatus.ACTIVE);
        for (Checkout c : activeCheckouts) {
            if (LocalDate.now().isAfter(c.getDueDate())) {
                c.setCheckoutStatus(CheckoutStatus.OVERDUE);
                checkoutRepository.save(c);
            }
        }
    }
}
