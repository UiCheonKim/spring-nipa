package dz.nft.nipa.dto;

import lombok.Data;

@Data
public class EthTransactionDto {
	private String hash;
	private String gasUsed;
	private String insertedDt;
}
