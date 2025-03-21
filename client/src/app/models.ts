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