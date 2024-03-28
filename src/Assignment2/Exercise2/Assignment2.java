package Assignment2.Exercise2;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Assignment2 {

    private static final int ALPHABET_SIZE = 26;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the ciphertext:");
        String cipherText = scanner.nextLine();
        scanner.close();

        String result = HackAffine(cipherText);
        System.out.println("Plaintext: " + result);
    }

    public static String HackAffine(String cipherText) {
        int[] frequencies = calculateFrequencies(cipherText);
        int[] sortedFrequencies = sortFrequencies(frequencies);
        int[] possibleKeys = findPossibleKeys(sortedFrequencies);

        String plaintext = "";
        int maxScore = Integer.MIN_VALUE;
        int bestKeyA = 0;
        int bestKeyB = 0;

        for (int keyA : possibleKeys) {
            for (int keyB = 0; keyB < ALPHABET_SIZE; keyB++) {
                String decryptedText = decrypt(cipherText, keyA, keyB);
                int score = calculateScore(decryptedText);
                if (score > maxScore) {
                    maxScore = score;
                    plaintext = decryptedText;
                    bestKeyA = keyA;
                    bestKeyB = keyB;
                }
            }
        }

        System.out.println("Keys a: " + bestKeyA + ", b: " + bestKeyB);
        return plaintext;
    }

    private static int[] calculateFrequencies(String text) {
        int[] frequencies = new int[ALPHABET_SIZE];
        for (char c : text.toCharArray()) {
            if (Character.isLetter(c)) {
                int index = Character.toUpperCase(c) - 'A';
                frequencies[index]++;
            }
        }
        return frequencies;
    }

    private static int[] sortFrequencies(int[] frequencies) {
        int[] sortedIndices = new int[ALPHABET_SIZE];
        for (int i = 0; i < ALPHABET_SIZE; i++) {
            sortedIndices[i] = i;
        }
        for (int i = 0; i < ALPHABET_SIZE - 1; i++) {
            for (int j = i + 1; j < ALPHABET_SIZE; j++) {
                if (frequencies[sortedIndices[j]] > frequencies[sortedIndices[i]]) {
                    int temp = sortedIndices[i];
                    sortedIndices[i] = sortedIndices[j];
                    sortedIndices[j] = temp;
                }
            }
        }
        return sortedIndices;
    }

    private static int[] findPossibleKeys(int[] sortedFrequencies) {
        // Assuming 'E' is the most common letter in English
        int mostCommonLetterIndex = 'E' - 'A';
        int[] possibleKeys = new int[ALPHABET_SIZE];
        for (int i = 0; i < ALPHABET_SIZE; i++) {
            int key = (mostCommonLetterIndex - sortedFrequencies[i] + ALPHABET_SIZE) % ALPHABET_SIZE;
            if (gcd(key, ALPHABET_SIZE) == 1) {
                possibleKeys[i] = key;
            }
        }
        return possibleKeys;
    }

    private static int gcd(int a, int b) {
        if (b == 0) {
            return a;
        }
        return gcd(b, a % b);
    }

    private static String decrypt(String cipherText, int keyA, int keyB) {
        StringBuilder plaintext = new StringBuilder();
        for (char c : cipherText.toCharArray()) {
            if (Character.isLetter(c)) {
                char decryptedChar = (char) (((c - 'A' - keyB) * modInverse(keyA, ALPHABET_SIZE)) % ALPHABET_SIZE + 'A');
                if (decryptedChar < 'A') {
                    decryptedChar += ALPHABET_SIZE;
                }
                plaintext.append(decryptedChar);
            } else {
                plaintext.append(c);
            }
        }
        return plaintext.toString();
    }

    private static int modInverse(int a, int m) {
        a = a % m;
        for (int x = 1; x < m; x++) {
            if ((a * x) % m == 1) {
                return x;
            }
        }
        return 1;
    }

    private static int calculateScore(String text) {
        // Score based on English letter frequency
        Map<Character, Double> letterFrequencies = new HashMap<>();
        letterFrequencies.put('E', 12.70);
        letterFrequencies.put('T', 9.06);
        letterFrequencies.put('A', 8.17);
        letterFrequencies.put('O', 7.51);
        letterFrequencies.put('I', 6.97);
        letterFrequencies.put('N', 6.75);
        letterFrequencies.put('S', 6.33);
        letterFrequencies.put('H', 6.09);
        letterFrequencies.put('R', 5.99);
        letterFrequencies.put('D', 4.25);
        letterFrequencies.put('L', 4.03);
        letterFrequencies.put('C', 2.78);
        letterFrequencies.put('U', 2.76);
        letterFrequencies.put('M', 2.41);
        letterFrequencies.put('W', 2.36);
        letterFrequencies.put('F', 2.23);
        letterFrequencies.put('G', 2.02);
        letterFrequencies.put('Y', 1.97);
        letterFrequencies.put('P', 1.93);
        letterFrequencies.put('B', 1.29);
        letterFrequencies.put('V', 0.98);
        letterFrequencies.put('K', 0.77);
        letterFrequencies.put('J', 0.15);
        letterFrequencies.put('X', 0.15);
        letterFrequencies.put('Q', 0.10);
        letterFrequencies.put('Z', 0.07);

        double score = 0;
        for (char c : text.toCharArray()) {
            if (Character.isLetter(c)) {
                score += letterFrequencies.getOrDefault(Character.toUpperCase(c), 0.0);
            }
        }
        return (int) score;
    }
}
