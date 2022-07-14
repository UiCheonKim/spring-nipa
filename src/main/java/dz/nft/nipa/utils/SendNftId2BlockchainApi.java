package dz.nft.nipa.utils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.jose4j.json.internal.json_simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class SendNftId2BlockchainApi {
	
	private static final Logger logger = LoggerFactory.getLogger(SendNftId2BlockchainApi.class);

	public HashMap<String, Object> sendData(String nftId) {
		
		HashMap<String, Object> requestMap = new HashMap<String, Object>();
		requestMap.put("nft_id", nftId);
		requestMap.put("timestamp", new UnixTimeUtil().makeUnixTime());
		
		JSONObject json = new JSONObject(requestMap);
		OkHttpClient client = new OkHttpClient();
		
		// 전달하고자 하는 데이터를 JSON 형식으로 FormDataPart에 삽입.
		RequestBody body = RequestBody.create(MediaType.parse("application/json"), json.toJSONString());
		
		// url안의 주소는 호출하고자 하는 API의 주소 및 포트번호
		Request request = new Request.Builder().url("http://121.78.145.25:4000/NFT_Get").post(body).build(); // SK_배포용
		// Request request = new Request.Builder().url("http://211.232.75.182:4000/NFT_Get").post(body).build(); // LG_test용
        
        HashMap<String, Object> responseMap = new HashMap<String, Object>();
        HashMap<String, Object> resultMap = null;
        ObjectMapper mapper = new ObjectMapper();
		try {
			// 외부API에 전달하는 값
	        Response response = client.newCall(request).execute();
	        
			// 외부 API로 부터 반환받는 값
	        String resultData = response.body().string();
	        responseMap = (HashMap<String, Object>) mapper.readValue(resultData, Map.class);
	        
	        if (responseMap != null && responseMap.get("success").toString().equals("true")) {
	        	// logger.info("Read_NFT API Result : "+responseMap.get("success").toString());
	        	resultMap = new HashMap<String, Object>();
	        	resultMap.put("nftUri", responseMap.get("content").toString());
			} else {
				if (!responseMap.get("success").toString().equals("true")) {
					logger.info("Read_NFT API Result : false");
					throw new IOException();
				}else {
					logger.info("Read_NFT API - 리턴값 없음");
					throw new IOException();
				}
			}
	        
		} catch (IOException e) {
			// e.printStackTrace();
			logger.info("Read_NFT API - 응답 없음");
			return null;
		}
		return resultMap;
	}
}
