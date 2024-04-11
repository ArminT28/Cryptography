//package Assignment3.Exercise2;
///Delete the above line when you run from the terminal and leave it when you run from IDE

import java.util.*;

public class DES {
    public static void main(String[] args) {
        String key = "3b3898371520f75e";
        String plaintext = "8f03456d3f78e2c5";
        System.out.println("Plaintext: " + plaintext);
        String ciphertext = Encryption(plaintext, key);
        System.out.println("Ciphertext: " + ciphertext);
    }

    // Initial permutation
    private static final int[] IP = { 58, 50, 42, 34, 26, 18, 10, 2,
            60, 52, 44, 36, 28, 20, 12, 4,
            62, 54, 46, 38, 30, 22, 14, 6,
            64, 56, 48, 40, 32, 24, 16, 8,
            57, 49, 41, 33, 25, 17, 9, 1,
            59, 51, 43, 35, 27, 19, 11, 3,
            61, 53, 45, 37, 29, 21, 13, 5,
            63, 55, 47, 39, 31, 23, 15, 7 };

    // Final permutation
    private static final int[] IP_1 = { 40, 8, 48, 16, 56, 24, 64, 32,
            39, 7, 47, 15, 55, 23, 63, 31,
            38, 6, 46, 14, 54, 22, 62, 30,
            37, 5, 45, 13, 53, 21, 61, 29,
            36, 4, 44, 12, 52, 20, 60, 28,
            35, 3, 43, 11, 51, 19, 59, 27,
            34, 2, 42, 10, 50, 18, 58, 26,
            33, 1, 41, 9, 49, 17, 57, 25 };

    // Permuted choice 1
    private static final int[] PC1 = { 57, 49, 41, 33, 25, 17, 9, 1,
            58, 50, 42, 34, 26, 18, 10, 2,
            59, 51, 43, 35, 27, 19, 11, 3,
            60, 52, 44, 36, 63, 55, 47, 39,
            31, 23, 15, 7, 62, 54, 46, 38,
            30, 22, 14, 6, 61, 53, 45, 37,
            29, 21, 13, 5, 28, 20, 12, 4 };

    // Permuted choice 2
    private static final int[] PC2 = { 14, 17, 11, 24, 1, 5, 3, 28,
            15, 6, 21, 10, 23, 19, 12, 4,
            26, 8, 16, 7, 27, 20, 13, 2,
            41, 52, 31, 37, 47, 55, 30, 40,
            51, 45, 33, 48, 44, 49, 39, 56,
            34, 53, 46, 42, 50, 36, 29, 32 };

    // Number of left shifts for each round
    private static final int[] leftShifts = { 1, 1, 2, 2, 2, 2, 2,
            2, 1, 2, 2, 2, 2, 2, 2, 1};

    // Expansion permutation
    private static final int[] E = { 32, 1, 2, 3, 4, 5, 4, 5,
            6, 7, 8, 9, 8, 9, 10, 11,
            12, 13, 12, 13, 14, 15, 16, 17,
            16, 17, 18, 19, 20, 21, 20, 21,
            22, 23, 24, 25, 24, 25, 26, 27,
            28, 29, 28, 29, 30, 31, 32, 1 };

    // Permutation after S-box substitution
    private static final int[] P = { 16, 7, 20, 21, 29, 12, 28, 17,
            1, 15, 23, 26, 5, 18, 31, 10,
            2, 8, 24, 14, 32, 27, 3, 9,
            19, 13, 30, 6, 22, 11, 4, 25 };

