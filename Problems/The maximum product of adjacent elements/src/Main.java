import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        int len = input.nextInt();
        int[] values = new int[len];

        for (int i = 0; i < len; i++) {
            values[i] = input.nextInt();
        }

        int maxProduct = 0;

        for (int i = 0; i < len; i++) {
            if (i + 1 < len) {
                int product = values[i] * values[i + 1];
                if (product > maxProduct) {
                    maxProduct = product;
                }
            }
        }
        System.out.println(maxProduct);
    }
}