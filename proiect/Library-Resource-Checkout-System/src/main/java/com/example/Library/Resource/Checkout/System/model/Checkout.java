package com.example.Library.Resource.Checkout.System.model;

import com.example.Library.Resource.Checkout.System.enums.CheckoutStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "checkouts")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Checkout {

    @Id // primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY) //database auto generates the value
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.EAGER) // fk, many checkouts can reference one book, LAZY = the book object is not fetched from the db immediately, only loads when you access it
    @JoinColumn(name = "book_id", nullable = false) //defines the fk column inside the table
    private Book book;

    @Column(name = "checkout_date", nullable = false)
    private LocalDate checkoutDate;

    @Column(name = "due_date", nullable = false)
    private LocalDate dueDate;

    @Column(name = "return_date")
    private LocalDate returnDate;

    @Enumerated(EnumType.STRING) //to store the enum as text in the db not as number
    @Column(name = "status", nullable = false)
    private CheckoutStatus checkoutStatus;

    @Column(name = "late_fee")
    private BigDecimal lateFee;
}