    // S-boxes
    private static final int[][][] S = {
            // S1
            {
                    {14, 4, 13, 1, 2, 15, 11, 8, 3, 10, 6, 12, 5, 9, 0, 7},
                    {0, 15, 7, 4, 14, 2, 13, 1, 10, 6, 12, 11, 9, 5, 3, 8},
                    {4, 1, 14, 8, 13, 6, 2, 11, 15, 12, 9, 7, 3, 10, 5, 0},
                    {15, 12, 8, 2, 4, 9, 1, 7, 5, 11, 3, 14, 10, 0, 6, 13}
            },
            // S2
            {
                    {15, 1, 8, 14, 6, 11, 3, 4, 9, 7, 2, 13, 12, 0, 5, 10},
                    {3, 13, 4, 7, 15, 2, 8, 14, 12, 0, 1, 10, 6, 9, 11, 5},
                    {0, 14, 7, 11, 10, 4, 13, 1, 5, 8, 12, 6, 9, 3, 2, 15},
                    {13, 8, 10, 1, 3, 15, 4, 2, 11, 6, 7, 12, 0, 5, 14, 9}
            },
            // S3
            {
                    {10, 0, 9, 14, 6, 3, 15, 5, 1, 13, 12, 7, 11, 4, 2, 8},
                    {13, 7, 0, 9, 3, 4, 6, 10, 2, 8, 5, 14, 12, 11, 15, 1},
                    {13, 6, 4, 9, 8, 15, 3, 0, 11, 1, 2, 12, 5, 10, 14, 7},
                    {1, 10, 13, 0, 6, 9, 8, 7, 4, 15, 14, 3, 11, 5, 2, 12}
            },
            // S4
            {
                    {7, 13, 14, 3, 0, 6, 9, 10, 1, 2, 8, 5, 11, 12, 4, 15},
                    {13, 8, 11, 5, 6, 15, 0, 3, 4, 7, 2, 12, 1, 10, 14, 9},
                    {10, 6, 9, 0, 12, 11, 7, 13, 15, 1, 3, 14, 5, 2, 8, 4},
                    {3, 15, 0, 6, 10, 1, 13, 8, 9, 4, 5, 11, 12, 7, 2, 14}
            },
            // S5
            {
                    {2, 12, 4, 1, 7, 10, 11, 6, 8, 5, 3, 15, 13, 0, 14, 9},
                    {14, 11, 2, 12, 4, 7, 13, 1, 5, 0, 15, 10, 3, 9, 8, 6},
                    {4, 2, 1, 11, 10, 13, 7, 8, 15, 9, 12, 5, 6, 3, 0, 14},
                    {11, 8, 12, 7, 1, 14, 2, 13, 6, 15, 0, 9, 10, 4, 5, 3}
            },
            // S6
            {
                    {12, 1, 10, 15, 9, 2, 6, 8, 0, 13, 3, 4, 14, 7, 5, 11},
                    {10, 15, 4, 2, 7, 12, 9, 5, 6, 1, 13, 14, 0, 11, 3, 8},
                    {9, 14, 15, 5, 2, 8, 12, 3, 7, 0, 4, 10, 1, 13, 11, 6},
                    {4, 3, 2, 12, 9, 5, 15, 10, 11, 14, 1, 7, 6, 0, 8, 13}
            },
            // S7
            {
                    {4, 11, 2, 14, 15, 0, 8, 13, 3, 12, 9, 7, 5, 10, 6, 1},
                    {13, 0, 11, 7, 4, 9, 1, 10, 14, 3, 5, 12, 2, 15, 8, 6},
                    {1, 4, 11, 13, 12, 3, 7, 14, 10, 15, 6, 8, 0, 5, 9, 2},
                    {6, 11, 13, 8, 1, 4, 10, 7, 9, 5, 0, 15, 14, 2, 3, 12}
            },
            // S8
            {
                    {13, 2, 8, 4, 6, 15, 11, 1, 10, 9, 3, 14, 5, 0, 12, 7},
                    {1, 15, 13, 8, 10, 3, 7, 4, 12, 5, 6, 11, 0, 14, 9, 2},
                    {7, 11, 4, 1, 9, 12, 14, 2, 0, 6, 10, 13, 15, 3, 5, 8},
                    {2, 1, 14, 7, 4, 10, 8, 13, 15, 12, 9, 0, 3, 5, 6, 11}
            }
    };

    public static String Encryption(String plainText, String key) {
        String binaryPlaintext = hexToBinary(plainText);
        String binaryKey = hexToBinary(key);
        List<String> allPossibleSubkeys = generatePossibleSubkeys(binaryKey);
        String plaintextAfterInitialPermutation = applyInitialPermutation(binaryPlaintext);
        String leftString = plaintextAfterInitialPermutation.substring(0, 32);
        String rightString = plaintextAfterInitialPermutation.substring(32);
        for (int i = 0; i < 16; i++) {
            String temp = rightString;
            String singlePossibleKey = allPossibleSubkeys.get(i);
            rightString = applyXOROnTwoBinaryStrings(applyLeftFunction(rightString, singlePossibleKey), leftString);
            leftString = temp;
            System.out.println("Round " + (i+1) + ":");
            printAlignedHexAndBinary(binaryToHex(leftString),binaryToHex(rightString),leftString,rightString);
            System.out.println();
//            System.out.println("Round " + i + " (HEXADECIMAL): " + binaryToHex(leftString) + " " + binaryToHex(rightString));
//            System.out.println("Round " + i + " (BINARY):      " + leftString + " " + rightString + "\n");
        }
        String cipherText = applyFinalPermutation(rightString + leftString);
        return binaryToHex(cipherText);
    }

    public static String hexToBinary(String hexadecimalString) {
        StringBuilder binaryString = new StringBuilder();
        for (int i = 0; i < hexadecimalString.length(); i++) {
            char singleCharacter = hexadecimalString.charAt(i);
            String binaryDigit = Integer.toBinaryString(Character.digit(singleCharacter, 16));
            binaryString.append(String.format("%4s", binaryDigit).replace(' ', '0'));
        }

        return binaryString.toString();
    }

    private static String binaryToHex(String binaryString) {
        StringBuilder hexadecimalString = new StringBuilder();
        for (int i = 0; i < binaryString.length(); i += 4) {
            int decimalCharacter = Integer.parseInt(binaryString.substring(i, i + 4), 2);
            hexadecimalString.append(Integer.toHexString(decimalCharacter).toUpperCase());
        }
        return hexadecimalString.toString();
    }

