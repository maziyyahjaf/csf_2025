package vttp.batch5.csf.assessment.server.services;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.xml.bind.DatatypeConverter;
import vttp.batch5.csf.assessment.server.models.LineItem;
import vttp.batch5.csf.assessment.server.models.Menu;
import vttp.batch5.csf.assessment.server.models.PaymentDetails;
import vttp.batch5.csf.assessment.server.models.ValidUser;
import vttp.batch5.csf.assessment.server.repositories.OrdersRepository;
import vttp.batch5.csf.assessment.server.repositories.RestaurantRepository;

@Service
public class RestaurantService {

  private static final Logger logger = LoggerFactory.getLogger(RestaurantService.class);

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
    // String encoded = Base64.getEncoder().encodeToString(hashedByteArray);
    String encoded = DatatypeConverter.printHexBinary(hashedByteArray);

    String savedPassword = user.getHashPassword();
    logger.info("saved password: {}", savedPassword);

    logger.info("hashed password: {}", encoded);

    if (encoded.equals(savedPassword)) {
      return true;
    }

    return false;
  }

  public void saveOrderAndPaymentDetails(PaymentDetails paymentDetails, ValidUser user, List<LineItem> lineItems, double checkoutTotal) {

    // save to sql
    String username = user.getUsername();
    restaurantRepository.saveOrder(paymentDetails, username, checkoutTotal);
  }


}
