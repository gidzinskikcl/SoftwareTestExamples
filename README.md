# A Demonstration of Software Testing in Java

This simple project was created to demonstrate my approach to software testing in Java, utilising a variety of test cases and techniques.

This project includes:

- **Test Case/Suite Design:** A series of unit tests targeting specific functionality within the software (specified in the system requirement below), written using JUnit and Mockito.

  - **Note:** some tests fail on purpose in order to demonstrate how tests helped detecting faults in the software.

- **Fault Detection and Debugging: (to be continued)** A detailed analysis of potential software faults, their identification through tests, and explanations of how the issues were uncovered.

- **Coverage Analysis: (to be continued)** Generating test coverage to measure how much of the software has been tested and to highlight areas that may need additional attention (VSCode feature).

<u>**Below I explain the software system under test and the user requirements**</u>

## Brief Software Description

The Shopping Cart system is an online platform designed to provide a seamless purchasing experience. It allows users to browse products, add them to their cart, and accurately calculate the total cost. The system includes several discount mechanisms, such as tiered discounts based on cart value, customer-specific pricing (Regular, Premium, or VIP), and bundle discounts for certain product combinations. Additionally, it supports time-limited promotions and coupon codes for extra savings.


## System Requirements:

**1. Shopping Cart Functionality**
- Users shall be able to browse and select products to add to their shopping cart.

**2. Total Price Calculation**
- The system shall calculate the total price of the items in the cart before applying any discounts.

**3. Bundle Discounts**
- The system shall support additional bundle discounts, such as 5% off a mouse when a laptop is also in the cart.

**4. Tiered Discounts**
- The system shall apply tiered discounts based on the cart value:
  - 10% off for cart values over £1,000
  - 15% off for cart values over £5,000
  - 20% off for cart values over £10,000

**5. Customer Categories and Discounts**
- Customers shall be categorized as **Regular**, **Premium**, or **VIP**:
  - Premium customers receive an additional 5% discount.
  - VIP customers receive an additional 10% discount.

**6. Coupon Code Support**
- Users shall be able to enter a coupon code for extra discounts:
  - Percentage-based discounts (e.g., 10% off with code "DISCOUNT10")
  - Fixed-amount discounts (e.g., £50 off with code "SAVE50")
  - Only one coupon code may be applied per transaction.
  - The £50 off coupon is applied before any percentage-based discounts.

**7. Time-Limited Promotions**
- The system shall support time-limited promotions that apply a flat discount (e.g., 25% off during a promotional event), which can be activated or deactivated.

**8. Cumulative Discounts**
- Discounts from points 3 to 7 shall be applied cumulatively, in the order specified.

**9. Receipt Generation**
- The system shall print a detailed receipt summarizing the items in the cart, the total price before discounts, and the final price after all discounts.

**10. Credit Card Validation**
- The system shall validate credit card numbers to ensure they meet the 16-digit requirement.
- It shall also check that the transaction amount is positive.
- If either condition is not met, an error message shall prevent the transaction from proceeding.
