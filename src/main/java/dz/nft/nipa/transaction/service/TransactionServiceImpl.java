package dz.nft.nipa.transaction.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import dz.nft.nipa.dto.DataNftDto;
import dz.nft.nipa.dto.EthTransactionDto;
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
	public String getTransHashInput(String transHash) {
		return tranMapper.getTransHashInput(transHash.substring(2));
	}
	public int getTransHashcnt(String transHash) {
		return tranMapper.getTransHashcnt(transHash.substring(2));
	}
	public DataNftDto getNFTData(String nftId) {
		return tranMapper.getNFTData(nftId);
	}
	public ArrayList<String> getNFTID() {
		return tranMapper.getNFTID();
	}

	public int getFirstTransNum() {
//		Integer dataNum = tranMapper.getFirstTransNum();
		Integer dataNum = tranMapper.getEthFirstTransNum();
		logger.trace("dataNum = {}", dataNum);
		int resultNum = 0;
		if (dataNum != null) {
			resultNum = (int)dataNum;
		}
		return resultNum;
	}
	
	public ArrayList<EthTransactionDto> getRecentTrList() {
		return tranMapper.getEthRecentTrList();
	}

	public ArrayList<TransactionDto> getTrDataByBlocknum(int blNum) {
		return tranMapper.getTrDataByBlocknum(blNum);
	}

	public EthTransactionDto getTrDataById(String trId) {

		EthTransactionDto dto = tranMapper.getTrDataById(trId);
		if (dto == null) {
			return null;
		}
		
		// String ws = dto.getWriteSet();
//
//		if (dto.getChaincodename().equals("CHECK_LIVENESS") || dto.getChaincodename().equals("INIT_TOKEN")) {
//			dto.setWriteSet(null);
//
//		} else if (dto.getFcn().equals("CREATE_NFT")) {
//
//			Object[] test = ws.split("},");
//			String str = test[2].toString();
//			String subStr = str.substring(7, str.length()-12);
//			ObjectMapper mapper = new ObjectMapper();
//			HashMap<String, Object> map = null;
//			HashMap<String, Object> valueMap = null;
//			Map<String, Object> baseMap = new LinkedHashMap<String, Object>();
//			Map<String, Object> midMap = new LinkedHashMap<String, Object>();
//			HashMap<String, Object> resultMap = new HashMap<String, Object>();
//
//			try {
//				map = mapper.readValue(subStr, HashMap.class);
//				midMap.put("key", map.get("key"));
//				midMap.put("is_delete", map.get("is_delete"));
//				valueMap = mapper.readValue(map.get("value").toString(), HashMap.class);
//				baseMap.put("nft_id", valueMap.get("nft_id"));
//				baseMap.put("owner", valueMap.get("owner"));
//				baseMap.put("timestamp", valueMap.get("timestamp"));
//				baseMap.put("tokenuri", valueMap.get("tokenuri"));
//				baseMap.put("minter", valueMap.get("minter"));
//				baseMap.put("fcn", "CREATE_NFT");
//				baseMap.put("title", valueMap.get("title"));
//				midMap.put("value", baseMap);
//
//			} catch (JsonProcessingException e) {
//				logger.info("CREATE_NFT로 변환 오류");
//				return null;
//			}
//			resultMap.put("set", midMap);
//			String custom = JSONObject.toJSONString(resultMap);
//			dto.setWriteSet(CustomWriteSetDataText(custom));
//
//		} else if (dto.getFcn().equals("READ_NFT")) {
//
//			Object[] test = ws.split("},");
//			String str = test[2].toString();
//			String subStr = str.substring(7, str.length()-12);
//			ObjectMapper mapper = new ObjectMapper();
//			HashMap<String, Object> map = null;
//			HashMap<String, Object> valueMap = null;
//			Map<String, Object> baseMap = new LinkedHashMap<String, Object>();
//			Map<String, Object> midMap = new LinkedHashMap<String, Object>();
//			HashMap<String, Object> resultMap = new HashMap<String, Object>();
//
//			try {
//				map = mapper.readValue(subStr, HashMap.class);
//				midMap.put("key", map.get("key"));
//				midMap.put("is_delete", map.get("is_delete"));
//				valueMap = mapper.readValue(map.get("value").toString(), HashMap.class);
//				baseMap.put("nft_read_id", valueMap.get("nft_read_id"));
//				baseMap.put("timestamp", valueMap.get("timestamp"));
//				baseMap.put("fcn", "READ_NFT");
//				baseMap.put("nft_id", valueMap.get("nft_id"));
//				midMap.put("value", baseMap);
//
//			} catch (JsonProcessingException e) {
//				logger.info("READ_NFT 변환 오류");
//				return null;
//			}
//			resultMap.put("set", midMap);
//			String custom = JSONObject.toJSONString(resultMap);
//			dto.setWriteSet(CustomWriteSetDataText(custom));
//
//		} else {
//			dto.setWriteSet(null);
//		}
		
		return dto;
	}
	
	public int getTotalReadCnt() {
		logger.trace("조회 수 = {}", tranMapper.getEthTotalReadCnt());
		return tranMapper.getEthTotalReadCnt();
	}

	public ArrayList<TransactionDto> getTotalTrList(int pageNum) {
		return tranMapper.getTotalTrList(pageNum);
	}

	public HashMap<String, Object> readNftContents(String nftId) {
		String nftHash = new SendNftId2BlockchainApi().sendData(nftId).get("nftUri").toString();	// 호출함
		//String nftUri = new Base64DecodingUtil().base64Decoding(nftHash);
		/**
		 * 블록체인에서 콘텐츠 사이트 연결 URI를 정상적으로 리턴하지 못함.
		 * (응답값 자체를 BASE64 Decode 하지 못함. 오류 발생.
		 * https://www.nipa.kr 로 임시 설정
		 * 단, youtube 링크의 경우 아래와 같이 하드코딩
		 */
		String nftUri = "https://www.nipa.kr";
		String upperCaseNftId = (nftId == null) ? "" : nftId.toLowerCase();
		if ("1a64491ad8845f521ab54d62e639777c15cc9057".equals(upperCaseNftId)) { nftUri = "https://youtu.be/-UVyiZl8wxI?list=PLh0nQbUlbT1pz2wjvVt_u8jdLpuXEhlPG"; }
		if ("2f8a2c63a848223dc610d68ae2d912177d9939f8".equals(upperCaseNftId)) { nftUri = "https://youtu.be/4sr9QGw_0iE?list=PLh0nQbUlbT1pz2wjvVt_u8jdLpuXEhlPG"; }
		if ("c5304fbb5680ca11ad430122f1375cc6c8fc0cf0".equals(upperCaseNftId)) { nftUri = "https://youtu.be/yaUAe-65IAA?list=PLh0nQbUlbT1pz2wjvVt_u8jdLpuXEhlPG"; }
		if ("64284a6478a4bfc57bdb17dfdf65218e4de310f3".equals(upperCaseNftId)) { nftUri = "https://youtu.be/RKhOsD4UHbA?list=PLh0nQbUlbT1pz2wjvVt_u8jdLpuXEhlPG"; }
		if ("d1fac823dbf33a1309f1e9d4a7d2bbcc83d2b0ea".equals(upperCaseNftId)) { nftUri = "https://youtu.be/L0JJwvwT0S8?list=PLh0nQbUlbT1pz2wjvVt_u8jdLpuXEhlPG"; }
		if ("6e47dcc4d5a62da19c611a37784225d6540fc3f6".equals(upperCaseNftId)) { nftUri = "https://youtu.be/t1wI3qKxrV8?list=PLh0nQbUlbT1pz2wjvVt_u8jdLpuXEhlPG"; }
		if ("74bbd804396bacf76fad5330064373e5962445fd".equals(upperCaseNftId)) { nftUri = "https://youtu.be/rsECgf3yLA4?list=PLh0nQbUlbT1pz2wjvVt_u8jdLpuXEhlPG"; }
		if ("2d37fd0bfb20aa04f46828b860a48eba9a81e2f0".equals(upperCaseNftId)) { nftUri = "https://youtu.be/TU2XparxVEo?list=PLh0nQbUlbT1pz2wjvVt_u8jdLpuXEhlPG"; }
		if ("a387ce69b4647285d9690704641aac211386fcf2".equals(upperCaseNftId)) { nftUri = "https://youtu.be/yVvJlpuf43E?list=PLh0nQbUlbT1pz2wjvVt_u8jdLpuXEhlPG"; }
		if ("54b04a9f6cb58ca32c001d2d120402b67f183e5a".equals(upperCaseNftId)) { nftUri = "https://youtu.be/YAu_gOchsa0?list=PLh0nQbUlbT1pz2wjvVt_u8jdLpuXEhlPG"; }
		if ("e8efbbc84138896eda8ac829c5cd7d94fa9f14db".equals(upperCaseNftId)) { nftUri = "https://youtu.be/DVjMsZovfyI"; }
		if ("268c5db5b62a9a5b8e449e54f36a93638350412f".equals(upperCaseNftId)) { nftUri = "https://youtu.be/IrfrU0_Tiug"; }

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
