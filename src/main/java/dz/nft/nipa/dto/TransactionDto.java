package dz.nft.nipa.dto;

import lombok.Data;

@Data
public class TransactionDto {
	
	private int id;
	private int blockid;
	private String txhash;
	private String createdt;
	private String chaincodename;
	private String writeSet;
	private String fcn;
	private String nftId;
	private String title;
	private String timestamp;
	private int nftNum;
	private int detailNum;
	private String nftStatus;
}
