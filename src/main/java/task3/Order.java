package task3;

/**
 * Order - record for representing a shoe order
 * Contains order ID, shoe type, and quantity
 */
public record Order(int orderId, String shoeType, int quantity) {

    /**
     * Compact constructor with data validation
     */
    public Order {
        if (orderId <= 0) {
            throw new IllegalArgumentException("Order ID must be positive");
        }
        if (shoeType == null || shoeType.isEmpty()) {
            throw new IllegalArgumentException("Shoe type cannot be null or empty");
        }
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be positive");
        }
    }

    @Override
    public String toString() {
        return String.format("Order[ID=%d, Type=%s, Qty=%d]", orderId, shoeType, quantity);
    }
}
