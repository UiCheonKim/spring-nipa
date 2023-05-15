package dz.nft.nipa.dto;

import lombok.Data;
import org.apache.commons.codec.binary.Hex;

@Data 
public class EthBlockDto {
	private String hash;
	private String minerHash;
	private String parentHash;
	private String timestamp;
	private String blockNumber;

	public void setHash(byte[] hash) {
		this.hash = Hex.encodeHexString(hash);
	}
	public void setMinerHash(byte[] minerHash) {
		this.minerHash = Hex.encodeHexString(minerHash);
	}
	public void setParentHash(byte[] parentHash) {
		this.parentHash = Hex.encodeHexString(parentHash);
	}

}
