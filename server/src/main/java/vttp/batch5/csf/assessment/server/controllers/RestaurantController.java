package vttp.batch5.csf.assessment.server.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObject;
import vttp.batch5.csf.assessment.server.services.RestaurantService;
import vttp.batch5.csf.assessment.server.models.Menu;


@Controller
@RequestMapping(path="/api", produces= MediaType.APPLICATION_JSON_VALUE)
public class RestaurantController {

  @Autowired
  private RestaurantService restaurantService;

  // TODO: Task 2.2
  // You may change the method's signature
  @GetMapping("/menu")
  public ResponseEntity<String> getMenus() {
    List<Menu> menuList = restaurantService.getMenu();
    // need to transform from menu to json -> jsonarray
    JsonArrayBuilder jab = Json.createArrayBuilder();
    for (Menu m : menuList) {
      JsonObject menuJson = m.toJson();
      jab.add(menuJson);
    }

    // build the response / payload
    JsonArray jsonArray = jab.build();

    return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(jsonArray.toString());
  }

  // TODO: Task 4
  // Do not change the method's signature
  public ResponseEntity<String> postFoodOrder(@RequestBody String payload) {
    return ResponseEntity.ok("{}");
  }
}
