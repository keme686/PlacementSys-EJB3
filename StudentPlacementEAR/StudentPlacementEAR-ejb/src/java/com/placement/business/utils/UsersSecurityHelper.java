/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.placement.business.utils;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

/**
 *
 * @author kemele
 */
public class UsersSecurityHelper {
    public static String md5(String password) {
        String md5 = null;
        if (password == null) {
            return null;
        }
        try {
            //Create MessageDigest object for MD5
            MessageDigest digest = MessageDigest.getInstance("MD5");
            //Update input string in message digest
            digest.update(password.getBytes(), 0, password.length());
            //Converts message digest value in base 16 (hex)
            md5 = new BigInteger(1, digest.digest()).toString(16);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return md5;
    }
    static final String seed = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    static final int N = seed.length();    
    static Random gene = new Random();
    public static String getSecretCode() {
        char co[] = {'0', '0', '0', '0', '0', '0'};
        try {
            co[0] = seed.charAt(gene.nextInt(N));
            co[1] = seed.charAt(gene.nextInt(N));
            co[2] = seed.charAt(gene.nextInt(N));
            co[3] = seed.charAt(gene.nextInt(N));
            co[4] = seed.charAt(gene.nextInt(N));
            co[5] = seed.charAt(gene.nextInt(N));    
        } catch (Exception e) {
            e.printStackTrace();
        }
        String str = new String(co);
        return str;
    }
}
