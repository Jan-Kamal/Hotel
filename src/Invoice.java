import java.time.LocalDate;

public class Invoice {
    public enum PaymentMethod { CASH, CREDIT_CARD, ONLINE }

    private double amount;
    private PaymentMethod method;
    private LocalDate date;

    public Invoice(double amount, PaymentMethod method) {
        this.amount = amount;
        this.method = method;
        this.date = LocalDate.now();
    }

    public void printReceipt() {
        System.out.println("\n------- PAYMENT RECEIPT -------");
        System.out.println("Date: " + date);
        System.out.println("Total Paid: $" + amount);
        System.out.println("Method Used: " + method);
        System.out.println("Status: COMPLETED");
        System.out.println("--------------------------------");
    }
}