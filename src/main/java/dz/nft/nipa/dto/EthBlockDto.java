package dz.nft.nipa.dto;

import lombok.Data;
import org.apache.commons.codec.binary.Hex;
@Data 
public class EthBlockDto {
	private String hash;
	private String minerHash;
	private String parentHash;
	private String timestamp;
	private int txCount;
	private String insertedAt;
	private String number;
	private int blockNumber;
	private String size;

	public void setHash(byte[] hash) {
		this.hash = "0x" + Hex.encodeHexString(hash);
	}
	public void setMinerHash(byte[] minerHash) {
		this.minerHash = "0x" + Hex.encodeHexString(minerHash);
	}
	public void setParentHash(byte[] parentHash) {
		this.parentHash = "0x" + Hex.encodeHexString(parentHash);
	}

}
