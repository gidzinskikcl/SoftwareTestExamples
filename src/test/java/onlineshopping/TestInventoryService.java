package onlineshopping;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import onlineshopping.CartItem;
import onlineshopping.InventoryService;
import onlineshopping.Product;

public class TestInventoryService {

    private InventoryService inventoryService;

    @BeforeEach
    public void setUp() {
        inventoryService = new InventoryService();
    }

    @Test
    public void testUpdateStock() {
        CartItem mockItem = mock(CartItem.class);
        Product mockProduct = mock(Product.class);
        when(mockItem.getProduct()).thenReturn(mockProduct);
        when(mockItem.getQuantity()).thenReturn(2);
        doNothing().when(mockProduct).reduceStock(2);

        inventoryService.updateStock(mockItem);
        verify(mockProduct).reduceStock(2); // verify that reduceStock was called with 2
    }

    @Test
    public void testUpdateStockNotEnoughStock() {
        CartItem mockItem = mock(CartItem.class);
        Product mockProduct = mock(Product.class);
        when(mockItem.getProduct()).thenReturn(mockProduct);
        when(mockItem.getQuantity()).thenReturn(2);
        doThrow(new IllegalArgumentException()).when(mockProduct).reduceStock(2);
        // Not enough stock available, it should throw an exception
        Exception exception = assertThrows(Exception.class, () -> {
            inventoryService.updateStock(mockItem);
        });

    }
}
