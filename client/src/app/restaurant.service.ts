import { HttpClient, HttpHeaders } from "@angular/common/http";
import { inject, Injectable } from "@angular/core";
import { CheckoutDetails, Menu } from "./models";

// call the backend for the menus
@Injectable({
  providedIn: 'root'
})
export class RestaurantService {

  http = inject(HttpClient);

  // TODO: Task 2.2
  // You change the method's signature but not the name
  getMenuItems() {
    return this.http.get<Menu[]>('/api/menu')
  }

  // TODO: Task 3.2
  // submit order to backend
  placeOrder(order: CheckoutDetails) {
    console.log("sending order to backend", order);
    const httpHeaders = new HttpHeaders()
                          .set("Content-Type", "application/json")
                          .set("Accept", "application/json")
    return this.http.post('/api/food_order', order, {headers: httpHeaders})

  }

}
