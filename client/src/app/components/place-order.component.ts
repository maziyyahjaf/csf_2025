import { Component, inject, OnInit } from '@angular/core';
import { Cart, CartService } from '../services/cart.service';
import { FormBuilder, FormGroup } from '@angular/forms';

@Component({
  selector: 'app-place-order',
  standalone: false,
  templateUrl: './place-order.component.html',
  styleUrl: './place-order.component.css'
})
export class PlaceOrderComponent implements OnInit{
 

  // TODO: Task 3

  cart: Cart = {lineItems: []};
  checkout_total: number = 0;
  cartService = inject(CartService);

  currentCart$ = this.cartService.currentCart$;
  currentCartTotal$ = this.cartService.currentCartTotal$;
  currentNumOfItemsIncart$ = this.cartService.currentNumberOfItemsInCart$;

  fb = inject(FormBuilder);
  checkoutForm!: FormGroup;

  ngOnInit(): void {
    this.checkoutForm= this.createCheckoutForm();
    this.currentCart$.subscribe((cart) => {
      this.cart = cart;
      this.checkoutForm.patchValue({items: this.cart})
    })
    this.currentCartTotal$.subscribe((total) => {
      this.checkout_total = total;
      this.checkoutForm.patchValue({checkoutTotal: this.checkout_total})
    })
  }

  createCheckoutForm(): FormGroup {
    return this.fb.group({
      username: this.fb.control<string>(''),
      password: this.fb.control<string>(''),
      items: this.fb.control<Cart>({lineItems: []}),
      checkoutTotal: this.fb.control<number>(0)
    })
  }

}
