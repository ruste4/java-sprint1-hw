public class Product {
    String itemName;
    boolean isExpense;
    int quantity;
    int sumOfOne;

    Product(String itemName, boolean isExpense, int quantity, int sumOfOne) {
        this.itemName = itemName;
        this.isExpense = isExpense;
        this.quantity = quantity;
        this.sumOfOne = sumOfOne;
    }

    public int getSumOfAll() {
        return this.quantity * this.sumOfOne;
    }
}
