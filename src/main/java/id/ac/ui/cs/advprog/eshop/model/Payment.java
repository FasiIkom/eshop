package id.ac.ui.cs.advprog.eshop.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;
import java.util.Arrays;

@Builder
@Getter
public class Payment {
    String id;
    Order order;
    String method;
    Map<String, String> paymentData;
    @Setter
    String status;

    public Payment(String id, Order order, String method, Map<String, String> paymentData) {
        this.id = id;
        this.method = method;
        this.status = "PENDING";
        this.paymentData = paymentData;
        if (order == null) {
            throw new IllegalArgumentException();
        } else {
            this.order = order;
        }
    }
    public Payment(String id, Order order, String method, Map<String, String> paymentData, String status) {
        this(id, order, method, paymentData);
        String[] statusList = {"PENDING", "SUCCESS", "REJECTED"};
        if(Arrays.stream(statusList).noneMatch(item -> item.equals(status))) {
            throw new IllegalArgumentException();
        } else {
            this.status = status;
        }
    }
}
