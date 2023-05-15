package dz.nft.nipa.block.mapper;

import java.util.ArrayList;

import dz.nft.nipa.dto.EthBlockDto;
import org.apache.ibatis.annotations.Mapper;

import dz.nft.nipa.dto.BlockDto;

@Mapper
public interface BlockMapper {
	
	public int getBlockCnt();
	public Integer getFirstBlockNum();
	public Integer getEthFirstBlockNum();
	public ArrayList<BlockDto> getRecentBlList();
	public ArrayList<EthBlockDto> getEthRecentBlList();
	public BlockDto getBlDataById(int blNum);
	public BlockDto getBlDataByBlocknum(int blocknum);
	public ArrayList<BlockDto> getTotalBlList(int pageNum);
	
	public int getTestResult4SearchHash(String searchWord);
	public Integer getBlNumByHash(String searchWord);

}
