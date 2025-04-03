package tests.onlineshopping;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import onlineshopping.Customer;
import onlineshopping.CustomerType;

public class TestCustomer {

    private Customer customer;
    @Before
    public void setUp() {
        customer = new Customer("CustomerName", CustomerType.REGULAR);
    }

    @Test
    public void testSetGetName() {
        customer.setName("NewName");
        assertTrue("The name should be NewName", customer.getName().equals("NewName"));
    }


    @Test
    public void testSetGetCusomterType() {
        customer.setCustomerType(CustomerType.PREMIUM);
        assertTrue("The customer type should be premium", customer.getCustomerType().equals(CustomerType.PREMIUM));    
    }

    
}
