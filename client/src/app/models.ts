// You may use this file to create any models
export interface Menu {
    id: string;
    name: string;
    description: string;
    price: number;
}

export interface LineItem extends Menu {
    totalPrice: number;
    quantity: number;
}

export interface Cart {
  lineItems: LineItem[];
}

export interface CheckoutItem {
    id: string;
    price: number;
    quantity: number;
}

export interface CheckoutForm {
    username: string;
    password: string;
    items: Cart
}

export interface CheckoutDetails {
    username: string;
    password: string;
    items: CheckoutItem[];
}