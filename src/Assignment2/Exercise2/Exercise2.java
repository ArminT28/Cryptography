///package Assignment2.Exercise2;
///Delete the above line when you run from the terminal and leave it when you run from IDE

import java.util.*;
import java.util.stream.Collectors;

///Short Example: QOZFZFNCHNSFHRZCPUFTNBXZGGBFHQNSZCWQOHYGPZCQHIQ
///Long Example: LREKMEPQOCPCBOYGYWPPEHFIWPFZYQGDZERGYPWFYWECYOJEQCMYEGFGYPWFCYMJYFGFMFGWPQGDZERGPGFFZEYCIEDBCGPFEHFBEFFERQCPJEEPQRODFEXFWCPOWPEWLYETERCBXGLLEREPFQGDZERFEHFBEFFERYXEDEPXGPSWPGFYDWYGFGWPGPFZEIEYYCSE

public class Exercise2 {
    private static final int CONFIG_ALPHABET_SIZE = 26;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Ciphertext to hack is:");
        String cipherText = scanner.nextLine();

        String result = HackAffine(cipherText);
        System.out.println("Plaintext: " + result);

        scanner.close();
    }

    private static String HackAffine(String cipherText) {
         String UpperCaseCipherText = cipherText.toUpperCase();
         Map<Character,Integer> letterOccurrences = countLetterOccurences(UpperCaseCipherText);
         Map<Character, Integer> sortedOccurrences = sortLetterOccurrencesDictionary(letterOccurrences);
         Map<Character,Float> letterFrequencies = initializeEnglishLetterFrequencies();
         List<List<String>> combinations = generateCombinations(letterFrequencies, sortedOccurrences);

         System.out.println("SortedOccurencies " + sortedOccurrences);
         System.out.println("Frequencies " + letterFrequencies);
         System.out.println("Combinations " + combinations);

        for (List<String> combination : combinations) {
            String firstPair = combination.get(0);
            String secondPair = combination.get(1);

            char frequencyCharacter1 = firstPair.charAt(0);
            char occurenceCharacter1 = firstPair.charAt(2);
            char frequencyCharacter2 = secondPair.charAt(0);
            char occurenceCharacter2 = secondPair.charAt(2);

            int x1 = frequencyCharacter1 - 'A';
            int y1 = occurenceCharacter1 - 'A';
            int x2 = frequencyCharacter2 - 'A';
            int y2 = occurenceCharacter2 - 'A';

            int[] solution = solveMathematicalSystem(x1, y1, x2, y2);
            if(solution !=null) {
                int gcd = gcd(solution[0], CONFIG_ALPHABET_SIZE);
                if (gcd == 1) {
                    int a = solution[0];
                    int b = solution[1];
                    String plainText = Decryption(a,b,cipherText,CONFIG_ALPHABET_SIZE);

                    ///Check if the plaintext has most common Di-, Tri- and Quadrigrams
                    ///If at least 4 Digramms and 2 Trigramm || 2 Quadrigramm is found we found the answer
                    if (numberOfQuadrigrams(plainText)>=2) {
                        System.out.println(frequencyCharacter1 + "->" + occurenceCharacter1 + " " + frequencyCharacter2 + "->" + occurenceCharacter2);
                        System.out.println("Found at least two Quadrigramms for a:" + a + " b:" + b);
                        System.out.println(plainText);
                        System.out.print("Is PlainText readable? (Y/N): ");
                        Scanner scanner = new Scanner(System.in);
                        String answer = scanner.nextLine();
                        if (answer.equals("Y")) {
                            return plainText;
                        }
                    }
                    else if (numberOfTrigramms(plainText)>=4) {
                        System.out.println(frequencyCharacter1 + "->" + occurenceCharacter1 + " " + frequencyCharacter2 + "->" + occurenceCharacter2);
                        System.out.println("Found at least four Trigramms for a:" + a + " b:" + b);
                        System.out.println(plainText);
                        System.out.print("Is PlainText readable? (Y/N): ");
                        Scanner scanner = new Scanner(System.in);
                        String answer = scanner.nextLine();
                        if (answer.equals("Y")) {
                            return plainText;
                        }
                    }
                    else if (numberOfTrigramms(plainText)>=2 && numberOfDigramms(plainText)>=4) {
                        System.out.println(frequencyCharacter1 + "->" + occurenceCharacter1 + " " + frequencyCharacter2 + "->" + occurenceCharacter2);
                        System.out.println("Found at least two Trigramms and at least four Digramms for a:" + a + " b:" + b);
                        System.out.println(plainText);
                        System.out.print("Is PlainText readable? (Y/N): ");
                        Scanner scanner = new Scanner(System.in);
                        String answer = scanner.nextLine();
                        if (answer.equals("Y")) {
                            return plainText;
                        }
                    }
                    else if (numberOfDigramms(plainText)>=6) {
                        System.out.println(frequencyCharacter1 + "->" + occurenceCharacter1 + " " + frequencyCharacter2 + "->" + occurenceCharacter2);
                        System.out.println("Found at least six Digramms for a:" + a + " b:" + b);
                        System.out.println(plainText);
                        System.out.print("Is PlainText readable? (Y/N): ");
                        Scanner scanner = new Scanner(System.in);
                        String answer = scanner.nextLine();
                        if (answer.equals("Y")) {
                            return plainText;
                        }
                    }
                }
            }
        }
        return "Failed to decrypt.";
    }

    private static Map<Character,Integer> countLetterOccurences(String upperCaseCipherText) {
        Map<Character, Integer> letterCounts = new LinkedHashMap<>();

        for (char singleCharacter : upperCaseCipherText.toCharArray()) {
            if (Character.isLetter(singleCharacter)) {
                letterCounts.put(singleCharacter, letterCounts.getOrDefault(singleCharacter, 0) + 1);
            }
        }

        return letterCounts;
    }

    private static Map<Character, Integer> sortLetterOccurrencesDictionary(Map<Character, Integer> letterOccurrences) {
        return letterOccurrences.entrySet().stream()
                .sorted((entry1, entry2) -> entry2.getValue().compareTo(entry1.getValue()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
    }


    private static Map<Character, Float> initializeEnglishLetterFrequencies() {
        LinkedHashMap<Character, Float> letterFrequencies = new LinkedHashMap<>();
        letterFrequencies.put('E', 12.575645f);
        letterFrequencies.put('T', 9.085226f);
        letterFrequencies.put('A', 8.000395f);
        letterFrequencies.put('O', 7.591270f);
        letterFrequencies.put('I', 6.920007f);
        letterFrequencies.put('N', 6.903785f);
        letterFrequencies.put('S', 6.340880f);
        letterFrequencies.put('H', 6.236609f);
        letterFrequencies.put('R', 5.959034f);
        letterFrequencies.put('D', 4.317924f);
        letterFrequencies.put('L', 4.057231f);
        letterFrequencies.put('U', 2.841783f);
        letterFrequencies.put('C', 2.575785f);
        letterFrequencies.put('M', 2.560994f);
        letterFrequencies.put('F', 2.350463f);
        letterFrequencies.put('W', 2.224893f);
        letterFrequencies.put('G', 1.982677f);
        letterFrequencies.put('Y', 1.900888f);
        letterFrequencies.put('P', 1.795742f);
        letterFrequencies.put('B', 1.535701f);
        letterFrequencies.put('V', 0.981717f);
        letterFrequencies.put('K', 0.739906f);
        letterFrequencies.put('X', 0.179556f);
        letterFrequencies.put('J', 0.145188f);
        letterFrequencies.put('Q', 0.117571f);
        letterFrequencies.put('Z', 0.079130f);

        return letterFrequencies;
    }

    public static List<List<String>> generateCombinations(Map<Character, Float> freqDict, Map<Character, Integer> occurrencesDict) {
        List<List<String>> combinations = new ArrayList<>();

        List<Character> letters1 = new ArrayList<>(freqDict.keySet());
        letters1.sort(Comparator.comparingDouble(freqDict::get).reversed());

        List<Character> letters2 = new ArrayList<>(occurrencesDict.keySet());
        letters2.sort(Comparator.comparingInt(occurrencesDict::get).reversed());

        for (int index1 = 0; index1 < letters1.size()-1; index1++) {
            for (int index2 = 0; index2 < letters2.size(); index2++)
            {
                for(int index3 = index2+1; index3 < letters2.size(); index3++)
                {
                    List<String> combination = new ArrayList<>();
                    combination.add(letters1.get(index1) + "=" + letters2.get(index2));
                    combination.add(letters1.get(index1+1) + "=" + letters2.get(index3));
                    combinations.add(combination);
                }
            }
        }
        return combinations;
    }

    public static int[] solveMathematicalSystem(int x1, int y1, int x2, int y2) {
        int[] solution = new int[2];

        if (x1 == x2) {
            if (y1 == y2) {
                return null; // Infinite solutions
            } else {
                return null; // No solution
            }
        } else {
            int inverse_x1 = inverse_modulo_of_a_number((int) x1, 26);

            int a = ((y1 - y2) * inverse_x1) % 26;
            if (a < 0)
                a += 26;

            int b = (y1 - (x1 * a)) % 26;
            if (b < 0)
                b += 26;

            solution[0] = a;
            solution[1] = b;

            return solution;
        }
    }

    private static int gcd(int a, int b) {
        if (b == 0) {
            return a;
        }
        return gcd(b, a % b);
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
                int decryptedChar = ((a_inverse_modulo * ((single_character_upper - 'A') - b)) % m + m ) % m;
                char decrypted = (char) (decryptedChar + 'A');
                plaintext.append(decrypted);
            } else {
                plaintext.append(single_character);
            }
        }
        return plaintext.toString();
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

