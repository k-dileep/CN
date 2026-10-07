//Write a program for simple RSA algorithm to encrypt and decrypt the data.


import java.io.DataInputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.util.Random;

public class RSA {

    private BigInteger p;
    private BigInteger q;
    private BigInteger N;
    private BigInteger phi;
    private BigInteger e;
    private BigInteger d;

    private int bitlength = 1024;
    private Random r;

    public RSA() {

        r = new Random();

        // Generate two prime numbers
        p = BigInteger.probablePrime(bitlength, r);
        q = BigInteger.probablePrime(bitlength, r);

        // Calculate N = p * q
        N = p.multiply(q);

        // Calculate phi = (p - 1)(q - 1)
        phi = p.subtract(BigInteger.ONE)
                .multiply(q.subtract(BigInteger.ONE));

        // Generate public exponent e
        e = BigInteger.probablePrime(bitlength / 2, r);

        // Make sure gcd(e, phi) = 1
        while (!phi.gcd(e).equals(BigInteger.ONE)) {
            e = e.add(BigInteger.ONE);
        }

        // Calculate private exponent d
        d = e.modInverse(phi);
    }

    public RSA(BigInteger e, BigInteger d, BigInteger N) {
        this.e = e;
        this.d = d;
        this.N = N;
    }

    @SuppressWarnings("deprecation")
    public static void main(String[] args) throws IOException {

        RSA rsa = new RSA();

        DataInputStream in = new DataInputStream(System.in);

        String teststring;

        System.out.println("Enter the plain text:");
        teststring = in.readLine();

        System.out.println("Encrypting String: " + teststring);

        System.out.println("String in Bytes: "
                + bytesToString(teststring.getBytes()));

        // Encrypt
        byte[] encrypted = rsa.encrypt(teststring.getBytes());

        System.out.println("Encrypted Bytes: "
                + bytesToString(encrypted));

        // Decrypt
        byte[] decrypted = rsa.decrypt(encrypted);

        System.out.println("Decrypting Bytes: "
                + bytesToString(decrypted));

        System.out.println("Decrypted String: "
                + new String(decrypted));
    }

    // Convert byte array to String
    private static String bytesToString(byte[] encrypted) {

        String test = "";

        for (byte b : encrypted) {
            test += Byte.toString(b);
        }

        return test;
    }

    // Encrypt message
    public byte[] encrypt(byte[] message) {

        return new BigInteger(1, message)
                .modPow(e, N)
                .toByteArray();
    }

    // Decrypt message
    public byte[] decrypt(byte[] message) {

        return new BigInteger(1, message)
                .modPow(d, N)
                .toByteArray();
    }
}