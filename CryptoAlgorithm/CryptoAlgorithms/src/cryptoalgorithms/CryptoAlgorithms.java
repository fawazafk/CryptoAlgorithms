/*
 *  By: Fawaz Kserawi: fawazafk@gmail.com
 *  This programs asks for a .txt file and then ecrypts and decrypts the text using cryptographic algorithms saving each output to a file.
 *  It also calculate time used to encrypt/decrypt the file and outputs a comparison table 
 *  Encryption used: AES, DES, RC4, RSA and SHA256 (for SHA256 hashing only)
 */
package cryptoalgorithms;
import java.io.File;
import java.util.Scanner;

import static cryptoalgorithms.CryptoUtilsRSA.*;

/**
 *
 * @author fawaz
 */
public class CryptoAlgorithms {
    public static void main(String[] args) throws Exception {
        String key = "12345672";
        File inputFile = new File("Tale_of_Two_Cities.txt");
        File encryptedFileRC4 = new File("encryptedRC4.txt");
        File encryptedFileAES128 = new File("encryptedAES128.txt");
        File encryptedFileAES256 = new File("encryptedAES256.txt");
        File encryptedFileDES = new File("encryptedDES.txt");
        File encryptedFileRSA = new File("encryptedRSA.txt");
        File hashedFileSHA256 = new File("hashedFileSHA256.txt");
        File decryptedFileRC4 = new File("decryptedRC4.txt");
        File decryptedFileAES128 = new File("decryptedAES128.txt");
        File decryptedFileAES256= new File("decryptedAES256.txt");
        File decryptedFileDES = new File("decryptedDES.txt");
        File decryptedFileRSA = new File("decryptedRSA.txt");
        long startTime,endTime,processingTime;
        float relativeSize;
        Scanner sc = new Scanner(System.in);
        StringBuilder sb = new StringBuilder();
        sb.append("Cipher\tEncryption time(Millisecond)\tDecryption time(Millisecond)\tRelative ciphertext size\n");

        try {
            System.out.println("Please input full file path\\Filename. \nExample: C:\\document.txt. (press return for default:Tale_of_Two_Cities.txt)");
            String path=sc.nextLine();
            if (!path.equals("")){
                inputFile=new File(path);
            }
            System.out.println("Processing........");

// RC4:
            sb.append("RC4   \t\t\t");
            startTime = System.currentTimeMillis();
            CryptoUtilsRC4.encrypt("12345672", "RC4", "RC4", inputFile, encryptedFileRC4);
            endTime = System.currentTimeMillis();
            processingTime=endTime-startTime;
            sb.append(processingTime+"                          \t");
            startTime = System.currentTimeMillis();
            CryptoUtilsRC4.decrypt("12345672", "RC4", "RC4", encryptedFileRC4, decryptedFileRC4);
            endTime = System.currentTimeMillis();
            processingTime=endTime-startTime;
            sb.append(processingTime+"                          \t");
            relativeSize=(float)encryptedFileRC4.length()/inputFile.length();
            sb.append((relativeSize)+"\n");

// AES128:
            sb.append("AES128   \t\t");
            startTime = System.currentTimeMillis();
            CryptoUtilsAES.encrypt("1234567212345677", "AES", "AES/CBC/PKCS5Padding", inputFile, encryptedFileAES128); //Same as CryptoUtilsRC4
            endTime = System.currentTimeMillis();
            processingTime=endTime-startTime;
            sb.append(processingTime+"                          \t");
            startTime = System.currentTimeMillis();
            CryptoUtilsAES.decrypt("1234567212345677", "AES", "AES/CBC/PKCS5Padding", encryptedFileAES128, decryptedFileAES128);//Same as CryptoUtilsRC4
            endTime = System.currentTimeMillis();endTime = System.currentTimeMillis();
            processingTime=endTime-startTime;
            sb.append(processingTime+"                          \t");
            relativeSize=(float)encryptedFileAES128.length()/inputFile.length();
            sb.append((relativeSize)+"\n");

// AES256:
            sb.append("AES256   \t\t");
            startTime = System.currentTimeMillis();
            CryptoUtilsAES.encrypt("1234567212345677dad2123234223222", "AES", "AES/CBC/PKCS5Padding", inputFile, encryptedFileAES256);
            endTime = System.currentTimeMillis();
            processingTime=endTime-startTime;
            sb.append(processingTime+"                          \t");
            startTime = System.currentTimeMillis();
            CryptoUtilsAES.decrypt("1234567212345677dad2123234223222", "AES", "AES/CBC/PKCS5Padding", encryptedFileAES256, decryptedFileAES256);//Same as CryptoUtilsRC4
            endTime = System.currentTimeMillis();
            processingTime=endTime-startTime;
            sb.append(processingTime+"                          \t");
            relativeSize=(float)encryptedFileAES256.length()/inputFile.length();
            sb.append((relativeSize)+"\n");

// DES:
            sb.append("DES   \t\t\t");
            startTime = System.currentTimeMillis();
            CryptoUtilsDES.encrypt("12345672", "DES", "DES/CBC/PKCS5Padding", inputFile, encryptedFileDES);
            endTime = System.currentTimeMillis();
            processingTime=endTime-startTime;
            sb.append(processingTime+"                          \t");
            startTime = System.currentTimeMillis();
            CryptoUtilsDES.decrypt("12345672", "DES", "DES/CBC/PKCS5Padding", encryptedFileDES, decryptedFileDES);
            endTime = System.currentTimeMillis();
            processingTime=endTime-startTime;
            sb.append(processingTime+"                          \t");
            relativeSize=(float)encryptedFileDES.length()/inputFile.length();
            sb.append((relativeSize)+"\n");

// RSA:
            sb.append("RSA   \t\t\t");
            startTime = System.currentTimeMillis();
            encryptRSA(inputFile,encryptedFileRSA);  //length of 114
            endTime = System.currentTimeMillis();
            processingTime=endTime-startTime;
            sb.append(processingTime+"                          \t");
            startTime = System.currentTimeMillis();
            decryptRSA(encryptedFileRSA,decryptedFileRSA);
            endTime = System.currentTimeMillis();
            processingTime=endTime-startTime;
            sb.append(processingTime+"                          \t");
            relativeSize=(float)encryptedFileRSA.length()/inputFile.length();
            sb.append((relativeSize)+"\n");

//SHA256:
            sb.append("SHA256   \t\t");
            startTime = System.currentTimeMillis();
            CryptoUtilsSHA256.fileSha256ToBase64(inputFile,hashedFileSHA256);
            endTime = System.currentTimeMillis();
            processingTime=endTime-startTime;
            sb.append(processingTime+"                          \t");
            sb.append("                       \t\t");
            relativeSize=(float)hashedFileSHA256.length()/inputFile.length();
            sb.append((relativeSize)+"\n");

            System.out.println(sb.toString());
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        }
    }
}
