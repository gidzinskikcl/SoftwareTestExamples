package tests.onlineshopping;

import static org.junit.Assert.assertThrows;

import org.junit.Before;
import org.junit.Test;

import onlineshopping.PaymentService;

public class TestPaymentService {
    
    private PaymentService paymentService;

    @Before
    public void setUp() {
        paymentService = new PaymentService();
    }

    /*  TESTS FOR THE REQUIREMENT #10 (Display Error During Incorrect Payment) */

    @Test
    public void processPaymentShortDigitNegativeAmount() {
        Exception exception = assertThrows(Exception.class, () -> {
            paymentService.processPayment("1234", -1000.0);
        });
    }

    @Test
    public void processPaymentShortDigit() {
        Exception exception = assertThrows(Exception.class, () -> {
            paymentService.processPayment("1234", 1000.0);
        });
    }

    @Test
    public void processPaymentCorrectLengthButNotDigit() {
        Exception exception = assertThrows(Exception.class, () -> {
            paymentService.processPayment("abcdefghijklmnop", 1000.0);
        });

    }

    @Test
    public void processPaymentAmountZero() {
        Exception exception = assertThrows(Exception.class, () -> {
            paymentService.processPayment("1234567890123456", 0.0);
        });
    }

    @Test
    public void processPaymentAmountNegative() {
        Exception exception = assertThrows(Exception.class, () -> {
            paymentService.processPayment("1234567890123456", -1000.0);
        });
    }

    @Test
    public void processPaymentCorrect() {
        try {
            assert(paymentService.processPayment("1234567890123456", 1000.0));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
