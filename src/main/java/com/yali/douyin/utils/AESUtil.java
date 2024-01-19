package com.yali.vilivili.utils;

import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class AESUtil {
 
	private static final String KEY_AES = "AES";

	/**
	 * 根据密钥对指定的明文plainText进行加密.
	 *
	 * @param plainText 明文
	 * @return 加密后的密文.
	 */
	public static String encrypt(String plainText) {
		Key secretKey = getKey("fendo888");
		System.out.println(byteToHexString(secretKey.getEncoded()));
		System.out.println(secretKey.getFormat());
		System.out.println(secretKey);
		try {
			Cipher cipher = Cipher.getInstance("AES");
			cipher.init(Cipher.ENCRYPT_MODE, secretKey);
			byte[] p = plainText.getBytes(StandardCharsets.UTF_8);
			byte[] result = cipher.doFinal(p);
			String encoded = Base64.encodeBase64String(result);;
			return encoded;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static Key getKey(String keySeed) {
		if (keySeed == null) {
			keySeed = System.getenv("AES_SYS_KEY");
		}
		if (keySeed == null) {
			keySeed = System.getProperty("AES_SYS_KEY");
		}
		if (keySeed == null || keySeed.trim().length() == 0) {
			keySeed = "abcd1234!@#$";// 默认种子
		}
		try {
			SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
			secureRandom.setSeed(keySeed.getBytes());
			KeyGenerator generator = KeyGenerator.getInstance("AES");
			generator.init(secureRandom);
			return generator.generateKey();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 根据密钥对指定的密文cipherText进行解密.
	 *
	 * @param cipherText 密文
	 * @return 解密后的明文.
	 */
	public static String decrypt(String cipherText) {
		Key secretKey = getKey("fendo888");
		try {
			Cipher cipher = Cipher.getInstance("AES");
			cipher.init(Cipher.DECRYPT_MODE, secretKey);

			byte[] c = Base64.decodeBase64(cipherText);
			byte[] result = cipher.doFinal(c);
			return new String(result, StandardCharsets.UTF_8);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	public static byte[] hex2byte(String strhex) {
		if (strhex == null) {
			return null;
		}
		int l = strhex.length();
		if (l % 2 == 1) {
			return null;
		}
		byte[] b = new byte[l / 2];
		for (int i = 0; i != l / 2; i++) {
			b[i] = (byte) Integer.parseInt(strhex.substring(i * 2, i * 2 + 2),
					16);
		}
		return b;
	}
 
	public static String byte2hex(byte[] b) {
		String hs = "";
		String stmp = "";
		for (int n = 0; n < b.length; n++) {
			stmp = (Integer.toHexString(b[n] & 0XFF));
			if (stmp.length() == 1) {
				hs = hs + "0" + stmp;
			} else {
				hs = hs + stmp;
			}
		}
		return hs.toUpperCase();
	}

	/**
	 * byte数组转化为16进制字符串
	 * @param bytes
	 * @return
	 */
	public static String byteToHexString(byte[] bytes) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < bytes.length; i++) {
			String strHex=Integer.toHexString(bytes[i]);
			if(strHex.length() > 3) {
				sb.append(strHex.substring(6));
			} else {
				if(strHex.length() < 2) {
					sb.append("0" + strHex);
				} else {
					sb.append(strHex);
				}
			}
		}
		return sb.toString();
	}
}