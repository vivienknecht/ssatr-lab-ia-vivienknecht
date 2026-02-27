import React from "react";
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import { BookList } from "./pages/BookList";
import { Navbar } from "./components/NavBar";
import { CheckoutsPage } from "./pages/CheckoutPage";
import { ReservationsPage } from "./pages/ReservationsPage";

const App: React.FC = () => {
  return (
    <Router>
      <Navbar />
      <Routes>
        <Route path="/" element={<BookList />} />
         <Route path="/checkouts" element={<CheckoutsPage />} />
        <Route path="/reservations" element={<ReservationsPage />} /> 
      </Routes>
    </Router>
  );
};

export default App;