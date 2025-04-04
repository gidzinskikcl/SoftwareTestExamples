package onlineshopping;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;


import onlineshopping.CartItem;
import onlineshopping.Product;

public class TestCartItem {

    private CartItem cartItem;

    @Test
    public void testGetProduct() {
        Product mockProduct = mock(Product.class);
        when(mockProduct.getPrice()).thenReturn(100.0);
        int quantity1 = 2;
        cartItem = new CartItem(mockProduct, quantity1);
        Product expected = mockProduct;
        Product observed = cartItem.getProduct();
        assertTrue(expected.equals(observed), () -> "The observed product should be the mockProduct");
    }

    @Test
    public void testGetQuantity() {
        Product mockProduct = mock(Product.class);
        when(mockProduct.getPrice()).thenReturn(100.0);
        int quantity1 = 2;
        cartItem = new CartItem(mockProduct, quantity1);
        int expected = quantity1;
        int observed = cartItem.getQuantity();
        assertTrue(expected == observed, () -> "The observed quantity should be 2");
    }

    @Test
    public void testGetTotalPrice() {
        Product mockProduct = mock(Product.class);
        when(mockProduct.getPrice()).thenReturn(100.0);
        int quantity1 = 2;
        cartItem = new CartItem(mockProduct, quantity1);
        double expected = 200.0;
        double observed = cartItem.getTotalPrice();
        assertTrue(expected == observed, "The total price should be 200.0");
    }
}
