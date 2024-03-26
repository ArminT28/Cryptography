package Assignment1.Exercise2;
///Delete the above line when you run from the terminal and leave it when you run from IDE
import java.util.InputMismatchException;
import java.util.Scanner;

public class Exercise2 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean exit = false;

        while (!exit) {

            try {
                System.out.print("1.) Encryption\n2.) Decryption\n3.)Exit\nEnter your choice: ");
                int choice = scanner.nextInt();
                scanner.nextLine();

                if(choice == 1) {
                    System.out.print("Enter a: ");
                    int a = scanner.nextInt();

                    System.out.print("Enter b: ");
                    int b = scanner.nextInt();

                    System.out.print("Enter plaintext: ");
                    scanner.nextLine();
                    String plaintext = scanner.nextLine();

                    if (gcd(a, 26) == 1) {
                        PrintAffineAlphabet(a,b);
                        String ciphertext = Encryption(a, b, plaintext);
                        System.out.println("Ciphertext: " + ciphertext);
                    } else {
                        System.out.println("Invalid 'a'. gcd(a, 26) should be 1.");
                    }
                    System.out.print("Do you want to continue with another Cipher? (Y/N): ");
                    String response = scanner.nextLine();
                    exit = !response.equals("Y") && !response.equals("y");
                } else if(choice == 2) {
                    System.out.print("Enter a: ");
                    int a = scanner.nextInt();

                    System.out.print("Enter b: ");
                    int b = scanner.nextInt();

                    System.out.print("Enter ciphertext: ");
                    scanner.nextLine(); // Consume newline
                    String ciphertext = scanner.nextLine();

                    if (gcd(a, 26) == 1) {
                        PrintAffineAlphabet(a,b);
                        String plaintext = Decryption(a, b, ciphertext,26);
                        System.out.println("Plaintext: " + plaintext);
                    } else {
                        System.out.println("Invalid 'a'. gcd(a, 26) should be 1.");
                    }
                    System.out.print("Do you want to continue with another Cipher? (Y/N): ");
                    String response = scanner.nextLine();
                    exit = !response.equals("Y") && !response.equals("y");
                }
                else if(choice == 3) {
                    scanner.close();
                    break;
                } else {
                    System.out.println("Invalid choice. Please enter 1 or 2.");
                }
                System.out.println("\n\n\n\n");
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid integer for the input.");
                scanner.nextLine();
            } catch (Exception e) {
                System.out.println("An error occurred: " + e.getMessage());
                break;
            }
        }
        scanner.close();
    }

    public static int gcd(int a, int b) {
        while (b != 0) {
            int temp = b;
            b = a % b;
            a = temp;
        }
        return a;
    }

    private static void PrintAffineAlphabet(int a, int b) {
        System.out.println("\nAfine Alphabet for a=" + a + " and b=" + b + " is:");
        for (int i = 0; i < 26; i++) {
            System.out.print(i + "|");
        }
        System.out.println();
        for (int i = 0; i < 26; i++) {
            char character = (char) ('A' + i);
            if (i < 10) {
                System.out.print(character + "|");
            } else {
                System.out.print(character + " |");
            }
        }
        System.out.println();
        for (int i = 0; i < 26; i++) {
            char shifted_character = (char) ('A' + (a * i + b) % 26);
            if (i < 10) {
                System.out.print(shifted_character + "|");
            } else {
                System.out.print(shifted_character + " |");
            }
        }
        System.out.println("\n");
    }

    private static String Encryption(int a, int b, String plaintext) {
        StringBuilder ciphertext = new StringBuilder();
        for (char single_character : plaintext.toCharArray()) {
            if (Character.isLetter(single_character)) {
                char single_character_upper = Character.isUpperCase(single_character) ? single_character : Character.toUpperCase(single_character);
                int encrypted_value = (a * (single_character_upper - 'A') + b) % 26;
                char encrypted_character = (char) (encrypted_value + 'A');
                ciphertext.append(encrypted_character);
            } else {
                ciphertext.append(single_character);
            }
        }
        return ciphertext.toString();
    }

    private static int inverse_modulo_of_a_number(int a, int m) {
        for (int i = 1; i < m; i++) {
            if ( ( (a * i) - 1 ) % m == 0) {
                return i;
            }
        }
        return -1; // Inverse doesn't exist
    }

    private static String Decryption(int a, int b, String ciphertext, int m) {
        StringBuilder plaintext = new StringBuilder();
        int a_inverse_modulo = inverse_modulo_of_a_number(a, m);
        if ( a_inverse_modulo == -1) {
            throw new IllegalArgumentException("Inverse of 'a' doesn't exist.");
        }
        for (char single_character : ciphertext.toCharArray()) {
            if (Character.isLetter(single_character)) {
                char single_character_upper = Character.toUpperCase(single_character);
                // Regularize the value of modulo in case of negative value by adding m to it
                int decryptedChar = ((a_inverse_modulo * ((single_character_upper - 'A') - b)) % m + m ) % m;
                char decrypted = (char) (decryptedChar + 'A');
                plaintext.append(decrypted);
            } else {
                plaintext.append(single_character);
            }
        }
        return plaintext.toString();
    }
}
