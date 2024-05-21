//package Assignment5.Excersie1;

import java.math.BigInteger;

public class ElGamal {
//    (a) Verify β by the function verify_beta(α, a, p).
    public static int compute_beta(int alpha, int a, int p) {
//        Beta is ( L ^ a ) mod p
        BigInteger alpha_Big = BigInteger.valueOf(alpha);
        BigInteger a_Big = BigInteger.valueOf(a);
        BigInteger p_Big = BigInteger.valueOf(p);

        BigInteger Beta = alpha_Big.modPow(a_Big, p_Big);
        int beta_int = Beta.intValue();
        return beta_int;
    }

    private static int inverse_modulo_of_a_number(int a, int m) {
        for (int i = 1; i < m; i++) {
            if ( ( (a * i) - 1 ) % m == 0) {
                return i;
            }
        }
        return -1; // Inverse doesn't exist
    }

//    (b)Compute signed message from Bob by function Sign_ElGamal(p, α, β, a).
    public static int[] Sign_ElGamal(int p, int alpha, int beta,int a) {
//        Chosen message and k
        int Ascii_message = 'A';
        int k = 45;
        BigInteger p_big = BigInteger.valueOf(p);
        BigInteger alpha_big = BigInteger.valueOf(alpha);
        BigInteger k_big = BigInteger.valueOf(k);
        BigInteger wierd_y_Big = alpha_big.modPow(k_big, p_big);
        int wierd_y = wierd_y_Big.intValue();
        int k_inverse_modulo = inverse_modulo_of_a_number(k, p-1);
        int message = Ascii_message % (p - 1);

        // Calculate s
        int s = (k_inverse_modulo * (message - (a * wierd_y) % (p - 1) + (p - 1))) % (p - 1);

        return new int[] { wierd_y, s };
    }

    public static void main(String[] args) {
        int p = 107;
        int alpha = 122503;
        int a = 10;
        int k = 45;
        String message = "B";

        int beta = compute_beta(alpha, a, p);
        System.out.println("Computed Beta: " + beta);

        int[] signedMessage = Sign_ElGamal(p, alpha, beta, a);
        System.out.println("Signed message (wierd_y, wierd_delta): " + signedMessage[0] + ", " + signedMessage[1]);
    }
}