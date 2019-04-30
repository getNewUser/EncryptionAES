package com.company;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.*;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

public class MainWindow {
    public static String ZIPFILE = null;
    public static String FILE = null;

    private JPanel mainPanel;
    private JTextField directoryTextField;
    private JButton encryptButton;
    private JTextArea resultsTextArea;
    private JButton decryptButton;
    private JProgressBar progressBar1;
    public static String key = "AES__256__RAKTAS";
    private Archive archiveThread;

    public MainWindow(){

        File md5File = new File("C:\\Users\\80085\\IdeaProjects\\ThreadsEncryption\\src\\com\\company\\MD5.txt");
        File md5Crypted = new File("C:\\Users\\80085\\IdeaProjects\\ThreadsEncryption\\src\\com\\company\\MD5Encrypted.txt-encrypted");

            encrypt(Cipher.DECRYPT_MODE, key, md5Crypted, md5File);

        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
            @Override
            public void run() {
                File md5File = new File("C:\\Users\\80085\\IdeaProjects\\ThreadsEncryption\\src\\com\\company\\MD5.txt");
                File md5Crypted = new File("C:\\Users\\80085\\IdeaProjects\\ThreadsEncryption\\src\\com\\company\\MD5Encrypted.txt-encrypted");

                encrypt(Cipher.ENCRYPT_MODE, key, md5File, md5Crypted);
            }
        }));

        encryptButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
//                progressBar1.setStringPainted(true);
//                progressBar1.setValue(0);

                FILE = directoryTextField.getText();
                ZIPFILE = directoryTextField.getText() + ".zip";

                archiveThread = new Archive(FILE,ZIPFILE,key,1);
                archiveThread.start();
            }
        });
        decryptButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                FILE = directoryTextField.getText();
                ZIPFILE = directoryTextField.getText() + ".zip";

                archiveThread = new Archive(FILE, ZIPFILE, key,2);
                archiveThread.start();
            }
        });
    }


    public static void main(String[] args) {

        JFrame mainFrame = new JFrame("Main Window");
        mainFrame.setContentPane(new MainWindow().mainPanel);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setSize(800, 600);
        mainFrame.setVisible(true);
    }
    static void encrypt(int cipherMode, String key, File file1, File file2){
        try{
            Key secretKey = new SecretKeySpec(key.getBytes(), "AES");
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(cipherMode,secretKey);


            FileInputStream FIS = new FileInputStream(file1);
            byte[] bytes = new byte[(int) file1.length()];
            FIS.read(bytes);

            byte[] outputBytes = cipher.doFinal(bytes);
            FileOutputStream FOS = new FileOutputStream(file2);
            FOS.write(outputBytes);

            FIS.close();
            FOS.close();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        }
    }




}


