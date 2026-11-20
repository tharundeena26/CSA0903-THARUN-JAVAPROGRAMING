import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;

class InsufficientFundsException extends Exception {
    InsufficientFundsException(String message) { super(message); }
}

class InvalidWithdrawalException extends IllegalArgumentException {
    InvalidWithdrawalException(String message) { super(message); }
}

public class ATMSimulation {
    private static final double BALANCE = 5000.00;

    private static void withdraw(String account, double amount)
            throws IOException, InsufficientFundsException {
        String cardStatus = Files.readString(Path.of("card-reader.txt")).trim();
        if (!cardStatus.equalsIgnoreCase("READY")) {
            throw new IOException("Card reader is not ready");
        }
        if (amount <= 0) {
            throw new InvalidWithdrawalException("Withdrawal amount must be positive");
        }
        if (amount > BALANCE) {
            throw new InsufficientFundsException("Insufficient account balance");
        }
        System.out.printf("Account %s: cash dispensed Rs.%.2f%n", account, amount);
    }

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        System.out.println("ATM WITHDRAWAL SIMULATION");
        try {
            System.out.print("Enter account number: ");
            String account = input.nextLine().trim();
            System.out.print("Enter withdrawal amount: ");
            String amountText = input.nextLine().trim();
            double amount = Double.parseDouble(amountText);
            withdraw(account, amount);
        } catch (NumberFormatException e) {
            System.out.println("Input error: enter a numeric withdrawal amount");
        } catch (InvalidWithdrawalException | InsufficientFundsException e) {
            System.out.println("Transaction rejected: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("ATM device error: " + e.getMessage());
        } finally {
            System.out.println("Transaction session closed (finally executed)");
            input.close();
        }
    }
}