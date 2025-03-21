import { Component, inject, OnInit } from '@angular/core';
import { RestaurantService } from '../restaurant.service';
import { Observable } from 'rxjs';
import { Menu } from '../models';
import { Cart, CartService } from '../services/cart.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-menu',
  standalone: false,
  templateUrl: './menu.component.html',
  styleUrl: './menu.component.css'
})
export class MenuComponent implements OnInit {
 
  // TODO: Task 2
  restaurantService = inject(RestaurantService);
  menus$!: Observable<Menu[]>

  cart: Cart = {lineItems: []}
  checkout_total: number = 0;
  cartService = inject(CartService);

  currentCart$ = this.cartService.currentCart$;
  currentCartTotal$ = this.cartService.currentCartTotal$;
  currentNumOfItemsIncart$ = this.cartService.currentNumberOfItemsInCart$;

  router = inject(Router);




  ngOnInit(): void {
    this.menus$ = this.restaurantService.getMenuItems();
    this.currentCart$.subscribe((cart) => {
      this.cart = cart;
    })
    this.currentCartTotal$.subscribe((total) => {
      this.checkout_total = total;
    })
    
  }

  add(item: Menu) {
    this.cartService.addItem(item);
  }

  remove(item: Menu) {
    this.cartService.removeItem(item);
  }

  valid(): boolean {
    return !(this.cart.lineItems.length > 0)
  }

  placeOrder() {
    this.router.navigate(['order']);
  }
 
}
