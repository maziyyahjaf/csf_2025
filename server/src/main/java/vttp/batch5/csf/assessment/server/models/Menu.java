package vttp.batch5.csf.assessment.server.models;

import org.bson.Document;
// import org.bson.types.ObjectId;

import jakarta.json.Json;
import jakarta.json.JsonObject;

public class Menu {
    private String id;
    private String name;
    private String description;
    private double price;

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public double getPrice() {
        return price;
    }
    public void setPrice(double price) {
        this.price = price;
    }


    @Override
    public String toString() {
        return "Menu [id=" + id + ", name=" + name + ", description=" + description + ", price=" + price + "]";
    }
    public static Menu fromDocToMenu(Document doc) {
        //ObjectId id = doc.getObjectId("_id");
        // String idString = id.toString();
        String idString = doc.getString("_id");
        String name = doc.getString("name");
        double price =  (double) doc.get("price"); // not sure what the data type for this is
        String description = doc.getString("description");

        Menu menu = new Menu();
        menu.setDescription(description);
        menu.setId(idString);
        menu.setName(name);
        menu.setPrice(price);

        return menu;
    }

    public JsonObject toJson() {
        JsonObject jsonObject = Json.createObjectBuilder()
                                    .add("id", getId())
                                    .add("name", getName())
                                    .add("description", getDescription())
                                    .add("price", getPrice())
                                    .build();
        return jsonObject;
    }

    

    
}
