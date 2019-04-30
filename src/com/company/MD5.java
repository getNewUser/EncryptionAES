package com.company;

import javax.xml.bind.DatatypeConverter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5 {
    private String name;

    public MD5(String name) {
        this.name = name;
    }

    public String getValue() throws NoSuchAlgorithmException, IOException {
            MessageDigest MD5 = MessageDigest.getInstance("MD5");
            MD5.update(Files.readAllBytes(Paths.get(name)));
            byte[] digest = MD5.digest();
            String hash = DatatypeConverter.printHexBinary(digest).toUpperCase();
            return hash;


    }
}
