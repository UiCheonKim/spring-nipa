package dz.nft.nipa.utils;

import java.util.Base64;
import java.util.Base64.Decoder;

public class Base64DecodingUtil {

	public String base64Decoding(String encodedStr) {
		Decoder decoder = Base64.getDecoder();
		byte[] decoded = decoder.decode(encodedStr);
		return new String(decoded);
	}
	
}
