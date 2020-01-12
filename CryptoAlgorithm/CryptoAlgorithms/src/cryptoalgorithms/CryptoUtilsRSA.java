package cryptoalgorithms;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.*;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Arrays;
import java.util.Base64;

public class CryptoUtilsRSA {

    private static String publicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQChE4a7nWt7eP5f8wYGRkY1/zok+JuS85Id0VYWBj2L0YNqq/bDV8a3kzsxSvF5XAmSxpnnTeWqr7Bn+Bira3jCEIo7dvuA0Wg0G+T35aU48nURTFvoSMdiWId7550mzy6ViVHsENNftO2ADcF9wBpmVyUnS1qu60DdujbUeaaGuwIDAQAB";
    private static String privateKey = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAKEThruda3t4/l/zBgZGRjX/OiT4m5Lzkh3RVhYGPYvRg2qr9sNXxreTOzFK8XlcCZLGmedN5aqvsGf4GKtreMIQijt2+4DRaDQb5PflpTjydRFMW+hIx2JYh3vnnSbPLpWJUewQ01+07YANwX3AGmZXJSdLWq7rQN26NtR5poa7AgMBAAECgYBKY9U2spYgGRoJwBgJL81fRLdaucJH1Nunj7VdSJaGC0XRLwgw4UnyIDoXItBwxvPY6IXNkGlAMhGbgrYJ/QFwiGywLC70YpH4X/REtPH/qqK3gFaZ77N67uruvOq8EFWyQuGZ01iN9DnS/t7QSoyCJD1q9ixJIySijvMxHivtUQJBAPIw0pxPTvAilZ16G42081F1lNEmaIITvwVIpi3rQf+/aWAIp6yWh1hCQg85bi57rrMkwzWPCNImwoKccYmykBMCQQCqQrP/JIgBAOovg+ZBKduTkDgoGWhYRRGj5xGKwdQ0369blmG5e7Pxhi1VoC6MMQ2EdOp4Xz9sDVQm4jQIyRO5AkAK5eGEQRlXOucql+UHgl30i2rv4l+Pgt30melIZa6pmG5gcONCo99hPFIGp1nq44vf6caS8WW/NxlHkUyWmp9bAkAbr6cXaThYeW6bdWZe2FClaUFIfAE0wV6B+ZQnrSc1xd39FbIkeFIlhz0d/GM1VtCs5louYsasampxvDzb5N65AkEAlLHF52XDd7pENMFqSQofpjODtFcxmEI5Yzca/Jw3tJz7MRz5zXGbk0OczD7Jf1VPIW7sYhmFsMA8yYEnIMXmaw==";

    public static PublicKey getPublicKey(String base64PublicKey){
        PublicKey publicKey = null;
        try{
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(Base64.getDecoder().decode(base64PublicKey.getBytes()));
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            publicKey = keyFactory.generatePublic(keySpec);
            return publicKey;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        }
        return publicKey;
    }

    public static PrivateKey getPrivateKey(String base64PrivateKey){
        PrivateKey privateKey = null;
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(Base64.getDecoder().decode(base64PrivateKey.getBytes()));
        KeyFactory keyFactory = null;
        try {
            keyFactory = KeyFactory.getInstance("RSA");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        try {
            privateKey = keyFactory.generatePrivate(keySpec);
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        }
        return privateKey;
    }

    public static byte[] encrypt(byte[] data, String publicKey) throws BadPaddingException, IllegalBlockSizeException, InvalidKeyException, NoSuchPaddingException, NoSuchAlgorithmException {
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        cipher.init(Cipher.ENCRYPT_MODE, getPublicKey(publicKey));
        return cipher.doFinal(data);
    }

    public static byte[] decrypt(byte[] data, PrivateKey privateKey) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        return cipher.doFinal(data);
    }

    public static byte[] decrypt(byte[] data, String base64PrivateKey) throws IllegalBlockSizeException, InvalidKeyException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException {
        return decrypt(Base64.getDecoder().decode(data), getPrivateKey(base64PrivateKey));
    }
    public static void encryptRSA(File inputFile, File outputFile) throws InvalidKeyException, BadPaddingException, NoSuchPaddingException, IllegalBlockSizeException {
        try {
            FileOutputStream writer = new FileOutputStream(outputFile);
            writer.write(("").getBytes());
            writer.close();
            FileInputStream inputStream = new FileInputStream(inputFile);
            byte[] inputBytes = new byte[(int) inputFile.length()];
            inputStream.read(inputBytes);
            byte[] outputBytes = new byte[0];
            FileOutputStream outputStream = new FileOutputStream(outputFile,true);
            byte[] slice2 = new byte[0];
            byte[] slice1 = new byte[0];
            for (int i = 0; i < inputFile.length(); i = i + 117) {
               byte[] slice = Arrays.copyOfRange(inputBytes, i, i + 117);
                outputBytes = Base64.getEncoder().encode(encrypt(slice, publicKey));
                outputStream.write(outputBytes);
            }
            inputStream.close();
            outputStream.close();
        } catch (NoSuchAlgorithmException | IOException e) {
            System.err.println(e.getMessage());
        }
    }
    public static void decryptRSA(File inputFile, File outputFile) throws InvalidKeyException, BadPaddingException, NoSuchPaddingException, IllegalBlockSizeException {
        try {
            FileInputStream inputStream = new FileInputStream(inputFile);
            byte[] inputBytes = new byte[(int) inputFile.length()];
            FileOutputStream outputStream = new FileOutputStream(outputFile);
            inputStream.read(inputBytes);
            byte[] outputBytes = new byte[0];

            for (int i = 0; i < inputFile.length(); i = i + 172) {
                byte[] slice = Arrays.copyOfRange(inputBytes, i, i + 172);
                outputBytes = CryptoUtilsRSA.decrypt(slice, privateKey);//Base64.getEncoder().encode(encrypt(inputBytes, publicKey));
                outputStream.write(outputBytes);
            }
            inputStream.close();
            outputStream.close();
        } catch (NoSuchAlgorithmException | IOException e) {
            System.err.println(e.getMessage());
        }
    }
}