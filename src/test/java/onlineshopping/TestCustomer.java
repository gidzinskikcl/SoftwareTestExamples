package onlineshopping;

import static org.junit.jupiter.api.Assertions.assertTrue;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import onlineshopping.Customer;
import onlineshopping.CustomerType;

public class TestCustomer {

    private Customer customer;
    @BeforeEach
    public void setUp() {
        customer = new Customer("CustomerName", CustomerType.REGULAR);
    }

    @Test
    public void testSetGetName() {
        customer.setName("NewName");
        assertTrue( customer.getName().equals("NewName"), () -> "The name should be NewName");
    }


    @Test
    public void testSetGetCusomterType() {
        customer.setCustomerType(CustomerType.PREMIUM);
        assertTrue( customer.getCustomerType().equals(CustomerType.PREMIUM), () -> "The customer type should be premium");
    }

    
}
