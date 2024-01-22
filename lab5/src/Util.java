public class Util {
    public static boolean isPrime(int number) {
        for (int i = 2; i <= Math.sqrt(number); i++) {
            if (number % i == 0) {
                return false;
            }
        }
        return true;
    }

    public static int[] getMultipliers(int value) {
        for (int i = (int) Math.sqrt(value); i > 1; i--) {
            if (value % i == 0) {
                return new int[] {i, value / i};
            }
        }
        return new int[] {value, 1};
    }
}
