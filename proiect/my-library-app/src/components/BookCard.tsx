import React from "react";
import type { Book } from "../types/Book";

interface Props {
  book: Book;
  onCheckout: () => void;
  onQr: () => void;
}

export const BookCard: React.FC<Props> = ({ book, onCheckout, onQr }) => {
  return (
    <div style={{ border: "1px solid #ddd", padding: "1rem", margin: "0.5rem" }}>
      <h3>{book.title}</h3>
      <p>Author: {book.author}</p>
      <p>Available: {book.availableCopies} / {book.totalCopies}</p>
      <button onClick={onCheckout} disabled={book.availableCopies === 0}>
        Checkout
      </button>
      <button onClick={onQr}>QR</button>
    </div>
  );
};