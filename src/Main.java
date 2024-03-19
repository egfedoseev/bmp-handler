import bytemethods.ByteMethods;

public class Main {
    public static void main(String[] args) {
        short x = Short.MIN_VALUE;
        byte[] bytes = ByteMethods.shortToByteArray(x);

        for (byte elem : bytes) {
            System.out.print(ByteMethods.byteToString(elem) + ' ');
        }
    }
}
