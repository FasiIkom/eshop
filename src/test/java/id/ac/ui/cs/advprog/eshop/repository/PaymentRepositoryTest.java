package id.ac.ui.cs.advprog.eshop.repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import id.ac.ui.cs.advprog.eshop.enums.PaymentStatus;
import id.ac.ui.cs.advprog.eshop.model.Order;
import id.ac.ui.cs.advprog.eshop.model.Payment;
import id.ac.ui.cs.advprog.eshop.model.Product;

public class PaymentRepositoryTest {
    PaymentRepository paymentRepository;
    List<Payment> payments;
    Order order;

    @BeforeEach
    void setUp() {
        paymentRepository = new PaymentRepository();

        List<Product> products = new ArrayList<>();
        Product product = new Product();
        product.setProductId("be928e9f-1c39-270e-8860-71af6af63ba1");
        product.setProductName("Toyota Avanza");
        product.setProductQuantity(5);
        products.add(product);
        order = new Order("13652556-012a-4c07-b546-54eb1396d79b", products, 1708560000L, "Muldayat");

        payments = new ArrayList<>();

        Map<String, String> paymentData1 = new HashMap<>();
        paymentData1.put("Masjid Kukusan", "10000");
        Payment payment1 = new Payment("13652556-012a-4c07-b546-54eb1396d75b", order, "Cash on Delivery", paymentData1);
        payments.add(payment1);

        Map<String, String> paymentData2 = new HashMap<>();
        paymentData2.put("Masjid Beji", "20000");
        Payment payment2 = new Payment("13652556-012a-4c07-b546-54eb1396d75c", order, "Cash on Delivery", paymentData2, PaymentStatus.SUCCESS.getValue());
        payments.add(payment2);

        Map<String, String> paymentData3 = new HashMap<>();
        paymentData3.put("Masjid UI", "30000"); 
        Payment payment3 = new Payment("13652556-012a-4c07-b546-54eb1396d75d", order, "Cash on Delivery", paymentData3);
        payments.add(payment3);
    }

    @Test
    void testSaveCreate() {
        Payment payment = payments.get(1);
        Payment result = paymentRepository.save(payment);
        Payment findResult = paymentRepository.findById(payment.getId());
        assertEquals(payment.getId(), result.getId());
        assertEquals(payment.getId(), findResult.getId());
        assertEquals(payment.getMethod(), findResult.getMethod());
        assertEquals(payment.getStatus(), findResult.getStatus());
        assertEquals("20000", payment.getPaymentData().get("Masjid Beji"));
    }

    @Test
    void testSaveUpdate() {
        Payment payment = payments.get(1);
        paymentRepository.save(payment);
        Payment updatedPayment = new Payment(payment.getId(), payment.getOrder(), payment.getMethod(), payment.getPaymentData(), PaymentStatus.REJECTED.getValue());
        Payment result = paymentRepository.save(updatedPayment);
        Payment findResult = paymentRepository.findById(payment.getId());
        assertEquals(payment.getId(), result.getId());
        assertEquals(PaymentStatus.REJECTED.getValue(), findResult.getStatus());
    }

    @Test
    void testFindByIdIfIdFound() {
        for (Payment payment : payments) {
            paymentRepository.save(payment);
        }
        Payment findResult = paymentRepository.findById(payments.get(1).getId());
        assertEquals(payments.get(1).getId(), findResult.getId());
        assertEquals(payments.get(1).getMethod(), findResult.getMethod());
        assertEquals(payments.get(1).getStatus(), findResult.getStatus());
    }

    @Test
    void testFindByIdIfIdNotFound() {
        for (Payment payment : payments) {
            paymentRepository.save(payment);
        }
        Payment findResult = paymentRepository.findById("non-existent-id");
        assertNull(findResult);
    }

    @Test
    void testFindAllByMethodIfMethodCorrect() {
        for (Payment payment : payments) {
            paymentRepository.save(payment);
        }
        List<Payment> result = paymentRepository.findAllByMethod("Cash on Delivery");
        assertEquals(3, result.size());
    }

    @Test
    void testFindAllByMethodIfMethodCaseSensitive() {
        paymentRepository.save(payments.get(1));
        List<Payment> result = paymentRepository.findAllByMethod(payments.get(1).getMethod().toLowerCase());
        assertTrue(result.isEmpty());
    }
}