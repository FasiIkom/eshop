package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.model.Order;
import id.ac.ui.cs.advprog.eshop.model.Payment;
import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.repository.PaymentRepository;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class PaymentServiceImplTest {
    @InjectMocks
    PaymentServiceImpl paymentService;

    @Mock
    PaymentRepository paymentRepository;

    List<Payment> payments;
    Order order;

    @BeforeEach
    void setUp() {
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
        Payment payment2 = new Payment("13652556-012a-4c07-b546-54eb1396d75c", order, "Cash on Delivery", paymentData2, "SUCCESS");
        payments.add(payment2);

        Map<String, String> paymentData3 = new HashMap<>();
        paymentData3.put("Masjid UI", "30000");
        Payment payment3 = new Payment("13652556-012a-4c07-b546-54eb1396d75d", order, "Cash on Delivery", paymentData3);
        payments.add(payment3);
    }

    @Test
    void testCreatePayment() {
        Payment payment = payments.get(1);
        doReturn(payment).when(paymentRepository).save(payment);

        Payment result = paymentService.createPayment(payment);
        verify(paymentRepository, times(1)).save(payment);
        assertEquals(payment.getId(), result.getId());
    }

    @Test
    void testCreatePaymentIfAlreadyExist() {
        Payment payment = payments.get(1);
        doReturn(payment).when(paymentRepository).findById(payment.getId());
        assertNull(paymentService.createPayment(payment));
        verify(paymentRepository, times(0)).save(payment);
    }

    @Test
    void testUpdateStatus() {
        Payment payment = payments.get(1);
        Payment updatedPayment = new Payment(payment.getId(), payment.getOrder(), payment.getMethod(), payment.getPaymentData(), "REJECTED");
        doReturn(payment).when(paymentRepository).findById(payment.getId());
        doReturn(updatedPayment).when(paymentRepository).save(any(Payment.class));

        Payment result = paymentService.updateStatus(payment.getId(), "REJECTED");
        assertEquals(payment.getId(), result.getId());
        assertEquals("REJECTED", result.getStatus());
        verify(paymentRepository, times(1)).save(any(Payment.class));
    }

    @Test
    void testUpdateStatusInvalidStatus() {
        Payment payment = payments.get(1);
        doReturn(payment).when(paymentRepository).findById(payment.getId());

        assertThrows(IllegalArgumentException.class, () -> {
            paymentService.updateStatus(payment.getId(), "INVALID_STATUS");
        });
        verify(paymentRepository, times(0)).save(any(Payment.class));
    }

    @Test
    void testUpdateStatusInvalidPaymentId() {
        doReturn(null).when(paymentRepository).findById("non-existent-id");

        assertThrows(NoSuchElementException.class, () -> {
            paymentService.updateStatus("non-existent-id", "SUCCESS");
        });
        verify(paymentRepository, times(0)).save(any(Payment.class));
    }

    @Test
    void testFindByIdIfIdFound() {
        Payment payment = payments.get(1);
        doReturn(payment).when(paymentRepository).findById(payment.getId());

        Payment result = paymentService.findById(payment.getId());
        assertEquals(payment.getId(), result.getId());
    }

    @Test
    void testFindByIdIfIdNotFound() {
        doReturn(null).when(paymentRepository).findById("non-existent-id");
        assertNull(paymentService.findById("non-existent-id"));
    }

    @Test
    void testFindAllByMethodIfMethodCorrect() {
        for (Payment payment : payments) {
            paymentRepository.save(payment);
        }
        doReturn(payments).when(paymentRepository).findAllByMethod("Cash on Delivery");

        List<Payment> result = paymentService.findAllByMethod("Cash on Delivery");
        assertEquals(3, result.size());
    }

    @Test
    void testFindAllByMethodIfMethodCaseSensitive() {
        Payment payment = payments.get(1);
        doReturn(new ArrayList<Payment>()).when(paymentRepository).findAllByMethod(payment.getMethod().toLowerCase());
        List<Payment> result = paymentService.findAllByMethod(payment.getMethod().toLowerCase());
        assertTrue(result.isEmpty());
    }
}