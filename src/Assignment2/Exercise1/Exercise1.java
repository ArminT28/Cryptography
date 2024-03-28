///package Assignment2.Exercise1;
///Delete the above line when you run from the terminal and leave it when you run from IDE

import java.util.Scanner;

public class Exercise1 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Ciphertext to hack is:");
        String ciphertext = scanner.nextLine();

        String plaintext = HackShift(ciphertext);
        System.out.println("Decrypted Ciphertext: " + plaintext);

        scanner.close();
    }

    ///Question, how many Quadri-, Tri- and Digramms should we check for at the Shift Cipher hack function?
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
            System.out.println("Key: " + key + " | PlainText: " + plainText);
            ///Check if the plaintext has most common Di-, Tri- and Quadrigrams
            ///If at least 4 Digramms and 2 Trigramm || 2 Quadrigramm is found we found the answer
            if (numberOfQuadrigrams(plainText)>=2) {
                System.out.println("Found at least two Quadrigramms for the key:" + key);
                System.out.println(plainText);
                System.out.print("Is PlainText readable? (Y/N): ");
                Scanner scanner = new Scanner(System.in);
                String answer = scanner.nextLine();
                if (answer.equals("Y")) {
                    return plainText;
                }
            }
            else if (numberOfTrigramms(plainText)>=4) {
                System.out.println("Found at least four Trigramms for the key:" + key);
                System.out.println(plainText);
                System.out.print("Is PlainText readable? (Y/N): ");
                Scanner scanner = new Scanner(System.in);
                String answer = scanner.nextLine();
                if (answer.equals("Y")) {
                    return plainText;
                }
            }
            else if (numberOfTrigramms(plainText)>=2 && numberOfDigramms(plainText)>=4) {
                System.out.println("Found at least two Trigramms and at least four Digramms for the key:" + key);
                System.out.println(plainText);
                System.out.print("Is PlainText readable? (Y/N): ");
                Scanner scanner = new Scanner(System.in);
                String answer = scanner.nextLine();
                if (answer.equals("Y")) {
                    return plainText;
                }
            }
            else if (numberOfDigramms(plainText)>=6) {
            System.out.println("Found at least six Digramms for the key:" + key);
                System.out.println(plainText);
                System.out.print("Is PlainText readable? (Y/N): ");
                Scanner scanner = new Scanner(System.in);
                String answer = scanner.nextLine();
                if (answer.equals("Y")) {
                    return plainText;
                }
            }
        }
        return "";
    }

    public static int numberOfDigramms(String plainText) {
        ///20 most common digramms
        String[] possibleDigramms = {
                "TH", "HE", "IN", "ER", "AN", "RE", "ND", "ON", "EN", "AT",
                "OU", "ED", "HA", "TO", "OR", "IT", "IS", "HI", "ES", "NG"
        };

        int foundDigramms = 0;
        for (String digram : possibleDigramms) {
            if (plainText.toUpperCase().contains(digram)) {
                foundDigramms++;
            }
        }
        return foundDigramms;
    }


    public static int numberOfTrigramms(String plainText) {
        //20 most common trigramms
        String[] possibleTrigramms = {
                "THE", "AND", "ING", "HER", "HAT", "HIS", "THA", "ERE", "FOR",
                "ENT", "ION", "TER", "WAS", "YOU", "ITH", "VER", "ALL", "WIT",
                "THI", "TIO"
        };

        int foundTrigramms = 0;
        for (String trigram : possibleTrigramms) {
            if (plainText.toUpperCase().contains(trigram)) {
                foundTrigramms++;
            }
        }
        return foundTrigramms;
    }

    public static int numberOfQuadrigrams(String plainText) {
        //20 most common quadrigrams
        String[] possibleQuadrigrams = {
                "THAT", "THER", "WITH", "TION", "HERE", "OULD", "IGHT", "HAVE",
                "HICH", "WHIC", "THIS", "THIN", "THEY", "ATIO", "EVER", "FROM",
                "OUGH", "WERE", "HING", "MENT"
        };

        int foundQuadrigrams = 0;
        for (String quadrigram : possibleQuadrigrams) {
            if (plainText.toUpperCase().contains(quadrigram)) {
                foundQuadrigrams++;
            }
        }
        return foundQuadrigrams;
    }
}