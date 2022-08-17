package com.mx.agilethought.encryptionapp.utils;

import org.bouncycastle.jce.ECNamedCurveTable;
import org.bouncycastle.jce.spec.ECNamedCurveParameterSpec;
import org.bouncycastle.util.io.pem.PemWriter;

import java.security.*;


public class KeyParGen {
    public static KeyPair generateECKeys(){

        try {
            ECNamedCurveParameterSpec ecNamedCurveParameterSpec = ECNamedCurveTable.getParameterSpec("P-384");
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("ECDH" , "BC");
            keyPairGenerator.initialize(ecNamedCurveParameterSpec);
            KeyPair keyPair = keyPairGenerator.generateKeyPair();
            return keyPair;
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        } catch (NoSuchProviderException e) {
            throw new RuntimeException(e);
        } catch (InvalidAlgorithmParameterException e) {
            throw new RuntimeException(e);
        }


    }


}
