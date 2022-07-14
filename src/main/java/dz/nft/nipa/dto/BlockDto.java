package dz.nft.nipa.dto;

import lombok.Data;

@Data 
public class BlockDto {
	private int id;
	private int blocknum;
	private String createdt;
	private String blockhash;
	private String datahash;
	private String prehash;
	private String blksize;
	private String txcount;
}
