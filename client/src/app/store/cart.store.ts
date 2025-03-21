import { ComponentStore } from "@ngrx/component-store";
import { LineItem, Menu } from "../models";
import { Injectable } from "@angular/core";

export interface CartState {
    items : LineItem[];
    checkoutTotal: number;
}

@Injectable({
    providedIn: 'root'
})
export class CartStore extends ComponentStore<CartState> {
    constructor() {
        super({items: [], checkoutTotal: 0})
    }

    readonly items$ = this.select(state => state.items);
    readonly checkoutTotal$ = this.select(state => state.checkoutTotal);
    readonly total$ = this.select(
        this.items$,
        (items) => items.reduce((acc, item) => acc + (item.price * item.quantity), 0)
    );

    readonly quantityOfEachItem$ = this.select(
        this.items$,
        (items) => items.map((item) => ({name: item.name, quantity: item.quantity}))
    )



}