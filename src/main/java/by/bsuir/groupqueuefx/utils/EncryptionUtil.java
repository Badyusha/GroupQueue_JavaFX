package by.bsuir.groupqueuefx.utils;


import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class EncryptionUtil {
	public static int[] convertByteArrayToIntArray(byte[] byteArray) {
		int[] intArray = new int[byteArray.length];
		for (int i = 0; i < byteArray.length; i++) {
			intArray[i] = byteArray[i] & 0xFF;
		}
		return intArray;
	}

	// cipher method
	public static String hashData(String message) {
		MessageDigest messageDigest = null;
		try {
			messageDigest = MessageDigest.getInstance("SHA-256");
		} catch (NoSuchAlgorithmException e) {
			System.err.println("Exception in Hash.hashData\nCannot getInstance of SHA-256");
			e.printStackTrace();
		}
		byte[] resultByteArray = messageDigest.digest(message.getBytes());
		return (new BigInteger(1, resultByteArray)).toString(16);
	}
}
