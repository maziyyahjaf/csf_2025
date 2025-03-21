package vttp.batch5.csf.assessment.server.repositories;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import vttp.batch5.csf.assessment.server.models.LineItem;
import vttp.batch5.csf.assessment.server.models.Menu;
import vttp.batch5.csf.assessment.server.models.PaymentDetails;
import vttp.batch5.csf.assessment.server.models.ValidUser;


@Repository
public class OrdersRepository {

  private static final Logger logger = LoggerFactory.getLogger(OrdersRepository.class);
  
  @Autowired
  private MongoTemplate mongoTemplate;
  // TODO: Task 2.2
  // You may change the method's signature
  // Write the native MongoDB query in the comment below
  //
  //  Native MongoDB query here
  //  
  /*
   * i want to get the menus from mongodb 
   * 
   * db.menus.find({}).sort({name : 1})
   */
  public List<Menu> getMenu() {

    Query query = new Query().with(Sort.by(Sort.Direction.ASC, "name"));
    List<Document> menuDocs = mongoTemplate.find(query, Document.class, "menus");
    logger.info("doc from mongo: {}", menuDocs);
    List<Menu> menus = new ArrayList<>();

    for (Document doc : menuDocs) {
        Menu menu = Menu.fromDocToMenu(doc);
        menus.add(menu);
    }
    logger.info("docs to menu: {}", menus);
    return menus;

  }

  // TODO: Task 4
  // Write the native MongoDB query for your access methods in the comment below
  //
  //  Native MongoDB query here

  public void saveOrderAndPaymentDetails(PaymentDetails paymentDetails, ValidUser user, List<LineItem> lineItems, double checkoutTotal) {
    // save to mongo
    String _id = paymentDetails.getOrder_id();
    String order_id = paymentDetails.getOrder_id();
    long timestamp = paymentDetails.getTimestamp();
    Date date = new Date(timestamp);

    String username = user.getUsername();

    List<Document> documentList = new ArrayList<>();
    
    for (LineItem item: lineItems) {
      Document doc = toDocument(item);
      documentList.add(doc);
    }


    Document newDoc = new Document()
                        .append("_id", _id)
                        .append("order_id", order_id)
                        .append("username", username)
                        .append("timestamp", date)
                        .append("items", documentList);
    
    mongoTemplate.insert(newDoc, "orders");



  }

  public Document toDocument(LineItem item) {
    Document doc = new Document()
                    .append("id", item.getId())
                    .append("price", item.getPrice())
                    .append("quantity", item.getQuantity());
    return doc;
  }
  
}
