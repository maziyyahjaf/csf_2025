package vttp.batch5.csf.assessment.server.models;

public class PaymentDetails {

    private String payment_id;
    private String order_id;
    private long timestamp;

    
    public PaymentDetails() {
    }
    
    public PaymentDetails(String payment_id, String order_id, long timestamp) {
        this.payment_id = payment_id;
        this.order_id = order_id;
        this.timestamp = timestamp;
    }
    public String getPayment_id() {
        return payment_id;
    }
    public void setPayment_id(String payment_id) {
        this.payment_id = payment_id;
    }
    public String getOrder_id() {
        return order_id;
    }
    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }
    public long getTimestamp() {
        return timestamp;
    }
    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    
    
}
