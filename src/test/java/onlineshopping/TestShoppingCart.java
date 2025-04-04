package onlineshopping;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import onlineshopping.CartItem;
import onlineshopping.Customer;
import onlineshopping.CustomerType;
import onlineshopping.DiscountService;
import onlineshopping.Product;
import onlineshopping.ShoppingCart;

public class TestShoppingCart {

    private ShoppingCart cart;
    private CartItem item1;
    private CartItem item2;


    @BeforeEach
    public void createShoppingCart() {
        Customer customer = new Customer("Mr White", CustomerType.REGULAR);
        DiscountService discountService = new DiscountService();
        cart = new ShoppingCart(customer, discountService);
    }

    @BeforeEach
    public void createItems() {
        Product product1 = new Product("Arbitrary Item", 1000.0, 10);
        item1 = new CartItem(product1, 1);
        
        Product product2 = new Product("Another Arbitrary Item", 2000.0, 10);
        item2 = new CartItem(product2, 1);
    }

    /* TESTS FOR THE REQUIREMENT #1 (Adding, Removing and Viewing Items)*/
    @Test
    public void testCartIsEmpty() {
        List<CartItem> observed = cart.getItems();
        assertTrue(observed.isEmpty(), () -> "The cart should be empty and not throw any errors");
    }
    
    @Test 
    public void testAddItem() {        
        cart.addItem(item1);
        List<CartItem> observed = cart.getItems();

        int expected_size = 1;
        CartItem expected_item = item1;
        assertEquals(expected_size, observed.size(), "The cart should have 1 item");
        assertEquals(expected_item, observed.get(0), "The observed item in the cart should be item1");
    }

    @Test
    public void testAddTwoItems() {
            
            cart.addItem(item1);
            cart.addItem(item2);
            List<CartItem> observed = cart.getItems();

            int expected_size = 2;
            CartItem expected_item1 = item1;
            CartItem expected_item2 = item2;
            assertEquals(expected_size, observed.size(), "The cart should have 2 items");
            assertEquals(expected_item1, observed.get(0), "The observed item at index 0 should be item1");
            assertEquals(expected_item2, observed.get(1), "The observed item at index 1 should be item2");
    }

    @Test
    public void testRemoveItem() {
            
            cart.addItem(item1);
            
            cart.removeItem(item1);
            
            List<CartItem> observed = cart.getItems();
            assertTrue(observed.isEmpty(), () -> "The cart should be empty and not throw any errors");
    } 

    @Test
    public void testAddTwoItemsRemoveOne() {
                
                cart.addItem(item1);
                cart.addItem(item2);
                
                cart.removeItem(item1);
                
                List<CartItem> observed = cart.getItems();
                assertEquals(1, observed.size(), "The cart should have 1 item");
                assertEquals(item2, observed.get(0), "The observed item in the cart should be item2");
    }

    @Test
    public void testAddTwoItemsRemoveTwo() {
                
                cart.addItem(item1);
                cart.addItem(item2);
                
                cart.removeItem(item1);
                cart.removeItem(item2);
                
                List<CartItem> observed = cart.getItems();
                assertTrue(observed.isEmpty(), () -> "The cart should be empty and not throw any errors");
    }

    @Test
    public void testRemoveFromEmptyCart() {
        assertDoesNotThrow(() -> cart.removeItem(item1), "The cart should not throw any errors when removing an item from an empty cart");
        
        List<CartItem> observed = cart.getItems();
        assertTrue(observed.isEmpty(), () -> "The cart should be empty and not throw any errors");
    }

    @Test
    public void testAddTheSameItemTwice() {
        assertDoesNotThrow(() -> {
            cart.addItem(item1);
            cart.addItem(item1);
        }, "The cart should not throw any errors when adding the same item twice");
        
        List<CartItem> observed = cart.getItems();
        assertEquals(item1, observed.get(0), "The observed item in the cart at index 0 should be item1");
        assertEquals(2, observed.get(0).getQuantity(), "The observed item in the cart at index 0 should have the quantity of 2");
    }

    /* TESTS FOR THE REQUIREMENT #2 (Calculate Total Price Before Discounts) */
    @Test
    public void testCalculateTotalEmptyCart() {
        double observed = cart.calculateTotal();
        double expected = 0.0;
        assertEquals(expected, observed, "The empty cart should have the total 0");
    }

    @Test
    public void testCalculateTotalCartOneItem() {
        cart.addItem(item1);
        double observed = cart.calculateTotal();
        double expected = 1000.0;
        assertEquals(expected, observed, "The cart should have the total 1000");
    }

    @Test
    public void testCalculateTotalCartWithTwoItems() {
        cart.addItem(item1);
        cart.addItem(item2);
        double observed = cart.calculateTotal();
        double expected = 3000.0;
        assertEquals(expected, observed, 0.0);

    }

