package Tests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Arrays;

import org.junit.Test;

import onlineshopping.CartItem;
import onlineshopping.InventoryService;
import onlineshopping.OrderService;
import onlineshopping.PaymentService;
import onlineshopping.ShoppingCart;

public class TestOrderService {

    private OrderService orderService;

    @Test
    public void testPlaceOrder() throws Exception {
        PaymentService mockPaymentService = mock(PaymentService.class);
        InventoryService mockInventoryService = mock(InventoryService.class);

        ShoppingCart mockCart = mock(ShoppingCart.class);
        String cardNumber = "1234567890123456";

        CartItem mockItem = mock(CartItem.class);
        when(mockCart.getItems()).thenReturn(Arrays.asList(mockItem));
        when(mockCart.calculateTotal()).thenReturn(100.0);

        doNothing().when(mockInventoryService).updateStock(mockItem);
        when(mockPaymentService.processPayment(cardNumber, 100.0)).thenReturn(true);

        orderService = new OrderService(mockPaymentService, mockInventoryService);
        assertTrue("The order should be placed", orderService.placeOrder(mockCart, cardNumber));
    }

    @Test
    public void testPlaceOrderThrowExceptionPayment() throws Exception {
        PaymentService mockPaymentService = mock(PaymentService.class);
        InventoryService mockInventoryService = mock(InventoryService.class);

        ShoppingCart mockCart = mock(ShoppingCart.class);
        String cardNumber = "1234567890123456";

        CartItem mockItem = mock(CartItem.class);
        when(mockCart.getItems()).thenReturn(Arrays.asList(mockItem));
        when(mockCart.calculateTotal()).thenReturn(100.0);

        doNothing().when(mockInventoryService).updateStock(mockItem);
        when(mockPaymentService.processPayment(cardNumber, 100.0)).thenThrow(new Exception());

        orderService = new OrderService(mockPaymentService, mockInventoryService);
        assertFalse("Placing order shoud not happen", orderService.placeOrder(mockCart, cardNumber));
    }

    @Test
    public void testPlaceOrderThrowExceptionInventory() throws Exception {
        PaymentService mockPaymentService = mock(PaymentService.class);
        InventoryService mockInventoryService = mock(InventoryService.class);

        ShoppingCart mockCart = mock(ShoppingCart.class);
        String cardNumber = "1234567890123456";

        CartItem mockItem = mock(CartItem.class);
        when(mockCart.getItems()).thenReturn(Arrays.asList(mockItem));
        when(mockCart.calculateTotal()).thenReturn(100.0);

        doNothing().when(mockInventoryService).updateStock(mockItem);
        doThrow(new IllegalArgumentException()).when(mockInventoryService).updateStock(mockItem);

        orderService = new OrderService(mockPaymentService, mockInventoryService);
        assertFalse("Placing order should not hapen", orderService.placeOrder(mockCart, cardNumber));
    }

    @Test
    public void testPlaceOrderThrowExceptionCalculateTotal() throws Exception {
        PaymentService mockPaymentService = mock(PaymentService.class);
        InventoryService mockInventoryService = mock(InventoryService.class);

        ShoppingCart mockCart = mock(ShoppingCart.class);
        String cardNumber = "1234567890123456";

        CartItem mockItem = mock(CartItem.class);
        when(mockCart.getItems()).thenReturn(Arrays.asList(mockItem));

        doNothing().when(mockInventoryService).updateStock(mockItem);
        doThrow(new IllegalArgumentException()).when(mockCart).calculateTotal();

        orderService = new OrderService(mockPaymentService, mockInventoryService);
        assertFalse("Placing order should not happen", orderService.placeOrder(mockCart, cardNumber));
    }
}
