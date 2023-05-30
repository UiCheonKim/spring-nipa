package dz.nft.nipa.transaction.mapper;

import java.util.ArrayList;

import dz.nft.nipa.dto.DataNftDto;
import dz.nft.nipa.dto.EthTransactionDto;
import dz.nft.nipa.dto.InputDataDto;
import org.apache.ibatis.annotations.Mapper;

import dz.nft.nipa.dto.TransactionDto;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface TransactionMapper {
	void save(@Param("nftNum") String nftNum, @Param("ipfsNum") String ipfsNum);
	public int getTransCnt(); // 트랜잭션 개수 가져옴
	public int getTransHashcnt(String transHash);
	public InputDataDto getTransHashInput(String transHash);
	public DataNftDto getNFTData(String nftId);
	public ArrayList<String> getNFTID();
	public Integer getFirstTransNum();
	public Integer getEthFirstTransNum();
	public String getNftIdByNftNum(String nftNum);
	public ArrayList<TransactionDto> getRecentTrList();
	public ArrayList<EthTransactionDto> getEthRecentTrList();
	public ArrayList<TransactionDto> getTrDataByBlocknum(int blNum);
	public EthTransactionDto getTrDataById(String trId);
	public int getTotalReadCnt();
	public int getEthTotalReadCnt();

	public ArrayList<TransactionDto> getTotalTrList(int pageNum);
	
	public int getTestResult4SearchHash(String searchWord);
	public String getTrNumByHash(String searchWord);
	public int confirmReadRecord(String nftId);
	

}
