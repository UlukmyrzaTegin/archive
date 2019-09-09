package rts.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.lang.StringUtils;

public class Crypto {
   public static String getEncodedStringAlg1(String str1, String str2) {
      StringBuffer result = new StringBuffer();
      result.append(StringUtils.reverse(str1 + "*"));
      result.append(StringUtils.reverse(str2));
      String str = getMd5(result.toString());
      return str.replaceAll("0", "");
   }
   
   public static String getEncodedStringAlg2(String str1, String str2) {
  	 StringBuffer result = new StringBuffer();
  	 result.append(StringUtils.reverse(str1 + "$"));
  	 result.append(StringUtils.reverse(str2));
  	 return getMd5(result.toString());
   }
   
   public static String getMd5(String string) {
      StringBuffer hexString = null;
      try {
         MessageDigest algorithm = MessageDigest.getInstance("md5");
         algorithm.reset();
         algorithm.update(string.getBytes());
         byte messageDigest[] = algorithm.digest();

         hexString = new StringBuffer();
         for(int i = 0; i < messageDigest.length; i++) {
            hexString.append(Integer.toHexString(0xFF & messageDigest[i]));
         }
      } catch(NoSuchAlgorithmException e) {}
      return hexString.toString();
   }
   
}
