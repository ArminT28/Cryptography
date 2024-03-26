package Assignment1.Exercise3;
///Delete the above line when you run from the terminal and leave it when you run from IDE
import java.util.InputMismatchException;
import java.util.Scanner;

public class Exercise3 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean exit = false;

        while(!exit) {
            try {
                System.out.print("a11: ");
                int a11 = scanner.nextInt();
                System.out.print("a12: ");
                int a12 = scanner.nextInt();
                System.out.print("a21: ");
                int a21 = scanner.nextInt();
                System.out.print("a22: ");
                int a22 = scanner.nextInt();

                System.out.print("Plaintext: ");
                String plaintext = scanner.next();

                int determinant = calculate_matrix_determinant(a11,a12,a21,a22);
                int gcdResult = gcd(determinant, 26);

                if (gcdResult != 1) {
                    System.out.println("GCD(det(A), 26) is not 1. Choose different matrix values.");
                    return;
                }

                String ciphertext = Encryption(a11,a12,a21,a22, plaintext);
                System.out.println("Ciphertext: " + ciphertext);

                System.out.print("Do you want to continue with another Cipher? (Y/N): ");
                String response = scanner.next();
                exit = !response.equals("Y") && !response.equals("y");
                System.out.println("\n\n\n\n");
            }
            catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid integer for the key.");
                scanner.nextLine();
            }
            catch (Exception e) {
                System.out.println("An error occurred: " + e.getMessage());
                exit = true;
            }
        }
        scanner.close();
    }

    public static int calculate_matrix_determinant(int c11, int c12, int c21, int c22) {
        return c11 * c22 - c12 * c21;
    }

    public static int gcd(int a, int b) {
        while (b != 0) {
            int temp = b;
            b = a % b;
            a = temp;
        }
        return a;
    }

    public static String Encryption(int a11, int a12, int a21, int a22, String plaintext) {
        StringBuilder ciphertext = new StringBuilder();

        for (int i = 0; i < plaintext.length(); i += 2) {
            int[] matrix_1_by_2 = new int[2];
            matrix_1_by_2[0] = Character.toUpperCase(plaintext.charAt(i)) - 'A';
            // We choose the filling value to be A if the length of the plaintext is not perfect with the matrix
            matrix_1_by_2[1] = (i + 1 < plaintext.length()) ? Character.toUpperCase(plaintext.charAt(i + 1)) - 'A' : 0;

            int[][] matrix_2_by_2 = new int[][]{{a11, a12}, {a21, a22}};
            int[] result_1_by_2 = multiply_matrix_2_by_2_with_1_by_2(matrix_2_by_2, matrix_1_by_2);

            ciphertext.append((char) ((result_1_by_2[0] + 26) % 26 + 'A'));
            ciphertext.append((char) ((result_1_by_2[1] + 26) % 26 + 'A'));
        }

        return ciphertext.toString();
    }

    private static int[] multiply_matrix_2_by_2_with_1_by_2(int[][] matrix_2_by_2, int[] matrix_1_by_2) {
        int[] result = new int[2];

        result[0] = (matrix_1_by_2[0] * matrix_2_by_2[0][0] + matrix_1_by_2[1] * matrix_2_by_2[1][0]) % 26;
        result[1] = (matrix_2_by_2[0][1] * matrix_1_by_2[0] + matrix_2_by_2[1][1] * matrix_1_by_2[1]) % 26;

        return result;
    }
}
