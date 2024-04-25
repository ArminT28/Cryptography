//package Assignment4.Exercise2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class RSA_Hack {
    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        boolean exit = false;

        int n;
        System.out.println("Please Input n: ");
        n = scanner.nextInt();
        scanner.nextLine();

        int b;
        System.out.println("Please Input b: ");
        b = scanner.nextInt();
        scanner.nextLine();

        String cipherText;
        System.out.println("Please Input cipherText: ");
        cipherText = scanner.nextLine();

        var plainText = HackRSA(n, b, cipherText);
        System.out.println("PlainText: " + plainText);
    }

    private static String HackRSA(int n, int b, String cipherText) {
        var cipherTextArray = cipherText.split(" ");
        var plainAlphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        var plainAlphabetArray = plainAlphabet.toCharArray();
        var encryptedAlphabet = new HashMap<Integer,String>();
        for (char singleCharacter : plainAlphabetArray)
        {
            var encryptedCharacter = encrypt(n, b, String.valueOf(singleCharacter), 1);
            var encryptedValue = Integer.parseInt(encryptedCharacter);
            encryptedAlphabet.put(encryptedValue, String.valueOf(singleCharacter));
        }

        var plainText = "";
        for (String singleCihperTextValue: cipherTextArray)
        {
            var integerCipherTextValue = Integer.parseInt(singleCihperTextValue);
            var decryptedCharacter = encryptedAlphabet.get(integerCipherTextValue);
            plainText += decryptedCharacter;
        }
        return plainText;
    }

    public static String encrypt(Integer n, Integer b, String plaintext, int blockLength) {
        var numberOfDigitsInN = (int) Math.log10(n) + 1;

        var letterAlphabet = new ArrayList<Character>();
        for (char i = 'A'; i <= 'Z'; i++) {
            letterAlphabet.add(i);
        }

        var numericalMessage = new ArrayList<String>();
        for (char single_character : plaintext.toCharArray()) {

            if (Character.isLetter(single_character)) {
                int character_value = letterAlphabet.indexOf(Character.toUpperCase(single_character));
                String padded_value = String.format("%0" + numberOfDigitsInN + "d", character_value);
                numericalMessage.add(String.valueOf(padded_value));
            }
        }

//            System.out.println(numericalMessage);

        var encryptedMessage = new ArrayList<String>();
        for (int i = 0; i < numericalMessage.size(); i += blockLength) {
            StringBuilder combinedBlocks = new StringBuilder();

            int combinedValue = 0;
            var powerOfCurrentBlock = blockLength - 1;
            var blockValue = 0;
            for (int j = i; j < Math.min(i + blockLength, numericalMessage.size()); j++) {
                blockValue += (int) ((Math.pow(26,powerOfCurrentBlock))*Integer.parseInt(numericalMessage.get(j)));
                powerOfCurrentBlock--;
            }

//                System.out.println("BlockValue: " + blockValue);

            int encryptedValue=1;
            for (int j = 0; j < b; j++) {
                encryptedValue = (encryptedValue * blockValue) % n;
            }
            var encryptedBlock = String.format("%0" + numberOfDigitsInN + "d", encryptedValue);

//                System.out.println("Block: " + encryptedBlock + " ");

            encryptedMessage.add(encryptedBlock);
        }

//            System.out.println(encryptedMessage);

        return String.join("", encryptedMessage);
    }
}
