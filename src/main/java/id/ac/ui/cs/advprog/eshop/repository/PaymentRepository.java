package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Payment;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

@Repository
public class PaymentRepository {
    private List<Payment> paymentData = new ArrayList<>();

    public Payment save(Payment payment) {
        int i = 0;
        for (Payment savedPayment : paymentData) {
            if (savedPayment.getId().equals(payment.getId())) {
                paymentData.remove(i);
                paymentData.add(i, payment);
                return payment;
            }
            i += 1;
        }

        paymentData.add(payment);
        return payment;
    }
    public Payment findById(String id) {
        for (Payment savedPayment : paymentData) {
            if (savedPayment.getId().equals(id)) {
                return savedPayment;
            }
        }
        return null;
    }
    public List<Payment> findAllByMethod(String orderId) {
        List<Payment> result = new ArrayList<>();
        for (Payment savedPayment : paymentData) {
            if (savedPayment.getMethod().equals(orderId)) {
                result.add(savedPayment);
            }
        }
        return result;
    }
}
