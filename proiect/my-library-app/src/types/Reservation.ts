import type { Book } from "./Book";
import type { User } from "./User";

export interface Reservation {
    id: number;
    book: Book;
    user: User;
    priority: string; 
    reservationStatus: string;
    createdAt: Date;
}