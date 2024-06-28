public class CalculatePrice {
    public static String calculatePrice(int ilosc, int time) {
        int calculatedPrice = (10 * time) + (ilosc * 5);

        return Integer.toString(calculatedPrice);
    }
}
