package com.example.Library.Resource.Checkout.System.repositories;

import com.example.Library.Resource.Checkout.System.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {
}
