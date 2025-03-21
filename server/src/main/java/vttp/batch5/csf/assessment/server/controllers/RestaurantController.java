package vttp.batch5.csf.assessment.server.controllers;

import java.io.StringReader;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import vttp.batch5.csf.assessment.server.services.PaymentService;
import vttp.batch5.csf.assessment.server.services.RestaurantService;
import vttp.batch5.csf.assessment.server.utils.Utils;
import vttp.batch5.csf.assessment.server.models.Menu;
import vttp.batch5.csf.assessment.server.models.PaymentDetails;
import vttp.batch5.csf.assessment.server.exceptions.InvalidPasswordException;
import vttp.batch5.csf.assessment.server.models.LineItem;
import vttp.batch5.csf.assessment.server.models.ValidUser;


@Controller
@RequestMapping(path="/api", produces= MediaType.APPLICATION_JSON_VALUE)
public class RestaurantController {

  @Autowired
  private RestaurantService restaurantService;

  @Autowired
  private PaymentService paymentService;

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
  @PostMapping("/food_order")
  public ResponseEntity<String> postFoodOrder(@RequestBody String payload) throws NoSuchAlgorithmException {

    //read the payload first
    JsonReader jsonReader = Json.createReader(new StringReader(payload));
    JsonObject root = jsonReader.readObject();
    String username = root.getString("username");
    String password = root.getString("password");
  

    JsonArray itemsArray = root.getJsonArray("items");

    List<LineItem> lineItems = new ArrayList<>();

    for (JsonObject item : itemsArray.getValuesAs(JsonObject.class)) {
      LineItem lineItem = Utils.toLineItem(item);
      lineItems.add(lineItem);
    }

    // validate the request

    Optional<ValidUser> userOpt = restaurantService.checkifValidUser(username, password);
    ValidUser user = userOpt.get();

    // check password
    if (!restaurantService.checkIfValidPassword(password, user)) {
      throw new InvalidPasswordException("Invalid password");
    }
    // place order

    String orderId = UUID.randomUUID().toString().substring(0, 9);
    // need to get the total of the line items
    double checkoutTotal = lineItems.stream().mapToDouble(item -> getTotalSumForEachItem(item)).sum();
    PaymentDetails paymentDetails = paymentService.makePayment(orderId, checkoutTotal, username);



    return ResponseEntity.ok("{}");
  }

  private double getTotalSumForEachItem(LineItem item) {
    double totalSum = item.getPrice() * item.getQuantity();
    return totalSum;
  }
}
