package com.mx.agilethought.encryptionapp.utils;

import javax.crypto.KeyAgreement;
import javax.crypto.SecretKey;
import java.security.PrivateKey;
import java.security.PublicKey;

public class SecretGen {
    public static SecretKey generateSharedSecret(PrivateKey privateKey, PublicKey publicKey){
        try{

            KeyAgreement keyAgreement = KeyAgreement.getInstance("ECDH" , "BC");

            keyAgreement.init(privateKey);

            keyAgreement.doPhase(publicKey, true);

            SecretKey key = keyAgreement.generateSecret("AES");

            return key;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
}
