package onlineshopping;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import onlineshopping.Product;

public class TestProduct {

    private Product product;

    @BeforeEach
    public void setUp() {
        product = new Product("ProductName", 100.0, 10);

    }

    @Test
    public void testGetName()  {
        assertTrue(product.getName().equals("ProductName"), "The name should be ProductName");
    }

    @Test
    public void testGetPrice() {
        assertTrue(product.getPrice() == 100.0, "The price should be 100.0");
    }

    @Test
    public void testSetGetStock() {
        product.setStock(20);
        assertTrue( product.getStock() == 20, "The stock should be 20");
    }

    @Test
    public void testReduceStock() {
        product.reduceStock(4);
        assertTrue(product.getStock() == 6, "The stock should be 10-4=6");
    }

    @Test
    public void testReduceStockNotEnoughStock() {
        // Not enough stock available, it should throw an exception
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            product.reduceStock(11);
        }); 
    }

    
}