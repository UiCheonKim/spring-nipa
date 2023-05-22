package dz.nft.nipa.dto;

import lombok.Data;
import org.apache.commons.codec.binary.Hex;

@Data
public class EthTransactionDto {
	private String hash;
	private int blockNumber;
	private String insertedAt;
	private String fcn;
	private String writeSet;

	public void setHash(byte[] hash) {
		this.hash = "0x" + Hex.encodeHexString(hash);
	}
}
