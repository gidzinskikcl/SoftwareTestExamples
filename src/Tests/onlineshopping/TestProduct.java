package tests.onlineshopping;

import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import onlineshopping.Product;

public class TestProduct {

    private Product product;

    @Before
    public void setUp() {
        product = new Product("ProductName", 100.0, 10);

    }

    @Test
    public void testGetName()  {
        assertTrue("The name should be ProductName", product.getName().equals("ProductName"));
    }

    @Test
    public void testGetPrice() {
        assertTrue("The price should be 100.0",product.getPrice() == 100.0);
    }

    @Test
    public void testSetGetStock() {
        product.setStock(20);
        assertTrue("The stock should be 20", product.getStock() == 20);
    }

    @Test
    public void testReduceStock() {
        product.reduceStock(4);
        assertTrue("The stock should be 10-4=6", product.getStock() == 6);
    }

    @Test
    public void testReduceStockNotEnoughStock() {
        // Not enough stock available, it should throw an exception
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            product.reduceStock(11);
        }); 
    }

    
}
