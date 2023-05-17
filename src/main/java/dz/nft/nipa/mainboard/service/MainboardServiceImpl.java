package dz.nft.nipa.mainboard.service;

import java.util.ArrayList;
import java.util.HashMap;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dz.nft.nipa.dto.DataDetailDto;
import dz.nft.nipa.dto.DataNftDto;
import dz.nft.nipa.dto.DataTypeDto;
import dz.nft.nipa.mainboard.mapper.MainboardMapper;
import dz.nft.nipa.utils.SendBurnNftId2BlockchainApi;

@Service
@Slf4j
public class MainboardServiceImpl {

	@Autowired
	private MainboardMapper mainMapper;

	public ArrayList<DataTypeDto> getAllDataType() {
		return mainMapper.getAllDataType();
	}
	
	public ArrayList<DataDetailDto> getAllDataDetail(int typeNum){
		return mainMapper.getAllDataDetail(typeNum);
	}
	
	public ArrayList<String> getNftDateList(int typeNum) {
		return mainMapper.getNftDateList(typeNum);
	}
	
	public ArrayList<HashMap<String, Object>> getAllNftListByDataType(int typeNum) {
		
		ArrayList<HashMap<String, Object>> resultList = new ArrayList<HashMap<String,Object>>();
		ArrayList<DataDetailDto> detailSize = mainMapper.getAllDataDetail(typeNum);
		
		for (DataDetailDto dto : detailSize) {
			HashMap<String, Object> resultMap = new HashMap<String, Object>();
			ArrayList<HashMap<String, Object>> detailList =  mainMapper.getAllNftListByDataType(typeNum, dto.getDetailNum());
			for (HashMap<String, Object> stringObjectHashMap : detailList) {
				String nftId = ((String) stringObjectHashMap.get("nft_id")).toUpperCase();
				String nftIdHex = utf8ToHex(nftId);
				String ethCnt = mainMapper.getEthCnt(nftIdHex);
				stringObjectHashMap.put("nft_cnt", ethCnt);
			}
			resultMap.put("detailDto", dto);
			resultMap.put("sortedList", detailList);
			resultList.add(resultMap);
		}
		
		return resultList;
	}
	
	public ArrayList<HashMap<String, Object>> getNftListByDateType(int typeNum, String dateType) {
		
		ArrayList<HashMap<String, Object>> resultList = new ArrayList<HashMap<String,Object>>();
		ArrayList<DataDetailDto> detailSize = mainMapper.getAllDataDetail(typeNum);
		
		for (DataDetailDto dto : detailSize) {
			HashMap<String, Object> resultMap = new HashMap<String, Object>();
			ArrayList<HashMap<String, Object>> detailList =  mainMapper.getNftListByDateType(typeNum, dto.getDetailNum(), dateType);
			resultMap.put("detailDto", dto);
			resultMap.put("sortedList", detailList);
			resultList.add(resultMap);
		}
		
		return resultList;
		
	}
	
	public String getImgLocation(int imgNum) {
		return mainMapper.getImgLocation(imgNum);
	}

	public String getEthNftNumToId(int nftNum) {
		return mainMapper.getEthNftNumToId(nftNum).toUpperCase();
	}
	
	public HashMap<String, Object> getNftDataBynftNum(int nftNum) {
		return mainMapper.getNftDataBynftNum(nftNum);
	}
	public HashMap<String, Object> getEthNftDataBynftNum(int nftNum, String nftIdHex) {
		return mainMapper.getEthNftDataBynftNum(nftNum, nftIdHex);
	}

	public void updateReadCnt(int nftNum) {
		mainMapper.updateReadCnt(nftNum);
	}
	
	public int getNftDataCnt() {
		return mainMapper.getNftDataCnt();
	}

	public int getTestResult4SearchWord(String searchWord) {
		return mainMapper.getTestResult4SearchWord(searchWord);
	}
	
	public DataNftDto getNftDataByNftId(String searchWord) {
		return mainMapper.getNftDataByNftId(searchWord);
	}

	public ArrayList<DataDetailDto> getAllDataDetailList() {
		return mainMapper.getAllDataDetailList();
	}
	
	public int getNftCntBytypeNum(int typeNum) {
		return mainMapper.getNftCntBytypeNum(typeNum);
	}
	
	public ArrayList<HashMap<String, Object>> getAdminNftListByDataType(int typeNum, int pageNum, int listNum) {
		return mainMapper.getAdminNftListByDataType(typeNum, pageNum, listNum);
	}

	public void updateHideYn(int nftNum) {
		mainMapper.updateHideYn(nftNum);
	}

	public void updatedelYn(int nftNum, String nftId) {
//		new SendBurnNftId2BlockchainApi().sendDeleteData(nftId);
		mainMapper.updatedelYn(nftNum);
	}

	public ArrayList<HashMap<String, Object>> searchByConditions(int typeNum, String searchType, String searchWord, int pageNum, int listNum) {
		return mainMapper.searchByConditions(typeNum, searchType, searchWord, pageNum, listNum);
	}
	
	public int searchByConditionsCnt(int typeNum, String searchType, String searchWord) {
		return mainMapper.searchByConditionsCnt(typeNum, searchType, searchWord);
	}


	public String getNftId4Admin(int nftNum) {
		return mainMapper.getNftId4Admin(nftNum);
	}
	
	public int getAdminNftListCnt(int typeNum) {
		return mainMapper.getAdminNftListCnt(typeNum);
	}

	private String utf8ToHex(String utf8) {
		StringBuilder sb = new StringBuilder();

		for (int i = 0; i < utf8.length(); i++) {
			char c = utf8.charAt(i);
			String hexString = Integer.toHexString(c);
			sb.append(hexString);
		}
		return sb.toString();
	}

}
