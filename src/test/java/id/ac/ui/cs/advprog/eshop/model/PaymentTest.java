package id.ac.ui.cs.advprog.eshop.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import static org.junit.jupiter.api.Assertions.*;
import id.ac.ui.cs.advprog.eshop.enums.PaymentStatus;

public class PaymentTest {
    private Order order;
    private List<Product> products;

    @BeforeEach
    void setUp() {
        this.products = new ArrayList<>();
        Product product1 = new Product();
        product1.setProductId("be928e9f-1c39-270e-8860-71af6af63ba1");
        product1.setProductName("Toyota Avanza");
        product1.setProductQuantity(20);

        Product product2 = new Product();
        product2.setProductId("be928e9f-1c39-270e-8860-71af6af63ba2");
        product2.setProductName("Honda Jazz");
        product2.setProductQuantity(10);

        this.products.add(product1);
        this.products.add(product2);

        this.order = new Order("12553856-210a-4c07-b546-54eb1396d79b", 
            this.products, 1806560000L, "Muldayat");
    }

    @Test
    void testCreatePaymentEmptyOrder() {
        Map <String, String> paymentData = new HashMap<>();
        paymentData.put("Masjid Kukusan", "10000");
        assertThrows(IllegalArgumentException.class, () -> {
            Payment payment = new Payment("00000000-0000-0000-0000-000000000000", null, "Cash on Delivery", paymentData);
        });
    }

    @Test
    void testCreatePaymentDefaultStatus() {
        Map <String, String> paymentData = new HashMap<>();
        paymentData.put("Masjid Kukusan", "10000");
        Payment payment = new Payment("00000000-0000-0000-0000-000000000000", this.order, 
            "Cash on Delivery", paymentData);

        assertSame(this.order, payment.getOrder());
        assertEquals("12553856-210a-4c07-b546-54eb1396d79b", payment.getOrder().getId());
        assertEquals(1806560000L, payment.getOrder().getOrderTime());
        assertEquals("Muldayat", payment.getOrder().getAuthor());

        assertEquals("00000000-0000-0000-0000-000000000000", payment.getId());
        assertEquals(this.order, payment.getOrder());
        assertEquals("Cash on Delivery", payment.getMethod());
        assertEquals(paymentData, payment.getPaymentData());
        assertEquals(PaymentStatus.PENDING.getValue(), payment.getStatus());
    }

    @Test
    void testCreatePaymentSuccessStatus() {
        Map <String, String> paymentData = new HashMap<>();
        paymentData.put("Masjid Kukusan", "10000");
        Payment payment = new Payment("00000000-0000-0000-0000-000000000000", this.order, 
            "Cash on Delivery", paymentData, PaymentStatus.SUCCESS.getValue());
        assertEquals(PaymentStatus.SUCCESS.getValue(), payment.getStatus());
    }

    @Test
    void testCreatePaymentInvalidStatus() {
        Map <String, String> paymentData = new HashMap<>();
        assertThrows(IllegalArgumentException.class, () -> {
            Payment payment = new Payment("00000000-0000-0000-0000-000000000000", this.order, 
                "Cash on Delivery", paymentData, "SLEEP");
        });
    }

    @Test
    void testSetStatusToRejected() {
        Map <String, String> paymentData = new HashMap<>();
        Payment payment = new Payment("00000000-0000-0000-0000-000000000000", this.order, 
            "Cash on Delivery", paymentData, PaymentStatus.SUCCESS.getValue());
        payment.setStatus(PaymentStatus.REJECTED.getValue());
        assertEquals(PaymentStatus.REJECTED.getValue(), payment.getStatus());
    }

    @Test
    void testSetStatusToInvalidStatus() {
        Map <String, String> paymentData = new HashMap<>();
        paymentData.put("Masjid Kukusan", "10000");
        Payment payment = new Payment("00000000-0000-0000-0000-000000000000", this.order, 
            "Cash on Delivery", paymentData);
        assertThrows(IllegalArgumentException.class, () -> {
            order.setStatus("TIDUR");
        });
    }
}
