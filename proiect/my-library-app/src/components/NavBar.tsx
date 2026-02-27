import React from "react";
import { Link } from "react-router-dom";

export const Navbar: React.FC = () => {
  return (
    <nav style={{ padding: "1rem", borderBottom: "1px solid #ccc" }}>
      <Link to="/">Books</Link> | <Link to="/checkouts">Checkouts</Link> |{" "}
      <Link to="/reservations">Reservations</Link>
    </nav>
  );
};