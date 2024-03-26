package Assignment1.Exercise1;
///Delete the above line when you run from the terminal and leave it when you run from IDE
import java.util.InputMismatchException;
import java.util.Scanner;

public class Exercise1 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean exit = false;

        while (!exit) {

            try {
                System.out.print("K = ");
                int K = scanner.nextInt();
                scanner.nextLine();

                System.out.print("Ciphertext to decrypt is: ");
                String ciphertext = scanner.nextLine();

                PrintShiftCypherAlphabet(K);
                String plaintext = ShiftCypherDecryption(K, ciphertext);
                System.out.println("Decrypted plaintext: " + plaintext);

                System.out.print("Do you want to continue with another Cipher? (Y/N): ");
                String response = scanner.nextLine();
                exit = !response.equals("Y") && !response.equals("y");
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid integer for the key.");
                scanner.nextLine();
            } catch (Exception e) {
                System.out.println("An error occurred: " + e.getMessage());
                exit = true;
            }
        }
        scanner.close();
    }

    private static void PrintShiftCypherAlphabet(int k) {
        System.out.println("Shift Cypher Alphabet for K=" + k + " is:");
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
            char shifted_character = (char) ('A' + (i + k) % 26);
            if (i < 10) {
                System.out.print(shifted_character + "|");
            } else {
                System.out.print(shifted_character + " |");
            }
        }
        System.out.println();
    }

    static String ShiftCypherDecryption(int k, String ciphertext) {
        StringBuilder plaintext = new StringBuilder();

        for (char current_character : ciphertext.toCharArray()) {
            if (Character.isLetter(current_character)) {
                char current_character_upper = Character.toUpperCase(current_character);
                char decryptedChar = calculateDecryptedChar(k, current_character_upper);
                plaintext.append(decryptedChar);
            } else {
                plaintext.append(current_character);
            }
        }

        return plaintext.toString();
    }

    private static char calculateDecryptedChar(int k, char character_to_decrypt) {
        int decryptedValue = (character_to_decrypt - 'A' - k + 26) % 26;
        return (char) ('A' + decryptedValue);
    }
}