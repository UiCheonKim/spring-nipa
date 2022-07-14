package dz.nft.nipa.transaction.mapper;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Mapper;

import dz.nft.nipa.dto.TransactionDto;

@Mapper
public interface TransactionMapper {
	
	public int getTransCnt(); // 트랜잭션 개수 가져옴
	public Integer getFirstTransNum();
	
	public String getNftIdByNftNum(String nftNum);
	public ArrayList<TransactionDto> getRecentTrList();
	public ArrayList<TransactionDto> getTrDataByBlocknum(int blocknum);
	public TransactionDto getTrDataById(int trId);
	public int getTotalReadCnt();
	public ArrayList<TransactionDto> getTotalTrList(int pageNum);
	
	public int getTestResult4SearchHash(String searchWord);
	public int getTrNumByHash(String searchWord);
	public int confirmReadRecord(String nftId);
	

}
