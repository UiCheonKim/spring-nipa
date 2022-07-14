package dz.nft.nipa.block.mapper;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Mapper;

import dz.nft.nipa.dto.BlockDto;

@Mapper
public interface BlockMapper {
	
	public int getBlockCnt();
	public Integer getFirstBlockNum();
	
	public ArrayList<BlockDto> getRecentBlList();
	public BlockDto getBlDataById(int blNum);
	public BlockDto getBlDataByBlocknum(int blocknum);
	public ArrayList<BlockDto> getTotalBlList(int pageNum);
	
	public int getTestResult4SearchHash(String searchWord);
	public Integer getBlNumByHash(String searchWord);

}
