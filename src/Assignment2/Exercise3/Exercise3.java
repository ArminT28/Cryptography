///package Assignment2.Exercise3;
///Delete the above line when you run from the terminal and leave it when you run from IDE
import java.util.*;

///Short Example: codebyjava
public class Exercise3 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Plaintext is:");
        String plainText = scanner.nextLine();

        List<Double> plaintext = EncryptCaesar(plainText);
        System.out.println("Encrypted analog Ciphertext: " + plaintext);
    }

    private static List<Double> EncryptCaesar(String plainText) {
        String upperPlainText = plainText.toUpperCase();

        int caesarKey = 3;

        char[] charactersArray = upperPlainText.toCharArray();

        List<Double> cipherText = new ArrayList<>();

        for (char singleCharacter : charactersArray) {
            if (Character.isLetter(singleCharacter)) {
                int singleCharacterValue = singleCharacter - 'A';

                int encryptedValue = (singleCharacterValue + caesarKey) % 26;

//                String binaryEncryptedValue = Integer.toBinaryString(encryptedValue);
//
//                cipherText.append(String.format("%5s", binaryEncryptedValue).replace(' ', '0'));

                double encryptedMappedValue = (encryptedValue - 31.0 / 2.0) / ( 31.0 / 2.0);

                cipherText.add(encryptedMappedValue);
            } else {
                cipherText.add((double)singleCharacter - 'A');
            }
        }
        return cipherText;
    }
}
