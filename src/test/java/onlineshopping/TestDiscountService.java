package onlineshopping;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import onlineshopping.CartItem;
import onlineshopping.CustomerType;
import onlineshopping.DiscountService;
import onlineshopping.Product;

public class TestDiscountService {

    private DiscountService discountService;
    private CustomerType customerType;
    private CartItem mouse;
    private CartItem laptop;
    private String couponCode = "NoDiscount";


    @BeforeEach
    public void setUp() {
        discountService = new DiscountService();
        customerType = CustomerType.REGULAR;
        mouse = new CartItem(new Product("Mouse", 100.0, 1), 1);
        laptop = new CartItem(new Product("Laptop", 200.0, 1), 1);
    }

    /* TESTS FOR THE REQUIREMENT #3 (Bundle Discount) */

    @Test
    public void testApplyDiscountLaptopOnly() {
        List<CartItem> items = List.of(laptop);
        double total = 200.0; // Price of the laptop is 200.0
        double observed = discountService.applyDiscount(total, customerType, items, couponCode);
        double expected = 200.0;
        assertEquals(expected, observed, "The bundle discount should not be applied, the total should be 200.0");
    }

    @Test
    public void testApplyDiscountMouseOnly() {
        List<CartItem> items = List.of(mouse);
        double total = 100.0; // Price of the mouse is 100.0
        double observed = discountService.applyDiscount(total, customerType, items, couponCode);
        double expected = 100.0;
        assertEquals(expected, observed, "The bundle discount should not be applied, the total should be 100.0");
    }

    @Test
    public void testApplyDiscountBundle() {
        double total = 300.0; // Price of the mouse is 100.0, price of the laptop is 200.0
        List<CartItem> items = List.of(mouse, laptop);
        double observed = discountService.applyDiscount(total, customerType, items, couponCode);
        double expected = 295.0;
        assertEquals(expected, observed, "The 5% bundle discount on the mouse is applied, results are expected to be 200 + (100-0.005*100) = 295");
    }

    @Test
    public void testApplyDiscountBundle2LaptopsQuantities1Mouse() {
        double total = 500.0; // Price of the mouse is 100.0, price of the laptops is 2x200.0
        CartItem laptops = new CartItem(new Product("Laptop", 200.0, 1), 2);
        List<CartItem> items = List.of(mouse, laptops);
        double observed = discountService.applyDiscount(total, customerType, items, couponCode);
        double expected = 495.0;
        assertEquals(expected, observed, "Adding two laptops together (quantity 2). It is expected to be 2*200 + 100-(0.05*100) = 495");
    }


    @Test
    public void testApplyDiscountBundle2LaptopItems1Mouse() {
        double total = 500.0; // Price of the mouse is 100.0, price of the laptops is 2x200.0
        List<CartItem> items = List.of(mouse, laptop, laptop);
        double observed = discountService.applyDiscount(total, customerType, items, couponCode);
        double expected = 495.0;
        assertEquals(expected, observed, "Adding two laptops separately. It is expected to be 2*200 + 100-(0.05*100) = 495");
    }

    @Test
    public void testApplyDiscountBundle2MousesQuantities1Laptop() {
        double total = 700.0; // Price of the mouses is 2x100.0, price of the laptop is 500.0
        CartItem mouses = new CartItem(new Product("Mouse", 100.0, 1), 2);
        List<CartItem> items = List.of(mouses, laptop);
        double observed = discountService.applyDiscount(total, customerType, items, couponCode);
        double expected = 695.0;
        assertEquals(expected, observed, "Adding two mouses together (quantity 2). It is expected to be 500 + 100 + (100-0.05*100) = 695");
    }

    @Test
    public void testApplyDiscountBundle2MouseItems1Laptop() {
        double total = 700.0; // Price of the mouses is 2x100.0, price of the laptop is 500.0
        List<CartItem> items = List.of(mouse, mouse, laptop);
        double observed = discountService.applyDiscount(total, customerType, items, couponCode);
        double expected = 695.0;
        assertEquals(expected, observed, "Adding two mouses separately. It is expected to be 500 + 100 + (100-0.05*100) = 695");
    }

