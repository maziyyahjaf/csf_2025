import { HttpClient } from "@angular/common/http";
import { inject, Injectable } from "@angular/core";
import { Menu } from "./models";

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
}
