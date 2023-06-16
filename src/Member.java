import java.io.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

class Member {
    private String username;
    private String password;
    private double balance;
    private List<Transaction> transactions;

    public Member(String username, String password, double balance) {
        this.username = username;
        this.password = password;
        this.balance = balance;
        this.transactions = new ArrayList<>();
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public double getBalance() {
        return balance;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void deposit(double amount, String category) {
        balance += amount;
        Transaction transaction = new Transaction(amount, category);
        transactions.add(transaction);
        saveTransactions();
    }

    public void withdraw(double amount, String category) {
        balance -= amount;
        Transaction transaction = new Transaction(-amount, category);
        transactions.add(transaction);
        saveTransactions();
    }

    public void saveTransactions() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(username + ".txt"))) {
            for (Transaction transaction : transactions) {
                writer.println(transaction.getDateTime() + "," + transaction.getAmount() + "," + transaction.getCategory());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadTransactions() {
        try (BufferedReader reader = new BufferedReader(new FileReader(username + ".txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] transactionData = line.split(",");
                LocalDateTime dateTime = LocalDateTime.parse(transactionData[0]);
                double amount = Double.parseDouble(transactionData[1]);
                String category = transactionData[2];
                Transaction transaction = new Transaction(amount, category);
                transactions.add(transaction);
            }
        } catch (IOException e) {
        }
    }
}