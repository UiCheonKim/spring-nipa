package dz.nft.nipa.transaction.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.jose4j.json.internal.json_simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import dz.nft.nipa.dto.TransactionDto;
import dz.nft.nipa.transaction.mapper.TransactionMapper;
import dz.nft.nipa.utils.Base64DecodingUtil;
import dz.nft.nipa.utils.SendNftId2BlockchainApi;

@Service
public class TransactionServiceImpl {
	
	private static final Logger logger = LoggerFactory.getLogger(TransactionServiceImpl.class);

	@Autowired
	private TransactionMapper tranMapper;
	
	public int getTransCnt() {
		return tranMapper.getTransCnt();
	} // 트랜잭션 개수 가져오는 메서드
	
	public int getFirstTransNum() {
		Integer dataNum = tranMapper.getFirstTransNum();
		int resultNum = 0;
		if (dataNum != null) {
			resultNum = (int)dataNum;
		}
		return resultNum;
	}
	
	public ArrayList<TransactionDto> getRecentTrList() {
		return tranMapper.getRecentTrList();
	}

	public ArrayList<TransactionDto> getTrDataByBlocknum(int blocknum) {
		return tranMapper.getTrDataByBlocknum(blocknum);
	}

	public TransactionDto getTrDataById(int trId) {
		
		TransactionDto dto = tranMapper.getTrDataById(trId);
		if (dto == null) {
			return null;
		}
		
		String ws = dto.getWriteSet();
		
		if (dto.getChaincodename().equals("CHECK_LIVENESS") || dto.getChaincodename().equals("INIT_TOKEN")) {
			dto.setWriteSet(null);
			
		} else if (dto.getFcn().equals("CREATE_NFT")) {
			
			Object[] test = ws.split("},");
			String str = test[2].toString();
			String subStr = str.substring(7, str.length()-12);
			ObjectMapper mapper = new ObjectMapper();
			HashMap<String, Object> map = null;
			HashMap<String, Object> valueMap = null;
			Map<String, Object> baseMap = new LinkedHashMap<String, Object>();
			Map<String, Object> midMap = new LinkedHashMap<String, Object>();
			HashMap<String, Object> resultMap = new HashMap<String, Object>();
			
			try {
				map = mapper.readValue(subStr, HashMap.class);
				midMap.put("key", map.get("key"));
				midMap.put("is_delete", map.get("is_delete"));
				valueMap = mapper.readValue(map.get("value").toString(), HashMap.class);
				baseMap.put("nft_id", valueMap.get("nft_id"));
				baseMap.put("owner", valueMap.get("owner"));
				baseMap.put("timestamp", valueMap.get("timestamp"));
				baseMap.put("tokenuri", valueMap.get("tokenuri"));
				baseMap.put("minter", valueMap.get("minter"));
				baseMap.put("fcn", "CREATE_NFT");
				baseMap.put("title", valueMap.get("title"));
				midMap.put("value", baseMap);
				
			} catch (JsonProcessingException e) {
				logger.info("CREATE_NFT로 변환 오류");
				return null;
			}
			resultMap.put("set", midMap);
			String custom = JSONObject.toJSONString(resultMap);
			dto.setWriteSet(CustomWriteSetDataText(custom));
			
		} else if (dto.getFcn().equals("READ_NFT")) {
			
			Object[] test = ws.split("},");
			String str = test[2].toString();
			String subStr = str.substring(7, str.length()-12);
			ObjectMapper mapper = new ObjectMapper();
			HashMap<String, Object> map = null;
			HashMap<String, Object> valueMap = null;
			Map<String, Object> baseMap = new LinkedHashMap<String, Object>();
			Map<String, Object> midMap = new LinkedHashMap<String, Object>();
			HashMap<String, Object> resultMap = new HashMap<String, Object>();
			
			try {
				map = mapper.readValue(subStr, HashMap.class);
				midMap.put("key", map.get("key"));
				midMap.put("is_delete", map.get("is_delete"));
				valueMap = mapper.readValue(map.get("value").toString(), HashMap.class);
				baseMap.put("nft_read_id", valueMap.get("nft_read_id"));
				baseMap.put("timestamp", valueMap.get("timestamp"));
				baseMap.put("fcn", "READ_NFT");
				baseMap.put("nft_id", valueMap.get("nft_id"));
				midMap.put("value", baseMap);
				
			} catch (JsonProcessingException e) {
				logger.info("READ_NFT 변환 오류");
				return null;
			}
			resultMap.put("set", midMap);
			String custom = JSONObject.toJSONString(resultMap);
			dto.setWriteSet(CustomWriteSetDataText(custom));
			
		} else {
			dto.setWriteSet(null);
		}
		
		return dto;
	}
	
	public int getTotalReadCnt() {
		return tranMapper.getTotalReadCnt();
	}

	public ArrayList<TransactionDto> getTotalTrList(int pageNum) {
		return tranMapper.getTotalTrList(pageNum);
	}

	public HashMap<String, Object> readNftContents(String nftId) {
		//String nftHash = new SendNftId2BlockchainApi().sendData(nftId).get("nftUri").toString();
		//String nftUri = new Base64DecodingUtil().base64Decoding(nftHash);
		/**
		 * 블록체인에서 콘텐츠 사이트 연결 URI를 정상적으로 리턴하지 못함.
		 * (응답값 자체를 BASE64 Decode 하지 못함. 오류 발생.
		 * https://www.nipa.kr 로 임시 설정
		 */

		String nftUri = "https://www.nipa.kr";
		//logger.info("Read_NFT 호출 성공");
		//logger.info("returnUri : "+nftUri);
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("data", nftUri);
		return map;
	}

	public int getTestResult4SearchHash(String searchWord) {
		return tranMapper.getTestResult4SearchHash(searchWord);
	}

	public int getTrNumByHash(String searchWord) {
		return tranMapper.getTrNumByHash(searchWord);
	}
	
	// 트랜잭션 json
	private String CustomWriteSetDataText(String oriText) {
		String[] arr = oriText.split("\\{|\\,|\\}");
		String forText = "";
		String endText = "";
		for (int i=2; i<5; i++) {
			forText += "<br/>&nbsp;&nbsp;&nbsp;&nbsp;"+arr[i];
		}
		for (int i=5; i<arr.length; i++) {
			endText += "<br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"+arr[i];
		}
		String resultText = "{<br/>&nbsp;&nbsp;"+arr[1]+
				"<br/>&nbsp;&nbsp;{"+forText+
				"<br/>&nbsp;&nbsp;&nbsp;&nbsp{"+endText+
				"<br/>&nbsp;&nbsp;&nbsp;&nbsp;}<br/>&nbsp;&nbsp;}<br/>}";
		return resultText;
	}

	public int confirmReadRecord(String nftId) {
		return tranMapper.confirmReadRecord(nftId);
	}


}
