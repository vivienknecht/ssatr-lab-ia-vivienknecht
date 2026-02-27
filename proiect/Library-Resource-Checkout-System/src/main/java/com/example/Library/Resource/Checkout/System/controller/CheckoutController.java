package com.example.Library.Resource.Checkout.System.controller;

import com.example.Library.Resource.Checkout.System.model.Checkout;
import com.example.Library.Resource.Checkout.System.service.CheckoutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/checkouts")
@CrossOrigin(origins = "http://localhost:5173")
public class CheckoutController {
    private final CheckoutService checkoutService;

    @Autowired
    public CheckoutController(CheckoutService checkoutService) {
        this.checkoutService = checkoutService;
    }

    @GetMapping("/getAllCheckouts")
    public ResponseEntity<List<Checkout>> getAllBooks() {
        List<Checkout> checkouts = checkoutService.getAllCheckouts();
        return ResponseEntity.ok(checkouts);
    }

    @GetMapping("/getCheckout/{id}")
    public ResponseEntity<Checkout> getCheckout(@PathVariable Long id) {
        Checkout checkout = checkoutService.getCheckout(id);
        return ResponseEntity.ok(checkout);
    }

    @PostMapping("/addCheckout")
    public ResponseEntity<Checkout> addCheckout(@RequestBody Checkout checkout) {
        Checkout addedCheckout = checkoutService.addCheckout(checkout);
        return ResponseEntity.ok(addedCheckout);
    }

    @DeleteMapping("/deleteCheckout")
    public ResponseEntity<Checkout> deleteCheckout(@PathVariable Long id) {
        checkoutService.deleteCheckout(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/checkoutBook/{bookId}/user/{userId}")
    public ResponseEntity<Checkout> checkoutBook(@PathVariable Long bookId, @PathVariable Long userId) {
        Checkout newBookCheckout = checkoutService.checkoutBook(bookId, userId);
        return ResponseEntity.ok(newBookCheckout);
    }

    @PostMapping("/returnBook/{id}")
    public ResponseEntity<Checkout> returnBook(@PathVariable Long id) {
        Checkout checkout = checkoutService.returnBook(id);
        return ResponseEntity.ok(checkout);
    }

    @GetMapping("/user/{userId}")
    public List<Checkout> getUserHistory(@PathVariable Long userId) {
        return checkoutService.getUserCheckoutHistory(userId);
    }

    @GetMapping("/overdue")
    public List<Checkout> getOverdueCheckouts() {
        return checkoutService.getOverdueCheckouts();
    }
}
