import React, { useEffect, useState } from "react";
import { getBooks, checkoutBook, getBookQr } from "../api/api";
import type { Book } from "../types/Book";
import { BookCard } from "../components/BookCard";
import { UserSelectModal } from "../components/UserSelectModal";

export const BookList: React.FC = () => {
  const [books, setBooks] = useState<Book[]>([]);
  const [modalOpen, setModalOpen] = useState(false);
  const [selectedBookId, setSelectedBookId] = useState<number | null>(null);

  const fetchBooks = async () => {
    const res = await getBooks();
    setBooks(res.data);
  };

  // When user is selected inside modal
  const handleUserSelect = async (userId: number) => {
    if (!selectedBookId) return;

    await checkoutBook(selectedBookId, userId);
    fetchBooks();
  };

  const handleCheckoutClick = (bookId: number) => {
    setSelectedBookId(bookId);
    setModalOpen(true);
  };

  const handleQr = async (bookId: number) => {
    const res = await getBookQr(bookId);
    const url = URL.createObjectURL(res.data);
    window.open(url);
  };

  useEffect(() => {
    fetchBooks();
  }, []);

  return (
    <div>
      <h1>Library Books</h1>

      <div style={{ display: "flex", flexWrap: "wrap" }}>
        {books.map((book) => (
          <BookCard
            key={book.id}
            book={book}
            onCheckout={() => handleCheckoutClick(book.id)}
            onQr={() => handleQr(book.id)}
          />
        ))}
      </div>

      <UserSelectModal
        open={modalOpen}
        onClose={() => setModalOpen(false)}
        onSelect={handleUserSelect}
      />
    </div>
  );
};