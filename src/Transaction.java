import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

class Transaction {
    private LocalDateTime dateTime;
    private double amount;
    private String category;

    public Transaction(double amount, String category) {
        this.dateTime = LocalDateTime.now();
        this.amount = amount;
        this.category = category;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public double getAmount() {
        return amount;
    }

    public String getCategory() {
        return category;
    }

    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return "[" + dateTime.format(formatter) + "] " + category + ": " + (amount > 0 ? "+" : "") + amount;
    }
}