    @Test
    public void testCalculateTotalItemWithTwoQuantities() {
        CartItem itemWithTwoQuantities = new CartItem(item1.getProduct(), 2);
        cart.addItem(itemWithTwoQuantities);
        double observed = cart.calculateTotal();
        double expected = 2000.0;
        assertEquals(expected, observed,"The cart should have the total 2000");
    }

    @Test
    public void testCalculateTotalAfterAddingOneMore() {
        cart.addItem(item1);
        cart.calculateTotal();

        cart.addItem(item2);
        double observed = cart.calculateTotal();
        double expected = 3000.0;
        assertEquals(expected, observed, "The cart should have the total 3000");
    }

    @Test
    public void testCalculateTotalAfterRemovingOne() {
        cart.addItem(item1);
        cart.addItem(item2);
        cart.calculateTotal();

        cart.removeItem(item1);
        double observed = cart.calculateTotal();
        double expected = 2000.0;
        assertEquals(expected, observed, "The cart should have the total 2000");
    }

    @Test
    public void testCalculateTotalWhenActiveDiscount() {
        cart.addItem(item1);
        cart.addItem(item2);
        cart.calculateTotal();

        cart.applyCouponCode("DISCOUNT10");
        cart.setPromotionActive(true);
        double observed = cart.calculateTotal();
        double expected = 3000.0;
        assertEquals(expected, observed,"Total should remain 3000 after applying coupon and promotion");
    }

    /* TESTS FOR THE REQUIREMENT #8 (Discounts in Order) */
    private ShoppingCart cartWithDiscounts;
    private Customer vipCustomer;


    @BeforeEach
    public void setUpCartWithDiscounts() {
        vipCustomer = new Customer("Mr White", CustomerType.VIP);
        cartWithDiscounts = new ShoppingCart(vipCustomer, new DiscountService());
        CartItem laptopMac = new CartItem(new Product("Laptop", 1000.0, 10), 1);
        CartItem mouseDell = new CartItem(new Product("Mouse", 100.0, 10), 1);
        cartWithDiscounts.addItem(laptopMac);
        cartWithDiscounts.addItem(mouseDell);
        cartWithDiscounts.applyCouponCode("SAVE50");
    }

    @Test
    public void testCalculateFinalPriceWithoutPromotion() {
        double observed = cartWithDiscounts.calculateFinalPrice();
        double expected = 836.0;
        assertEquals(expected, observed, "Total is 1100, first bundle discount is applied (1100-5 = 1095), then coupon is applied (1095-50 = 1045), then 10%+10% discounts are applied, hence we expect 1045*(1-0.2) = 836");
    }

    @Test
    public void testCalculateFinalPriceWithPromotion() {
        cartWithDiscounts.setPromotionActive(true);
        double observed = cartWithDiscounts.calculateFinalPrice();
        double expected = 574.75;
        assertEquals(expected, observed, "Total is 1100, first bundle discount is applied (1100-5 = 1095), then coupon is applied (1095-50 = 1045), then 10%+10%+25% discounts are applied, hence we expect 1045*(1-0.45) = 547.75");

    }
    

    /* TESTS FOR THE REQUIREMENT #9 (Receipt) */
    private ShoppingCart spyCart;

    @BeforeEach
    public void setUpSpyCart() {
        spyCart = spy(new ShoppingCart(new Customer("Mr White", CustomerType.REGULAR), new DiscountService()));
}

    @Test
    public void testPrintReceipt() {
        // Arrange

        // Mock the private methods
        doReturn(40.0).when(spyCart).calculateTotal();
        doReturn(36.0).when(spyCart).calculateFinalPrice();

        // Create a mocked product and cart items
        Product product1 = mock(Product.class);
        Product product2 = mock(Product.class);
        when(product1.getName()).thenReturn("Product 1");
        when(product1.getPrice()).thenReturn(10.0);
        when(product2.getName()).thenReturn("Product 2");
        when(product2.getPrice()).thenReturn(20.0);

        CartItem item1 = mock(CartItem.class);
        CartItem item2 = mock(CartItem.class);
        when(item1.getProduct()).thenReturn(product1);
        when(item1.getQuantity()).thenReturn(2);
        when(item2.getProduct()).thenReturn(product2);
        when(item2.getQuantity()).thenReturn(1);
        spyCart.addItem(item1);
        spyCart.addItem(item2);

        // Capture the printed output
        PrintStream standardOut = System.out;
        ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStreamCaptor));

        // Act
        spyCart.printReceipt();
        String observed = outputStreamCaptor.toString();

        System.setOut(standardOut);

        // Assert: Check if the output matches what we expect
        String expectedOutput = "----- Shopping Cart Receipt -----\n" +
                "Product 1 - 2 x $10.0\n" +
                "Product 2 - 1 x $20.0\n" +
                "---------------------------------\n" +
                "Total before discount: $40.0\n" +
                "Final price after discounts: $36.0\n";

        assertEquals(expectedOutput, observed, "The cart should print the receipt as expected");
    }


}



