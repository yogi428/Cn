import java.io.Serializable;

public class Stock implements Serializable {
    private static final long serialVersionUID = 1L;

    private String symbol;
    private String name;
    private double price;
    private int quantity;

    public Stock(String symbol, String name, double price, int quantity) {
        this.symbol = symbol;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    // Getters and other methods remain the same
}
