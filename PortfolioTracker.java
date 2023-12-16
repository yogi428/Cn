import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class PortfolioTracker {
    private static Map<String, Stock> portfolio = new HashMap<>();
    private static final String PORTFOLIO_FILE = "portfolio.dat";

    public static void main(String[] args) {
        loadPortfolio();

        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("1. Add Stock\n2. View Portfolio\n3. Save and Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character

            switch (choice) {
                case 1:
                    addStock();
                    break;
                case 2:
                    viewPortfolio();
                    break;
                case 3:
                    savePortfolio();
                    System.out.println("Exiting...");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private static void addStock() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter stock symbol: ");
        String symbol = scanner.nextLine();

        System.out.print("Enter stock name: ");
        String name = scanner.nextLine();

        System.out.print("Enter stock price: ");
        double price = scanner.nextDouble();

        System.out.print("Enter quantity: ");
        int quantity = scanner.nextInt();

        Stock stock = new Stock(symbol, name, price, quantity);
        portfolio.put(symbol, stock);
        System.out.println("Stock added to portfolio.");
    }

    private static void viewPortfolio() {
        if (portfolio.isEmpty()) {
            System.out.println("Portfolio is empty.");
            return;
        }

        System.out.println("Stock Portfolio:");
        System.out.printf("%-10s %-20s %-10s %-10s %-15s\n", "Symbol", "Name", "Price", "Quantity", "Value");
        for (Stock stock : portfolio.values()) {
            System.out.printf("%-10s %-20s %-10.2f %-10d %-15.2f\n",
                    stock.getSymbol(), stock.getName(), stock.getPrice(), stock.getQuantity(), stock.getValue());
        }

        double totalValue = portfolio.values().stream().mapToDouble(Stock::getValue).sum();
        System.out.println("Total Portfolio Value: " + totalValue);
    }

    private static void savePortfolio() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(PORTFOLIO_FILE))) {
            oos.writeObject(portfolio);
            System.out.println("Portfolio saved.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    private static void loadPortfolio() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(PORTFOLIO_FILE))) {
            Object obj = ois.readObject();
            if (obj instanceof Map) {
                portfolio = (Map<String, Stock>) obj;
                System.out.println("Portfolio loaded.");
            }
        } catch (IOException | ClassNotFoundException e) {
            // If the file doesn't exist or other errors occur, it's okay, just continue with an empty portfolio.
        }
    }
}
