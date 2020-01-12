package cryptoalgorithms;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;

public class CryptoUtilsDES {

    public static void encrypt(String key, String KeyAlgorithm,String Transformation, File inputFile, File outputFile) {
        doCrypto(Cipher.ENCRYPT_MODE, key, KeyAlgorithm,Transformation, inputFile, outputFile);
    }

    public static void decrypt(String key, String KeyAlgorithm,String Transformation, File inputFile, File outputFile) {
        doCrypto(Cipher.DECRYPT_MODE, key, KeyAlgorithm,Transformation, inputFile, outputFile);
    }

    private static void doCrypto(int cipherMode, String key, String KeyAlgorithm,String Transformation, File inputFile,
            File outputFile) {
        try {
            Key secretKey = new SecretKeySpec(key.getBytes(), KeyAlgorithm);
            Cipher cipher = Cipher.getInstance(Transformation);
            cipher.init(cipherMode, secretKey,new IvParameterSpec(new byte[8]));

            FileInputStream inputStream = new FileInputStream(inputFile);
            byte[] inputBytes = new byte[(int) inputFile.length()];
            inputStream.read(inputBytes);

            byte[] outputBytes = cipher.doFinal(inputBytes);

            FileOutputStream outputStream = new FileOutputStream(outputFile);
            outputStream.write(outputBytes);

            inputStream.close();
            outputStream.close();

        } catch (NoSuchPaddingException | NoSuchAlgorithmException
                | InvalidKeyException | BadPaddingException
                | IllegalBlockSizeException | IOException ex) {

        } catch (InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        }
    }
}
