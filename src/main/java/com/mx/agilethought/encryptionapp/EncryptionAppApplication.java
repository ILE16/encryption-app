package com.mx.agilethought.encryptionapp;

import com.google.common.base.Splitter;
import com.mx.agilethought.encryptionapp.utils.EncryptionUtils;
import com.mx.agilethought.encryptionapp.utils.KeyParGen;
import com.mx.agilethought.encryptionapp.utils.KeysProcess;
import com.mx.agilethought.encryptionapp.utils.SecretGen;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.util.encoders.Base64;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.crypto.SecretKey;
import java.security.KeyPair;
import java.security.PublicKey;
import java.security.Security;

@SpringBootApplication
public class EncryptionAppApplication {

	public static String  getPublicKeyFormat(byte[] publicKeyBytesFormat){

		String publicKeyContent = java.util.Base64.getEncoder().encodeToString(publicKeyBytesFormat);
		String publicKeyFormatted = "";
		for (final String row:
				Splitter
						.fixedLength(64)
						.split(publicKeyContent)
		)
		{
			publicKeyFormatted += row + System.lineSeparator();
		}
		return publicKeyFormatted;
	}
	//llave publica proveniente de swift IO
	private static final String publicKeyFromIOS = "MHYwEAYHKoZIzj0CAQYFK4EEACIDYgAEI7mRxPYJMN1m8OvZyf1xXIp6P2MKfqLT\n" +
			"AmUsHhaJ5T1mbXbtc4WK2OvYwpqfdimoZ1aZ64k2ji666vX/tiWC7WnGXUzoKx/p\n" +
			"jPabh9+hEAXvTlmHsOm/qv9F33h2CNS8";
	public static void main(String[] args) {
		SpringApplication.run(EncryptionAppApplication.class, args);
		Security.addProvider(new BouncyCastleProvider());

		KeyPair keyPairA = KeyParGen.generateECKeys();
		//se genera llave publica que se va compartir con front mobile

		//String publicKeyInPEMformat = publicKeyFormat(keyPairA.getPublic().getEncoded());
		//llave publica compartida por ios
		String publicKeyInPEMformat = getPublicKeyFormat(keyPairA.getPublic().getEncoded());
		System.out.println("PEM format :" + publicKeyInPEMformat);



		//se implementa llave publica generada en el lenguaje swift
		PublicKey publicKey = KeysProcess.createPublicKey(publicKeyFromIOS);
		String textToEncrypt = "Hola amigos y familia";

		SecretKey secretKey = SecretGen.generateSharedSecret(keyPairA.getPrivate(),publicKey);

		System.out.println("text before to encrypt: " + textToEncrypt );
		EncryptionUtils encryptionUtils = new EncryptionUtils();
		String textEncrypted = encryptionUtils.encrypt(secretKey, textToEncrypt);
		System.out.println("encrypted text : " + textEncrypted);
		String textDecrypted = encryptionUtils.decrypt(secretKey ,
				"eiMJGrKyGN1O5A==");
		System.out.println("decrypted text : " + textDecrypted);

	}

}
