package dz.nft.nipa.utils;

import java.security.MessageDigest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HashStringUtil {
	
	private static final Logger log = LoggerFactory.getLogger(HashStringUtil.class);
	private static final String secretKey = "secretPasswordKeyForDigatalzone";
	
	public String getHashString(String randomString) {
		
		String needHash = secretKey+randomString;
		String hashcode = null;
		StringBuilder sb = new StringBuilder();

		try {
			MessageDigest messageDigest = MessageDigest.getInstance("SHA-1");

			messageDigest.reset();
			messageDigest.update(needHash.getBytes("UTF-8"));

			byte[] chars = messageDigest.digest();

			for (int i = 0; i < chars.length; i++) {
				String tmp = Integer.toHexString(0xff & chars[i]);
				if (tmp.length() == 1)
					sb.append("0");
				sb.append(tmp);
			}
			hashcode = sb.toString();

		} catch (Exception e) {
			log.info("암호화 오류");
			hashcode = null;
		}

		return hashcode;
	}

}