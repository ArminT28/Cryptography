package Assignment2.Exercise2;

import java.util.*;
import java.util.stream.Collectors;

public class Exercise2 {
    private static final int CONFIG_ALPHABET_SIZE = 26;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Ciphertext to hack is:");
        String cipherText = scanner.nextLine();
        scanner.close();

        String result = HackAffine(cipherText);
        System.out.println("Plaintext: " + result);
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

            int keyA = occurenceCharacter1 - frequencyCharacter1;
            int keyB = occurenceCharacter2 - frequencyCharacter2;

            System.out.println("KeyA: " + keyA + " KeyB: " + keyB);
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
}
