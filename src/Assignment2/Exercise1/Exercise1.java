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

    ///Question, how many 4 letter , how many 3 letter , how many 2 letter words should we check for at the Shift Cipher hack function?
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
            ///Check if the plaintext has most common 2-3-4 letter words
            ///If at least two 2-letter words and one 3-letter word || one 4-letter word is found we found the answer
            if (numberOfFourLetterWord(plainText)>=2) {
                System.out.println("Found at least two four letter words: " + plainText);
                return plainText;
            }
            else if (numberOfThreeLetterWord(plainText)>=4) {
                System.out.println("Found at least four three letter words: " + plainText);
                return plainText;
            }
            else if (numberOfThreeLetterWord(plainText)>=2 && numberOfTwoLetterWords(plainText)>=4) {
                System.out.println("Found at least two three letter word and at least four two letter words: " + plainText);
                return plainText;
            }
            else if (numberOfTwoLetterWords(plainText)>=6) {
            System.out.println("Found at least six two letter words: " + plainText);
                return plainText;
            }
            System.out.println("Key: " + key + " | PlainText: " + plainText);
        }
        return plainText;
    }

    public static int numberOfTwoLetterWords(String plainText) {
        String[] possibleTwoLetterWords = {"OF", "TO", "IN", "IT", "IS", "BE", "AS", "AT", "SO", "WE", "HE", "BY", "OR", "ON", "DO", "IF", "ME", "MY", "UP", "AN", "GO", "NO", "US", "AM"};
        int foundWords = 0;
        for (String word : possibleTwoLetterWords) {
            if (plainText.contains(word)) {
                foundWords++;
            }
        }
        return foundWords;
    }

    public static int numberOfThreeLetterWord(String plainText) {
        String[] possibleThreeLetterWords = {"THE", "AND", "FOR", "ARE", "BUT", "NOT", "YOU", "ALL", "ANY", "CAN", "HAD", "HER", "WAS", "ONE", "OUR", "OUT", "DAY", "GET", "HAS", "HIM", "HIS", "HOW", "MAN", "NEW", "NOW", "OLD", "SEE", "TWO", "WAY", "WHO", "BOY", "DID", "ITS", "LET", "PUT", "SAY", "SHE", "TOO", "USE"};
        int foundWords = 0;
        for (String word : possibleThreeLetterWords) {
            if (plainText.contains(word)) {
                foundWords++;
            }
        }
        return foundWords;
    }

    public static int numberOfFourLetterWord(String plainText) {
        String[] possibleFourLetterWords = {"THAT", "WITH", "HAVE", "THIS", "WILL", "YOUR", "FROM", "THEY", "KNOW", "WANT", "BEEN", "GOOD", "MUCH", "SOME", "TIME"};
        int foundWords = 0;
        for (String word : possibleFourLetterWords) {
            if (plainText.contains(word)) {
                foundWords++;
            }
        }
        return foundWords;
    }
}