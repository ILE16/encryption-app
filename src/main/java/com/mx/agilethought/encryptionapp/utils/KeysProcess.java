package com.mx.agilethought.encryptionapp.utils;

import org.bouncycastle.util.encoders.Base64;

import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;

public class KeysProcess {
    public static PublicKey createPublicKey(String base64Key)
    {
        try{
            byte[] byteKey = Base64.decode(base64Key);
            X509EncodedKeySpec encodedKeySpec = new X509EncodedKeySpec(byteKey);
            KeyFactory kf = KeyFactory.getInstance("ECDH" , "BC");
            return kf.generatePublic(encodedKeySpec);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }

    }
}
