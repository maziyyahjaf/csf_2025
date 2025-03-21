import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';


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


@Injectable({
  providedIn: 'root'
})
export class CartService {

  constructor() { }

  cart: Cart = {lineItems: []}

    // BehaviorSubject to track cart state
    currentCart = new BehaviorSubject<Cart>(this.cart);
    currentCart$ = this.currentCart.asObservable();

    currentCartTotal = new BehaviorSubject<number>(0);
    currentCartTotal$ = this.currentCartTotal.asObservable();

    currentNumberOfItemsInCart = new BehaviorSubject<number>(this.cart.lineItems.length);
    currentNumberOfItemsInCart$ = this.currentNumberOfItemsInCart.asObservable();

    addItem(newMenuItem: Menu) {
       // need to check if it is an existing item
       const existingItemIndex = this.cart.lineItems.findIndex(itemInCart => itemInCart.name === newMenuItem.name);

       if (existingItemIndex !== -1) {
         // there is already an item with that name in the cart
        // need to update that item
        this.cart.lineItems[existingItemIndex].quantity++;
        this.cart.lineItems[existingItemIndex].totalPrice = this.cart.lineItems[existingItemIndex].quantity * newMenuItem.price;
       } else {
        // add new item to the cart
        this.cart.lineItems.push({
          id: newMenuItem.id,
          name: newMenuItem.name,
          price: newMenuItem.price,
          description: newMenuItem.description,
          quantity: 1,
          totalPrice: newMenuItem.price
        })
       }

       //emit updated cart to subscribers
       this.cart = {...this.cart, lineItems: [...this.cart.lineItems]}
       this.currentCart.next(this.cart);
       this.calculateCheckoutTotal();
       this.updatedNumItemsInCart();
    }

    removeItem(menuItem: Menu) {
      // find index of the menu item to remove from car
      const index = this.cart.lineItems.findIndex(item => item.name === menuItem.name);

      if (index !== -1) {
        if (this.cart.lineItems[index].quantity > 1) {
          this.cart.lineItems[index].quantity--;
          this.cart.lineItems[index].totalPrice = this.cart.lineItems[index].quantity * menuItem.price;
        } else {
          this.cart.lineItems.splice(index, 1);
        }
      }
      this.cart = {...this.cart, lineItems: [...this.cart.lineItems]}
      this.currentCart.next(this.cart);
      this.calculateCheckoutTotal();
      this.updatedNumItemsInCart();


    }

    calculateCheckoutTotal() {
      const checkoutTotal = this.cart.lineItems.reduce((acc,item) => acc + item.totalPrice, 0);
      this.currentCartTotal.next(checkoutTotal);
    }

    updatedNumItemsInCart() {
      const numberOfItems = this.cart.lineItems.reduce((acc, item) => acc + item.quantity, 0);
      this.currentNumberOfItemsInCart.next(numberOfItems);
    }

    resetCart() {
      this.cart = {lineItems: []}
      this.currentCart.next(this.cart);
      this.calculateCheckoutTotal();
      this.updatedNumItemsInCart();
    }


}
