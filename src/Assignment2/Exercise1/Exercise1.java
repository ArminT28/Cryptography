package Assignment2.Exercise1;
///Delete the above line when you run from the terminal and leave it when you run from IDE
import java.util.Scanner;

public class Exercise1 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Ciphertext to hack is:");
        String ciphertext = scanner.nextLine();
        scanner.close();

        String plaintext = HackShift(ciphertext);
        System.out.println("Decrypted Ciphertext: " + plaintext);
    }

    public static String HackShift(String cipherText) {
        String plainText = "";
        for (int key = 0; key < 26; key++) {
            plainText = "";
            for (int i = 0; i < cipherText.length(); i++) {
                char singleCharacter = cipherText.charAt(i);
                if (Character.isLetter(singleCharacter)) {
                    char upperCaseCharacter = Character.toUpperCase(singleCharacter);
                    char shiftedCharacter = (char) ('A' + (upperCaseCharacter - 'A' - key + 26) % 26);
                    plainText += shiftedCharacter;
                } else {
                    plainText += singleCharacter;
                }
            }
           ///Check if the plaintext has most common 2-3-4 letter words, if at least 2 of them are present then break the loop
            // Check if the plaintext satisfies the given conditions
            if (containsTwoLetterWords(plainText) && containsThreeLetterWord(plainText) || containsFourLetterWord(plainText)) {
                return plainText;
            }
        }
        return plainText;
    }

    // Method to check if the plaintext contains at least two 2-letter words
    public static boolean containsTwoLetterWords(String text) {
        String[] twoLetterWords = {"of", "to", "in", "it", "is", "be", "as", "at", "so", "we", "he", "by", "or", "on", "do", "if", "me", "my", "up", "an", "go", "no", "us", "am"};
        int count = 0;
        for (String word : twoLetterWords) {
            if (text.contains(word)) {
                count++;
                if (count >= 2) {
                    return true;
                }
            }
        }
        return false;
    }

    // Method to check if the plaintext contains at least one 3-letter word
    public static boolean containsThreeLetterWord(String text) {
        String[] threeLetterWords = {"the", "and", "for", "are", "but", "not", "you", "all", "any", "can", "had", "her", "was", "one", "our", "out", "day", "get", "has", "him", "his", "how", "man", "new", "now", "old", "see", "two", "way", "who", "boy", "did", "its", "let", "put", "say", "she", "too", "use"};
        for (String word : threeLetterWords) {
            if (text.contains(word)) {
                return true;
            }
        }
        return false;
    }

    // Method to check if the plaintext contains at least one 4-letter word
    public static boolean containsFourLetterWord(String text) {
        String[] fourLetterWords = {"that", "with", "have", "this", "will", "your", "from", "they", "know", "want", "been", "good", "much", "some", "time"};
        for (String word : fourLetterWords) {
            if (text.contains(word)) {
                return true;
            }
        }
        return false;
    }
}