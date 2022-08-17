package com.mx.agilethought.encryptionapp.utils;

import com.fasterxml.jackson.databind.ser.Serializers;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.security.SecureRandom;
import java.util.Base64;

public class EncryptionUtils {
    public static byte[] iv = new SecureRandom().generateSeed(16);





    public String encrypt(SecretKey key , String textToEncrypt){
        try{
            Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding" , "BC");
            byte[] textBytes = textToEncrypt.getBytes(StandardCharsets.UTF_8);
            byte[] cipherText;
            cipher.init(Cipher.ENCRYPT_MODE, key, new IvParameterSpec(iv));
            cipherText = new byte[cipher.getOutputSize(textBytes.length)];
            int encryptLength = cipher.update(textBytes, 0 , textBytes.length,cipherText, 0);
            encryptLength += cipher.doFinal(cipherText, encryptLength);
            return Base64.getEncoder().encodeToString(cipherText);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public String decrypt(SecretKey key , String encryptedText){
        try{
            Key decryptionKey = new SecretKeySpec(key.getEncoded(), key.getAlgorithm()) ;
            Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding", "BC");
            byte[] encryptedTextBytes = Base64.getDecoder().decode(encryptedText);
            byte[] decryptedTextBytes ;
            cipher.init(Cipher.DECRYPT_MODE , decryptionKey , new IvParameterSpec(iv));
            decryptedTextBytes = new byte[cipher.getOutputSize(encryptedTextBytes.length)];
            int decryptLength = cipher.update(encryptedTextBytes, 0 , encryptedTextBytes.length, decryptedTextBytes, 0);
            decryptLength+= cipher.doFinal(decryptedTextBytes ,decryptLength);

            return new String(decryptedTextBytes, StandardCharsets.UTF_8);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public String bytesToHex(byte[] data){
        return bytesToHex(data, data.length);
    }

    public String bytesToHex(byte[] data , int length){
        String digits = "0123456789ABCDEF";
        StringBuffer sb = new StringBuffer();

        for(int i = 0; i!=length; i++){
            int v = data[i] &0xff;
            sb.append(digits.charAt(v>>4));
            sb.append(digits.charAt(v & 0xf));
        }

        return sb.toString();
    }
    public byte[] hexToBytes(String text){
        int length =  text.length();
        byte[] data = new byte[length/2];
        for (int i = 0; i<length; i+=2){
            data[i/2] = (byte) ((Character.digit(text.charAt(i) , 16)<<4) + Character.digit(text.charAt(i+1) ,16));
        }

        return data;
    }
}
