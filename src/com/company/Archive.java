package com.company;


import javax.crypto.Cipher;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import static com.company.MainWindow.ZIPFILE;

public class Archive extends Thread {

    private ArrayList<String> filesList;
    private String file;
    private String zipFile;
    private String key;
    private int action;


    public Archive( String file, String zipFile, String key, int action) {
        this.file = file;
        this.zipFile = zipFile;
        this.key = key;
        this.action = action;

        if(action == 1){
            filesList = new ArrayList<>();
            this.generateFiles(new File(file));
        }
    }

    public void archive(String zipFile){
        byte[] buffer = new byte[1024];
        try(FileOutputStream FOS = new FileOutputStream(zipFile); ZipOutputStream ZOS = new ZipOutputStream(FOS)){

            // creating archive
            for(String file : filesList){


                ZipEntry ZE = new ZipEntry(file);
                FileInputStream FIS = new FileInputStream(this.file + File.separator + file);
                ZOS.putNextEntry(ZE);
                // adding files to archive

                int length;
                while((length = FIS.read(buffer)) > 0){
                    ZOS.write(buffer, 0, length);
                }
                FIS.close();
            }

            File zip = new File(ZIPFILE);
            File encryptedFile = new File(this.file +"-encrypted");
            try{
                MainWindow.encrypt(Cipher.ENCRYPT_MODE,key,zip,encryptedFile);
                MD5 hash = new MD5(encryptedFile.toString());

                try(BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(new File("C:\\Users\\80085\\IdeaProjects\\ThreadsEncryption\\src\\com\\company\\MD5.txt")))){
                    bufferedWriter.write(hash.getValue());
                }
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
//            metodasl.Baigti();
            ZOS.closeEntry();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void unpack(String file, String zipFile) throws IOException, NoSuchAlgorithmException {
        String md5File = null;
        try{
            md5File = new String(Files.readAllBytes(Paths.get("C:\\Users\\80085\\IdeaProjects\\ThreadsEncryption\\src\\com\\company\\MD5.txt")));
        } catch (IOException e) {
            e.printStackTrace();
        }

        File encryptedFile = new File(file + "-encrypted");
        File decryptedFile = new File(file + "-decrypted" + ".zip");

        MD5 hash = new MD5(encryptedFile.toString());
        String md5Value = hash.getValue();
        System.out.println("MD5Reiksmes: " +md5File + "\n" + md5Value);

        if(md5File.equals(md5Value)){
            try{
                MainWindow.encrypt(Cipher.DECRYPT_MODE,key, encryptedFile, decryptedFile);
                String zipFileName = file + ".zip";
                String decryptedFileName = file +"-decrypted";
                Files.delete(Paths.get(file + "-decrypted" + ".zip"));

                byte[] buffer = new byte[1024];

                try{
                    ZipInputStream zipInputStream = new ZipInputStream(new FileInputStream(zipFileName));
                    ZipEntry zipEntry = zipInputStream.getNextEntry();
                    System.out.println("1");

                    while(zipEntry != null){
                        System.out.println("2");


                        System.out.println("4");
                        File unpackedFile = new File(decryptedFileName + File.separator + zipEntry.getName());

                        new File(unpackedFile.getParent()).mkdirs();
                        FileOutputStream fileOutputStream = new FileOutputStream(unpackedFile);

                        int length;
                        while((length = zipInputStream.read(buffer)) > 0){
                            fileOutputStream.write(buffer, 0, length);
                        }
                        System.out.println("3");

                        fileOutputStream.close();
                        zipEntry = zipInputStream.getNextEntry();
                    }
//                    metodasl.Baigti();


                    zipInputStream.closeEntry();
                    zipInputStream.close();
                }catch(Exception ex){
                    System.out.println(ex.getMessage());
                }
            }catch (Exception ex){
                System.out.println(ex.getMessage());
            }
        }
    }

    public void generateFiles(File file){
        if(file.isFile()){
            filesList.add(toZip(file.getAbsoluteFile().toString()));
        }

        if(file.isDirectory()){
            String[] directory = file.list();
            for(String fileInDirectory : directory){
                generateFiles(new File(file, fileInDirectory));
            }
        }
    }

    private String toZip(String file){
        return file.substring(this.file.length() + 1, file.length());
    }

    @Override
    public void run() {
        if(action == 1){
                archive(zipFile);
        }else if(action == 2){
            try{
                unpack(file,zipFile);
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
