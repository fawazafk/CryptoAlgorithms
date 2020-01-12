package cryptoalgorithms;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class CryptoUtilsSHA256 {

    public static void fileSha256ToBase64(File inputFile, File outputFile) throws NoSuchAlgorithmException, IOException {
        byte[] data = Files.readAllBytes(inputFile.toPath());
        MessageDigest digester = MessageDigest.getInstance("SHA-256");
        digester.update(data);
        //return Base64.getEncoder().encodeToString(digester.digest());

        try {
            FileInputStream inputStream = new FileInputStream(inputFile);
            byte[] inputBytes = new byte[(int) inputFile.length()];
            inputStream.read(inputBytes);
            byte[] outputBytes = Base64.getEncoder().encode(digester.digest());
            FileOutputStream outputStream = new FileOutputStream(outputFile);
            outputStream.write(outputBytes);
            inputStream.close();
            outputStream.close();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
}
