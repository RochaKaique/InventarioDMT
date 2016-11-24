package com.tivit.inventariodmt.dataconsistency.utils;

import java.security.Key;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import Decoder.BASE64Decoder;
import Decoder.BASE64Encoder;

/**
 * Created by kaique.rocha on 21/11/2016.
 */

public class Criptografia {

    private static final String KEY = "@(4gdOM*oAn$%tRg";
    private static final String ALGORITHM = "AES";

    private static Key generateKey() throws Exception {

        Key key = new SecretKeySpec(Criptografia.KEY.getBytes(), Criptografia.ALGORITHM);
        return key;
    }

    public static String encrypt(String value) throws Exception {
        Key key = generateKey();
        Cipher cipher = Cipher.getInstance(Criptografia.ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, key);
        byte[] encryptedByteValue = cipher.doFinal(value.getBytes("utf-8"));
        String encryptedValue64 = new BASE64Encoder().encode(encryptedByteValue);
        return encryptedValue64.replace("/","$").replace("\\",")" );
    }



}