    private static String applyInitialPermutation(String plainText) {
        StringBuilder permutedString = new StringBuilder();
        for (int i = 0; i < IP.length; i++) {
            permutedString.append(plainText.charAt(IP[i] - 1));
        }
        return permutedString.toString();
    }

    private static String applyFinalPermutation(String cipherText) {
        StringBuilder permutedString = new StringBuilder();
        for (int i = 0; i < IP_1.length; i++) {
            permutedString.append(cipherText.charAt(IP_1[i] - 1));
        }
        return permutedString.toString();
    }

    private static String applyPC1(String key) {
        StringBuilder keyAfterPC1 = new StringBuilder();
        for (int i = 0; i < PC1.length; i++) {
            keyAfterPC1.append(key.charAt(PC1[i] - 1));
        }
        return keyAfterPC1.toString();
    }

    private static String applyPC2(String key) {
        StringBuilder keyAfterPC2 = new StringBuilder();
        for (int i = 0; i < PC2.length; i++) {
            keyAfterPC2.append(key.charAt(PC2[i] - 1));
        }
        return keyAfterPC2.toString();
    }

    private static String applyExpansionFunction(String right) {
        StringBuilder rightStringAfterExpansion = new StringBuilder();
        for (int i = 0; i < E.length; i++) {
            rightStringAfterExpansion.append(right.charAt(E[i] - 1));
        }
        return rightStringAfterExpansion.toString();
    }

    private static String applySBoxSubstitution(String right) {
        StringBuilder rightStringAfterSubstitution = new StringBuilder();
        for (int i = 0; i < 8; i++) {
            String singleBlock = right.substring(i * 6, (i + 1) * 6);
            int row = Integer.parseInt(singleBlock.charAt(0) + "" + singleBlock.charAt(5), 2);
            int col = Integer.parseInt(singleBlock.substring(1, 5), 2);
            int newValue = S[i][row][col];
            rightStringAfterSubstitution.append(String.format("%4s", Integer.toBinaryString(newValue)).replace(' ', '0'));
        }
        return rightStringAfterSubstitution.toString();
    }

    private static String applyPermutationAfterSubstitution(String substitutedString) {
        StringBuilder stringAfterPermutation = new StringBuilder();
        for (int i = 0; i < P.length; i++) {
            stringAfterPermutation.append(substitutedString.charAt(P[i] - 1));
        }
        return stringAfterPermutation.toString();
    }

    private static String applyRoundFunction(String right, String key) {
        String expandedRightString = applyExpansionFunction(right);
        String stringAfterXOR = applyXOROnTwoBinaryStrings(expandedRightString, key);
        String substitutedString = applySBoxSubstitution(stringAfterXOR);
        return applyPermutationAfterSubstitution(substitutedString);
    }

    private static String applyXOROnTwoBinaryStrings(String a, String b) {
        StringBuilder xorResultString = new StringBuilder();
        if ( a.length() != b.length() )
        {
            return null;
        }
        for (int i = 0; i < a.length(); i++) {
            xorResultString.append(a.charAt(i) ^ b.charAt(i));
        }
        return xorResultString.toString();
    }

    private static String applyLeftCircularShift(String key, int round) {
        int shiftKey = leftShifts[round];
        return key.substring(shiftKey) + key.substring(0, shiftKey);
    }

    private static List<String> generatePossibleSubkeys(String key) {
        List<String> allPossibleSubkeys = new ArrayList<>();
        String stringAfterPC1 = applyPC1(key);
        String leftString = stringAfterPC1.substring(0, 28);
        String rightString = stringAfterPC1.substring(28);
        for (int i = 0; i < 16; i++) {
            leftString = applyLeftCircularShift(leftString, i);
            rightString = applyLeftCircularShift(rightString, i);
            String singleRoundKey = applyPC2(leftString + rightString);
            allPossibleSubkeys.add(singleRoundKey);
        }
        return allPossibleSubkeys;
    }

    private static String applyLeftFunction(String right, String key) {
        return applyRoundFunction(right, key);
    }

    ///Note: Generated with ChatGPT to print the round prettier.
    public static void printAlignedHexAndBinary(String leftHex, String rightHex, String leftBinary, String rightBinary) {
        for (int i = 0; i < leftBinary.length(); i += 4) {
            System.out.print(" " + leftHex.charAt(i / 4));
            System.out.print("   ");
        }
        System.out.print("  |   ");
        for (int i = 0; i < rightBinary.length(); i += 4) {
            System.out.print(" " +rightHex.charAt(i / 4));
            System.out.print("   ");
        }
        System.out.println();
        for (int i = 0; i < leftBinary.length(); i += 4) {
            System.out.print(leftBinary.substring(i, Math.min(i + 4, leftBinary.length())) + " ");
        }
        System.out.print("  |   ");
        for (int i = 0; i < rightBinary.length(); i += 4) {
            System.out.print(rightBinary.substring(i, Math.min(i + 4, rightBinary.length())) + " ");
        }
        System.out.println();
    }
}