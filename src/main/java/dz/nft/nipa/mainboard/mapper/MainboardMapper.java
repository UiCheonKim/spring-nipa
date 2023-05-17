package dz.nft.nipa.mainboard.mapper;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import dz.nft.nipa.dto.DataDetailDto;
import dz.nft.nipa.dto.DataNftDto;
import dz.nft.nipa.dto.DataTypeDto;

@Mapper
public interface MainboardMapper {
	
	public ArrayList<DataTypeDto> getAllDataType();
	public ArrayList<DataDetailDto> getAllDataDetail(int typeNum);
	public ArrayList<String> getNftDateList(int detailNum);
	
	public String getImgLocation(int imgNum);
	public ArrayList<HashMap<String, Object>> getAllNftListByDataType(@Param("typeNum") int typeNum, @Param("detailNum") int detailNum);
	public String getEthCnt(String nftIdHex);

	public ArrayList<HashMap<String, Object>> getNftListByDateType(@Param("typeNum") int typeNum, @Param("detailNum") int detailNum, @Param("dateType") String dateType);
	public String getEthNftNumToId(int nftNum);
	public HashMap<String, Object> getNftDataBynftNum(int nftNum);
	public HashMap<String, Object> getEthNftDataBynftNum(@Param("nftNum") int nftNum, @Param("nftIdHex") String nftIdHex);
	public void updateReadCnt(int nftNum);
	public int getNftDataCnt();
	
	public int getTestResult4SearchWord(String searchWord);
	public DataNftDto getNftDataByNftId(String searchWord);
	
	public ArrayList<DataDetailDto> getAllDataDetailList(); //필요 없으면 수정하기
	public int getNftCntBytypeNum(int typeNum);
	public ArrayList<HashMap<String, Object>> getAdminNftListByDataType(
			@Param("typeNum") int typeNum,
			@Param("pageNum") int pageNum,
			@Param("listNum") int listNum);
	public void updateHideYn(int nftNum);
	public void updatedelYn(int nftNum);

	public ArrayList<HashMap<String, Object>> searchByConditions(
			@Param("typeNum") int typeNum,
			@Param("searchType") String searchType,
			@Param("searchWord") String searchWord,
			@Param("pageNum") int pageNum,
			@Param("listNum") int listNum);
	
	public int searchByConditionsCnt(
			@Param("typeNum") int typeNum,
			@Param("searchType") String searchType,
			@Param("searchWord") String searchWord);
	
	public String getNftId4Admin(int nftNum);
	public int getAdminNftListCnt(int typeNum);
	
	
}
