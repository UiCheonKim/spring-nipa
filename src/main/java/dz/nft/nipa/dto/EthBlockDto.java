package dz.nft.nipa.dto;

import lombok.Data;

@Data 
public class EthBlockDto {
	private String hash;
	private String minerHash;
	private String parentHash;
	private String timestamp;
}
