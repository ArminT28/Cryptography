//package Assignment4.Exercise1;

import java.util.*;

public class RSA_Encryption {
    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        boolean exit = false;

        int bob_p,bob_q,bob_b,blocklength;

        System.out.print("Please Input blocklength: ");
        blocklength = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Please Input Bob's p: ");
        bob_p = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Please Input Bob's q: ");
        bob_q = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Please Input Bob's b: ");
        bob_b = scanner.nextInt();
        scanner.nextLine();

        var Bob = new Person(bob_q,bob_p,bob_b);

        ///Random numbers for Alice because we didn't get any input for Alice.
        ///Trying to simulate the real life person case.
        var Alice = new Person(17, 19, 5);

        while (!exit) {

            try {
                System.out.print("1.) Encryption\n2.) Decryption\n3.) Exit\nEnter your choice: ");
                int choice = scanner.nextInt();
                scanner.nextLine();

                if (choice == 1) {
                    System.out.print("Enter plaintext: ");
                    String plaintext = scanner.nextLine();

                    ///Encryption
                    var BobPublicKey = Bob.return_public_key();
                    var cipherText = Alice.encrypt(BobPublicKey.get("n"),BobPublicKey.get("b"),plaintext,blocklength);
                    System.out.println("CipherText: " + cipherText);

                    System.out.print("Do you want to continue with another Cipher? (Y/N): ");
                    String response = scanner.nextLine();
                    exit = !response.equals("Y") && !response.equals("y");
                } else if (choice == 2) {
                    System.out.print("Enter ciphertext: ");
                    String ciphertext = scanner.nextLine();

                    ///Decryption
                    var plainText = Bob.decrypt(Bob.return_public_key().get("n"),Bob.returnPrivateKey().get("a"),ciphertext,blocklength);
                    System.out.println("PlainText: " + plainText);

                    System.out.print("Do you want to continue with another Cipher? (Y/N): ");
                    String response = scanner.nextLine();
                    exit = !response.equals("Y") && !response.equals("y");
                } else if (choice == 3) {
                    scanner.close();
                    break;
                } else {
                    System.out.println("Invalid choice. Please enter 1 or 2.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid integer for the input.");
                scanner.nextLine();
            } catch (Exception e) {
                System.out.println("An error occurred: " + e.getMessage());
                break;
            }
        }
    }

    public static class Person {
        public int p;
        public int q;
        public int n;
        public int phi;
        public int a;
        public int b;

        public Person(int q, int p,int b) throws Exception {
            this.p = p;
            this.q = q;
            this.n = q * p;
            this.phi = (q - 1) * (p - 1);
            this.b = b;
            check_coprime_b_phi(this.b,this.phi);
            this.a = inverse_modulo(b, phi);
        }

        private void check_coprime_b_phi(int b, int phi) {
            if (gcd(b, phi) != 1) {
                throw new InputMismatchException("b and phi should be coprime");
            }
        }

        public static int gcd(int a, int b) {
            while (b != 0) {
                int temp = b;
                b = a % b;
                a = temp;
            }
            return a;
        }

        private int inverse_modulo(int b, int phi) throws Exception {
            var result = 0;
            for ( result = 3; result < phi; result++) {
                if ((result * b) % phi == 1) {
                    return result;
                }
            }
            throw new Exception("No inverse modulo found");
        }

        public Map<String,Integer> return_public_key() {
            Map<String,Integer> publicKey = new HashMap<>();
            publicKey.put("n",this.n);
            publicKey.put("b",this.b);
            return publicKey;
        }

        public Map<String, Integer> returnPrivateKey() {
            Map<String, Integer> privateKey = new HashMap<>();
            privateKey.put("p", this.p);
            privateKey.put("q", this.q);
            privateKey.put("a", this.a);
            return privateKey;
        }

        public String encrypt(Integer n, Integer b, String plaintext, int blockLength) {
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

        public String decrypt(Integer n, Integer a, String ciphertext,int blocklength) {
            var numberOfDigitsInN = (int) Math.log10(n) + 1;

            var separateBlocks = new ArrayList<Integer>();

            for (int i = 0; i < ciphertext.length(); i += numberOfDigitsInN) {
                var block = Integer.parseInt(ciphertext.substring(i, Math.min(i + numberOfDigitsInN, ciphertext.length())));
                separateBlocks.add(block);
            }

            var decryptedBlocks = new ArrayList<String>();
            for( int block : separateBlocks ){
                int decryptedValue=1;
                for (int j = 0; j < a; j++) {
                    decryptedValue = (decryptedValue * block) % n;
                }
               var decryptedBlock = String.format("%0" + numberOfDigitsInN + "d", decryptedValue);
                decryptedBlocks.add(decryptedBlock);
            }

            var plainText = new ArrayList<String>();
            for (String decryptedblock : decryptedBlocks ) {
                StringBuilder blockText = new StringBuilder();
                int plainVal = Integer.parseInt(decryptedblock);
                for (int j = 0; j < blocklength; j++) {
                    char ch = (char) ('A' + plainVal % 26);
                    blockText.insert(0, ch);
                    plainVal /= 26;
                }
                plainText.add(blockText.toString());
            }

//            System.out.println(plainText);

            return String.join("", plainText);
        }
    }

}

