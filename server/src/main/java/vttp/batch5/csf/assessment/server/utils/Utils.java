package vttp.batch5.csf.assessment.server.utils;



import jakarta.json.JsonObject;
import vttp.batch5.csf.assessment.server.models.LineItem;

public class Utils {

    public static LineItem toLineItem(JsonObject jsonObject) {
        LineItem lineItem = new LineItem();
        String id = jsonObject.getString("id");
        double price = jsonObject.getJsonNumber("price").doubleValue();
        int quantity = jsonObject.getInt("quantity");

        return lineItem;
    }
    
}