    @Test
    public void testApplyDiscountBundle2Mouses2LaptopsQuantities() {
        double total = 900.0; // Price of the mouses is 2x100.0, price of the laptops is 2x200.0
        CartItem mouses = new CartItem(new Product("Mouse", 100.0, 1), 2);
        CartItem laptops = new CartItem(new Product("Laptop", 200.0, 1), 2);
        List<CartItem> items = List.of(mouses, laptops);
        double observed = discountService.applyDiscount(total, customerType, items, couponCode);
        double expected = 890.0;
        assertEquals(expected, observed, "Adding two mouses and two laptops together. It is expected to be 2*200 + 2*100 - 0.05*100 = 890");
    }

    @Test
    public void testApplyDiscountBundleZeroTotal() {
        double total = 0.0;
        CartItem mouses = new CartItem(new Product("Mouse", 100.0, 1), 1);
        CartItem laptops = new CartItem(new Product("Laptop", 200.0, 1), 1);
        List<CartItem> items = List.of(mouses, laptops);
        double observed = discountService.applyDiscount(total, customerType, items, couponCode);
        double expected = 0.0;
        assertEquals(expected, observed, "The total is 0.0, no discount should be applied, the final price should be 0.0");
    }

    @Test
    public void testApplyDiscountBundleItemsZero() {
        double total = 0.0;
        CartItem mouses = new CartItem(new Product("Mouse", 0.0, 1), 1);
        CartItem laptops = new CartItem(new Product("Laptop", 0.0, 1), 1);
        List<CartItem> items = List.of(mouses, laptops);
        double observed = discountService.applyDiscount(total, customerType, items, couponCode);
        double expected = 0.0;
        assertEquals(expected, observed, "The total is 0.0, no discount should be applied, the final price should be 0.0");
    }

    @Test
    public void testApplyDiscountNegativeTotal() {
        double total = -100.0;
        CartItem mouses = new CartItem(new Product("Item1", 100.0, 1), 1);
        CartItem laptops = new CartItem(new Product("Item2", 200.0, 1), 1);
        List<CartItem> items = List.of(mouses, laptops);
        Exception exception = assertThrows(Exception.class, () -> {
            discountService.applyDiscount(total, customerType, items, couponCode);
        });
    }   

    /* TESTS FOR THE REQUIREMENT #4 (Tier Discount) */

    private List<CartItem> dummyItemList;

    @BeforeEach
    public void createDummyItemList() {
        dummyItemList = List.of(); // Initialize the shared list here
    }

    @Test
    public void testApplyDiscountEdgeCase1000() {
        double total = 1000.0;
        double observed = discountService.applyDiscount(total, customerType, dummyItemList, couponCode);
        double expected = 1000.0;
        assertEquals(expected, observed, "The total is 1000.0, no discount should be applied, the final price should be 1000.0");
    }
    @Test
    public void testApplyDiscountTierAbove1000Below5000() {
        double total = 2000.0;
        double observed = discountService.applyDiscount(total, customerType, dummyItemList, couponCode);
        double expected = 1800.0;
        assertEquals(expected, observed, "The total is 2000.0, the discount should be 10% (2000*0.10 = 200), the final price should be 2000-200 = 1800");
    }
    @Test
    public void testApplyDiscountEdgeCase5000() {
        double total = 5000.0;
        double observed = discountService.applyDiscount(total, customerType, dummyItemList, couponCode);
        double expected = 4500.0;
        assertEquals(expected, observed, "The total is 5000.0, the discount should be 10% (5000*0.10 = 500), the final price should be 5000-500 = 4500");
    }

    @Test
    public void testApplyDiscountAbove5000Below10000() {
        double total = 7000.0;
        double observed = discountService.applyDiscount(total, customerType, dummyItemList, couponCode);
        double expected = 5950.0;
        assertEquals(expected, observed, "The total is 7000.0, the discount should be 15% (7000*0.15 = 1050), the final price should be 7000-1050 = 5950");
    }

    @Test
    public void testApplyDiscountEdgeCase10000() {
        double total = 10000.0;
        double observed = discountService.applyDiscount(total, customerType, dummyItemList, couponCode);
        double expected = 8000.0;
        assertEquals(expected, observed, "The total is 10000.0, the discount should be 20% (10000*0.20 = 2000), the final price should be 10000-2000 = 8000");
    }

    @Test
    public void testApplyDiscountAbove10000() {
        double total = 12000.0;
        double observed = discountService.applyDiscount(total, customerType, dummyItemList, couponCode);
        double expected = 9600.0;
        assertEquals(expected, observed, "The total is 12000.0, the discount should be 20% (12000*0.20 = 2400), the final price should be 12000-2400 = 9600");
    }

