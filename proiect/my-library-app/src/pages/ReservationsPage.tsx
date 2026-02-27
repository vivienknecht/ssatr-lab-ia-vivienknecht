import React, { useEffect, useState } from "react";
import { getBooks, getAllUsers, getReservationsForBook, createReservation } from "../api/api";
import type { Book } from "../types/Book";
import type { User } from "../types/User";
import type { Reservation } from "../types/Reservation";

export const ReservationsPage: React.FC = () => {
  const [books, setBooks] = useState<Book[]>([]);
  const [users, setUsers] = useState<User[]>([]);
  const [reservations, setReservations] = useState<Reservation[]>([]);
  const [selectedBookId, setSelectedBookId] = useState<number | null>(null);
  const [showUserModal, setShowUserModal] = useState(false);
  const [loadingReservations, setLoadingReservations] = useState(false);

  // fetch all books and users on load
  useEffect(() => {
    (async () => {
      const booksRes = await getBooks();
      setBooks(booksRes.data);
      const usersRes = await getAllUsers();
      setUsers(usersRes.data);
    })();
  }, []);

  const handleSeeReservations = async (bookId: number) => {
    setLoadingReservations(true);
    const res = await getReservationsForBook(bookId);
    setReservations(res.data);
    setSelectedBookId(bookId);
    setLoadingReservations(false);
  };

  const handleCreateReservation = (bookId: number) => {
    setSelectedBookId(bookId);
    setShowUserModal(true);
  };

  const handleUserSelect = async (userId: number) => {
    if (!selectedBookId) return;
    await createReservation(selectedBookId, userId);
    setShowUserModal(false);
    await handleSeeReservations(selectedBookId); // refresh reservations
  };

  return (
    <div style={{ padding: 20 }}>
      <h1>Reservations</h1>
      <div style={{ display: "flex", flexWrap: "wrap", gap: 10 }}>
        {books.map((book) => (
          <div key={book.id} style={cardStyle}>
            <div><strong>{book.title}</strong></div>
            <div>Available Copies: {book.availableCopies}</div>
            <button style={buttonStyle} onClick={() => handleSeeReservations(book.id)}>
              See Reservations
            </button>
            <button style={buttonStyle} onClick={() => handleCreateReservation(book.id)}>
              Create Reservation
            </button>
          </div>
        ))}
      </div>

      {loadingReservations && <p>Loading reservations...</p>}

      {selectedBookId && reservations.length > 0 && (
        <div style={{ marginTop: 20 }}>
          <h2>Reservations for Book ID: {selectedBookId} </h2>
          <ul>
            {reservations.map((res) => (
              <li key={res.id}>
                {res.user.name} - Status: {res.reservationStatus}
              </li>
            ))}
          </ul>
        </div>
      )}

      {/* User selection modal */}
      {showUserModal && (
        <div style={modalStyle}>
          <h3>Select User to Reserve</h3>
          <ul>
            {users.map((user) => (
              <li key={user.id}>
                <button style={buttonStyle} onClick={() => handleUserSelect(user.id)}>
                  {user.name} ({user.role})
                </button>
              </li>
            ))}
          </ul>
          <button style={buttonStyle} onClick={() => setShowUserModal(false)}>Cancel</button>
        </div>
      )}
    </div>
  );
};

// styles
const cardStyle: React.CSSProperties = {
  border: "1px solid #ddd",
  padding: 15,
  borderRadius: 6,
  width: 180,
  display: "flex",
  flexDirection: "column",
  gap: 5,
};

const buttonStyle: React.CSSProperties = {
  padding: "6px 12px",
  cursor: "pointer",
  marginTop: 5,
};

const modalStyle: React.CSSProperties = {
  position: "fixed",
  top: "20%",
  left: "50%",
  transform: "translateX(-50%)",
  background: "#fff",
  padding: 20,
  border: "1px solid #ccc",
  borderRadius: 6,
  zIndex: 100,
};