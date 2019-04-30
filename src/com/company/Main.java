package com.company;

//import com.company.backup.FileEncrypterDecrypter;
import com.company.backup.test;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import javax.swing.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;

public class Main {
    public static String raktas = "AES__256__RAKTAS";

    public static void main(String[] args) {

    }





//        try {
//            SecretKey secretKey = KeyGenerator.getInstance("AES").generateKey();
//            System.out.println(secretKey.toString());
//            FileEncrypterDecrypter fileEncrypterDecrypter = new FileEncrypterDecrypter(secretKey,"AES/GCM/NoPadding");
//            fileEncrypterDecrypter.encrypt("fff", "C:\\Users\\80085\\Desktop\\folder\\carrot-cake.jpg");
////            String text  = fileEncrypterDecrypter.decrypt("C:\\Users\\80085\\Desktop\\folder\\carrot-cake.jpg");
//            test.decrypt(secretKey,"C:\\Users\\80085\\Desktop\\folder\\carrot-cake.jpg","C:\\Users\\80085\\Desktop\\folder\\carrot-cake1.jpg");
////            System.out.println(text);
//        } catch (NoSuchAlgorithmException e) {
//            e.printStackTrace();
//        } catch (InvalidAlgorithmParameterException e) {
//            e.printStackTrace();
//        } catch (NoSuchPaddingException e) {
//            e.printStackTrace();
//        } catch (InvalidKeyException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
}


