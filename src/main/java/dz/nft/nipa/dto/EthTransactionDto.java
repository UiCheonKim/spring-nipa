package dz.nft.nipa.dto;

import lombok.Data;
import org.apache.commons.codec.binary.Hex;

@Data
public class EthTransactionDto {
	private String hash;
	private String blockNumber;
	private String insertedDt;

	public void setHash(byte[] hash) {
		this.hash = "0x" + Hex.encodeHexString(hash);
	}
}
