import React, { useEffect, useState } from "react";
import { getAllCheckouts, returnBook } from "../api/api";
import type { Checkout } from "../types/Checkout";

export const CheckoutsPage: React.FC = () => {
  const [checkouts, setCheckouts] = useState<Checkout[]>([]);
  const [loading, setLoading] = useState(false);

  const fetchCheckouts = async () => {
    setLoading(true);
    const res = await getAllCheckouts();
    setCheckouts(res.data);
    setLoading(false);
  };

  const handleReturn = async (checkoutId: number) => {
    await returnBook(checkoutId);
    fetchCheckouts();
  };

  useEffect(() => {
    fetchCheckouts();
  }, []);

  return (
    <div style={{ padding: 20 }}>
      <h1>All Checkouts</h1>

      {loading && <p>Loading...</p>}

      {checkouts.map((checkout) => (
        <div key={checkout.id} style={cardStyle}>
          <div>
            <strong>{checkout.book.title}</strong>
          </div>

          <div>User: {checkout.user.name}</div>

          <div>
            Status:{" "}
            <span style={getStatusStyle(checkout.checkoutStatus)}>
              {checkout.checkoutStatus}
            </span>
          </div>

          <div>Checkout Date: {checkout.checkoutDate}</div>
          <div>Due Date: {checkout.dueDate}</div>

          {checkout.returnDate && (
            <div>Returned: {checkout.returnDate}</div>
          )}

          {checkout.lateFee && checkout.lateFee > 0 && (
            <div>Late Fee: ${checkout.lateFee}</div>
          )}

          {(checkout.checkoutStatus === "ACTIVE" ||
            checkout.checkoutStatus === "OVERDUE") && (
            <button
              style={buttonStyle}
              onClick={() => handleReturn(checkout.id)}
            >
              Return Book
            </button>
          )}
        </div>
      ))}
    </div>
  );
};

const cardStyle: React.CSSProperties = {
  border: "1px solid #ddd",
  padding: 15,
  marginBottom: 15,
  borderRadius: 6,
};

const buttonStyle: React.CSSProperties = {
  marginTop: 10,
  padding: "6px 12px",
  cursor: "pointer",
};

const getStatusStyle = (status: string): React.CSSProperties => {
  if (status === "OVERDUE") return { color: "red" };
  if (status === "RETURNED") return { color: "green" };
  return { color: "orange" };
};