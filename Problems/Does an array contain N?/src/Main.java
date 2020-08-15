import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        int len = input.nextInt();
        int[] values = new int[len];

        for (int i = 0; i < len; i++) {
            values[i] = input.nextInt();
        }

        int toCheck = input.nextInt();
        boolean valueExists = false;

        for (int x = 0; x < len; x++) {
            if (values[x] == toCheck) {
                valueExists = true;
            }
        }

        System.out.println(valueExists);
    }
}