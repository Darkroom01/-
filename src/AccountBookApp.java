import java.util.*;
import java.time.LocalDateTime;

public class AccountBookApp {
    public static void main(String[] args) {
        Register register = new Register();
        Scanner scanner = new Scanner(System.in);

        int option;
        do {
            System.out.println("1. Sign Up");
            System.out.println("2. Log In");
            System.out.println("3. Exit");
            System.out.print("Select an option: ");
            option = scanner.nextInt();
            scanner.nextLine(); 

            switch (option) {
                case 1:
                    System.out.print("Enter username: ");
                    String username = scanner.nextLine();
                    if (register.isMemberExists(username)) {
                        System.out.println("Username already exists. Please choose a different username.");
                        break;
                    }

                    System.out.print("Enter password: ");
                    String password = scanner.nextLine();

                    System.out.print("Enter initial balance: ");
                    double balance = scanner.nextDouble();
                    scanner.nextLine();

                    register.addMember(username, password, balance);
                    System.out.println("Registration successful!");
                    break;

                case 2:
                    System.out.print("Enter username: ");
                    username = scanner.nextLine();
                    if (!register.isMemberExists(username)) {
                        System.out.println("Username does not exist. Please sign up first.");
                        break;
                    }

                    System.out.print("Enter password: ");
                    password = scanner.nextLine();

                    if (register.isLoginValid(username, password)) {
                        System.out.println("Login successful!");

                        Member member = register.getMemberByUsername(username);
                        performAccountActions(member, scanner);
                    } else {
                        System.out.println("Invalid credentials. Please try again.");
                    }
                    break;

                case 3:
                    System.out.println("Exiting the program...");
                    break;

                default:
                    System.out.println("Invalid option. Please select a valid option.");
                    break;
            }
            System.out.println();
        } while (option != 3);

        scanner.close();
    }

    public static void performAccountActions(Member member, Scanner scanner) {
        int option;
        do {
            System.out.println("1. Deposit");
            System.out.println("2. Withdraw");
            System.out.println("3. View Transactions");
            System.out.println("4. Calculate Weekly Deposits and Withdrawals");
            System.out.println("5. Back to Main Menu");
            System.out.print("Select an option: ");
            option = scanner.nextInt();
            scanner.nextLine();

            switch (option) {
                case 1:
                    System.out.print("Enter amount to deposit: ");
                    double depositAmount = scanner.nextDouble();
                    scanner.nextLine();

                    System.out.print("Enter category: ");
                    String depositCategory = scanner.nextLine();

                    member.deposit(depositAmount, depositCategory);
                    System.out.println("Deposit successful!");
                    break;

                case 2:
                    System.out.print("Enter amount to withdraw: ");
                    double withdrawAmount = scanner.nextDouble();
                    scanner.nextLine();

                    System.out.print("Enter category: ");
                    String withdrawCategory = scanner.nextLine();

                    if (withdrawAmount > member.getBalance()) {
                        System.out.println("Insufficient balance. Withdrawal failed.");
                    } else {
                        member.withdraw(withdrawAmount, withdrawCategory);
                        System.out.println("Withdrawal successful!");
                    }
                    break;

                case 3:
                    System.out.println("Transaction History:");
                    List<Transaction> transactions = member.getTransactions();
                    if (transactions.isEmpty()) {
                        System.out.println("No transactions available.");
                    } else {
                        double currentBalance = member.getBalance();
                        System.out.println("----------");
                        for (Transaction transaction : transactions) {
                            System.out.println(transaction);
                            currentBalance += transaction.getAmount();
                        }
                        System.out.println("----------");
                        System.out.println("Modified Current Balance: " + currentBalance);
                    }
                    break;

                case 4:
                    System.out.println("Calculating Weekly Deposits and Withdrawals...");
                    double totalDeposits = 0;
                    double totalWithdrawals = 0;
                    LocalDateTime now = LocalDateTime.now();
                    LocalDateTime oneWeekAgo = now.minusWeeks(1);
                    for (Transaction transaction : member.getTransactions()) {
                        LocalDateTime transactionDateTime = transaction.getDateTime();
                        if (transactionDateTime.isAfter(oneWeekAgo) && transactionDateTime.isBefore(now)) {
                            if (transaction.getAmount() > 0) {
                                totalDeposits += transaction.getAmount();
                            } else {
                                totalWithdrawals += Math.abs(transaction.getAmount());
                            }
                        }
                    }
                    System.out.println("Total Deposits in the Last Week: " + totalDeposits);
                    System.out.println("Total Withdrawals in the Last Week: " + totalWithdrawals);
                    break;

                case 5:
                    System.out.println("Returning to the main menu...");
                    break;

                default:
                    System.out.println("Invalid option. Please select a valid option.");
                    break;
            }
            System.out.println();
        } while (option != 5);
    }
}
