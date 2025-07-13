package car_rental_system;

public class Payment {
    private Rental rental;
    private double amount;
    private String paymentMode; // e.g., Cash, UPI, Card

    public Payment(Rental rental, String paymentMode) {
        this.rental = rental;
        this.amount = rental.getCar().calculatePrice(rental.getDays());
        this.paymentMode = paymentMode;
    }

    public void generateReceipt() {
        System.out.println("\n==== PAYMENT RECEIPT ====");
        System.out.println("Customer : " + rental.getCustomer().getName());
        System.out.println("Car      : " + rental.getCar().getBrand() + " " + rental.getCar().getModel());
        System.out.println("Days     : " + rental.getDays());
        System.out.println("Mode     : " + paymentMode);
        System.out.printf("Amount   : ₹%.2f%n", amount);
        System.out.println("==========================");
    }

    public double getAmount() {
        return amount;
    }

    public String getPaymentMode() {
        return paymentMode;
    }
}