    @Test
    public void testApplyDiscountEdgeCaseTierAndBundle() {
        double total = 5003.0;
        CartItem mouse = new CartItem(new Product("Mouse", 100.0, 1), 1);
        CartItem laptop = new CartItem(new Product("Laptop", 200.0, 1), 1);
        List<CartItem> items = List.of(mouse, laptop);
        double observed = discountService.applyDiscount(total, customerType, items, couponCode);
        double expected = 4248.3;
        assertEquals(expected, observed, "The total is 5003.0, the bundle discount should be applied to the mouse (0.05*100 = 5), the discount should be 15% (>5000), the final price should be (5003-5)*(1-0.15) = 4248.3");
    }


    /* TESTS FOR THE REQUIREMENT #5 (Customer-type Discount) */

    @Test
    public void testApplyDiscountPremium() {
        double total = 1000.0;
        double observed = discountService.applyDiscount(total, CustomerType.PREMIUM, dummyItemList, couponCode);
        double expected = 950.0;
        assertEquals(expected, observed, "The total is 1000.0, the discount should be 5% (1000*0.05 = 50), the final price should be 1000-50 = 950");
    }

    @Test
    public void testApplyDiscountVIP() {
        double total = 1000.0;
        double observed = discountService.applyDiscount(total, CustomerType.VIP, dummyItemList, couponCode);
        double expected = 900.0;
        assertEquals(expected, observed, "The total is 1000.0, the discount should be 10% (1000*0.10 = 100), the final price should be 1000-100 = 900");
    }

    /* TESTS FOR THE REQUIREMENT #6 (Coupon Discount) */
    @Test 
    public void testApplyDiscountCouponCodeDiscount10() {
        double total = 1000.0;
        String couponCode = "DISCOUNT10";
        double observed = discountService.applyDiscount(total, customerType, dummyItemList, couponCode);
        double expected = 900.0;
        assertEquals(expected, observed, "The total is 1000.0, the discount should be 10% (1000*0.10 = 100), the final price should be 1000-100 = 900");
    }

    @Test
    public void testApplyDiscountCouponCodeSave50() {
        double total = 10100.0;
        String couponCode = "SAVE50";
        double observed = discountService.applyDiscount(total, customerType, dummyItemList, couponCode);
        double expected = 8040.0;
        assertEquals(expected, observed, "The total is 10100.0, the 20% discount should be determined (>10000), the fixed amount discount of 50 should be applied before the 20% discount, the final price should be (10100-50)*(1-0.2) = 8040");
    }

    @Test
    public void testApplyDiscountEdgeCaseTierCouponCodeSave50() {
        double total = 1020.0;
        String couponCode = "SAVE50";
        double observed = discountService.applyDiscount(total, customerType, dummyItemList, couponCode);
        double expected = 873.0;
        assertEquals(expected, observed, "The total is 1020.0, the 10% discount should be determined (>1000), the fixed amount discount of 50 should be applied before the 10% discount, the final price should be (1020-50)*(1-0.1) = 873");
    }

    @Test
    public void testApplyDiscountNonValidCoupon() {
        double total = 1000.0;
        String couponCode = "INVALID";
        double observed = discountService.applyDiscount(total, customerType, dummyItemList, couponCode);
        double expected = 1000.0;
        assertEquals(expected, observed, "The total is 1000.0, no discount should be applied, the final price should be 1000.0");
    }

    @Test
    public void testApplyDiscountNull() {
        double total = 1000.0;
        String couponCode = null;
        double observed = discountService.applyDiscount(total, customerType, dummyItemList, couponCode);
        double expected = 1000.0;
        assertEquals(expected, observed, "The total is 1000.0, no discount should be applied, the final price should be 1000.0");
    }

    @Test
    public void testApplyDiscountEmptyString() {
        double total = 1000.0;
        String couponCode = "";
        double observed = discountService.applyDiscount(total, customerType, dummyItemList, couponCode);
        double expected = 1000.0;
        assertEquals(expected, observed, "The total is 1000.0, no discount should be applied, the final price should be 1000.0");
    }

    /* TESTS FOR THE REQUIREMENT #7 (Promotion Discount) */

    @Test
    public void testApplyPromotionDiscount() {
        double total = 1000.0;
        double observed = discountService.applyPromotionDiscount(total);
        double expected = 750.0;
        assertEquals(expected, observed, "The total is 1000.0, the promotion discount should be 25% (1000*0.25 = 250), the final price should be 1000-250 = 750");
    }
    
}