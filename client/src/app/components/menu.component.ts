import { Component, inject, OnInit } from '@angular/core';
import { RestaurantService } from '../restaurant.service';
import { Observable } from 'rxjs';
import { Menu } from '../models';

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


  ngOnInit(): void {
    this.menus$ = this.restaurantService.getMenuItems();
    
  }

}
