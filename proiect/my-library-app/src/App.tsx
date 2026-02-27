import React from "react";
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import { BookList } from "./pages/BookList";
import { Navbar } from "./components/NavBar";
import { CheckoutsPage } from "./pages/CheckoutPage";
import { ReservationsPage } from "./pages/ReservationsPage";

const App: React.FC = () => {
  return (
    <Router>
      <div style={{ 
        display: "flex", 
        flexDirection: "column", 
        alignItems: "center", 
        minHeight: "100vh",
        marginLeft: "100" 
      }}>
        <Navbar />

        <div style={{ width: "100%", maxWidth: "1200px", textAlign: "center" }}>
          <Routes>
            <Route path="/" element={<BookList />} />
            <Route path="/checkouts" element={<CheckoutsPage />} />
            <Route path="/reservations" element={<ReservationsPage />} />
          </Routes>
        </div>
      </div>
    </Router>
  );
};

export default App;