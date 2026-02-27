import axios from "axios";
import type { Book } from "../types/Book";
import type { Checkout } from "../types/Checkout";
import type { Reservation } from "../types/Reservation";
import type { User } from "../types/User";

const api = axios.create({
  baseURL: "http://localhost:8082",
});

export const getBooks = () => api.get<Book[]>("/books/getAllBooks");
export const checkoutBook = (bookId: number, userId: number) =>
  api.post<Checkout>(`/checkouts/checkoutBook/${bookId}/user/${userId}`);
export const returnBook = (checkoutId: number) =>
  api.post<Checkout>(`/checkouts/returnBook/${checkoutId}`);
export const getCheckoutHistory = (userId: number) =>
  api.get<Checkout[]>(`/checkouts/user/${userId}`);
export const getOverDueBooks = () => 
    api.get<Checkout[]>(`checkouts/overdue`);
export const getReservationsForBook = (bookId: number) =>
  api.get<Reservation[]>(`/reservations/book/${bookId}`);
export const createReservation = (bookId: number, userId: number) =>
  api.post<Reservation>(`/reservations/createReservation/${bookId}/user/${userId}`);
export const getBookQr = (bookId: number) =>
  api.get(`/books/getQR/${bookId}`, { responseType: "blob" });
export const getAllUsers = () => 
  api.get<User[]>(`users/getAllUsers`);
export const getAllCheckouts = () =>
  api.get<Checkout[]>(`checkouts/getAllCheckouts`);