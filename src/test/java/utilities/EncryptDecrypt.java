package utilities;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.util.Base64;
import java.util.Properties;

public class EncryptDecrypt {
    private static final String KEY_FILE = "src/main/java/config/secret.key"; // Store key securely
    private static SecretKey secretKey;

    /**
     * Static block to load or generate AES Secret Key.
     * If the key file exists, it reads the key; otherwise, it generates a new key and stores it.
     */
    static {
        try {
            File keyFile = new File(KEY_FILE);
            if (keyFile.exists()) {
                // Load the key if already generated
                try (FileInputStream fis = new FileInputStream(KEY_FILE)) {
                    byte[] keyBytes = new byte[16];
                    fis.read(keyBytes);
                    secretKey = new SecretKeySpec(keyBytes, "AES");
                }
            } else {
                // Generate and store the key if not present
                KeyGenerator keyGen = KeyGenerator.getInstance("AES");
                keyGen.init(128);
                secretKey = keyGen.generateKey();
                try (FileOutputStream fos = new FileOutputStream(KEY_FILE)) {
                    fos.write(secretKey.getEncoded());
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("Error initializing encryption key", e);
        }
    }

    /**
     * Encrypts a given password using AES encryption.
     *
     * @param password The plain text password to be encrypted.
     * @return The encrypted password as a Base64 encoded string.
     * @throws Exception If an encryption error occurs.
     */
    public static String encrypt(String password) throws Exception {
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        byte[] encryptedBytes = cipher.doFinal(password.getBytes());
        return Base64.getEncoder().encodeToString(encryptedBytes);
    }

    /**
     * Decrypts an encrypted password using AES decryption.
     *
     * @param encryptedPassword The encrypted password in Base64 format.
     * @return The decrypted password in plain text.
     * @throws Exception If a decryption error occurs.
     */
    public static String decrypt(String encryptedPassword) throws Exception {
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(encryptedPassword));
        return new String(decryptedBytes);
    }

    /**
     * Stores the encrypted password in a configuration file.
     *
     * @param encryptedPassword The encrypted password to be stored.
     */
    public static void saveToConfig(String encryptedPassword) {
        Properties properties = new Properties();
        try (FileOutputStream fos = new FileOutputStream("src/main/java/config/config.properties")) {
            properties.setProperty("encrypted.password", encryptedPassword);
            properties.store(fos, "Encrypted Password Configuration");
            System.out.println("Encrypted password saved to config.properties");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Retrieves the encrypted password from the configuration file.
     *
     * @return The encrypted password as a string, or null if not found.
     */
    public static String getEncryptedPassword() {
        Properties properties = new Properties();
        try (FileInputStream fis = new FileInputStream("src/main/java/config/config.properties")) {
            properties.load(fis);
            return properties.getProperty("encrypted.password");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Main method to encrypt and save the password, then retrieve and decrypt it.
     *
     * @param args Command-line arguments (not used).
     */
    public static void main(String[] args) {
        try {
            String password = ""; // Original password
            String encryptedPassword = encrypt(password);
            System.out.println("Encrypted Password: " + encryptedPassword);
            saveToConfig(encryptedPassword);

            // Retrieve encrypted password
            String retrievedEncryptedPassword = getEncryptedPassword();
            if (retrievedEncryptedPassword == null || retrievedEncryptedPassword.isEmpty()) {
                System.out.println("ERROR: No encrypted password found in config.properties!");
                return;
            }

            // Decrypt and print
            String decryptedPassword = decrypt(retrievedEncryptedPassword);
            System.out.println("Decrypted Password: " + decryptedPassword);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
