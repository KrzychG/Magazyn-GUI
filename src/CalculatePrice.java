public class CalculatePrice {
    public static String calculatePrice(int amount, int time) {
        int calculatedPrice = (10 * time) + (amount * 5);
        return Integer.toString(calculatedPrice);
    }
}
