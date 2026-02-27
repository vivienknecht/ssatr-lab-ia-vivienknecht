import type { Book } from "./Book";
import type { User } from "./User";

export interface Checkout {
    id: number;
    book: Book;
    user: User;
    checkoutDate: string;
    dueDate: string;
    returnDate?: string;
    checkoutStatus: string;
    lateFee?: number;
}