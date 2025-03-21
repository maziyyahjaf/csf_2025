package vttp.batch5.csf.assessment.server.services;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import vttp.batch5.csf.assessment.server.models.Menu;
import vttp.batch5.csf.assessment.server.models.ValidUser;
import vttp.batch5.csf.assessment.server.repositories.OrdersRepository;
import vttp.batch5.csf.assessment.server.repositories.RestaurantRepository;

@Service
public class RestaurantService {

  @Autowired
  private OrdersRepository ordersRepository;

  @Autowired
  private RestaurantRepository restaurantRepository;


  // TODO: Task 2.2
  // You may change the method's signature
  public List<Menu> getMenu() {
    return ordersRepository.getMenu();
  }
  
  // TODO: Task 4

  // @Transactional
  public Optional<ValidUser> checkifValidUser(String username, String password) {
    return restaurantRepository.validateUsername(username, password);
  }

  public boolean checkIfValidPassword(String password, ValidUser user) throws NoSuchAlgorithmException {
    // create the hashed password?
    MessageDigest digest = MessageDigest.getInstance("SHA-256");
    byte[] byteOfPasswordToHash = password.getBytes(StandardCharsets.UTF_8);
    byte[] hashedByteArray = digest.digest(byteOfPasswordToHash);
    String encoded = Base64.getEncoder().encodeToString(hashedByteArray);

    String savedPassword = user.getHashPassword();

    if (encoded.equals(savedPassword)) {
      return true;
    }

    return false;
    
  }


}
