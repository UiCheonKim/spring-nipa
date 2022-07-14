package dz.nft.nipa.utils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.jose4j.json.internal.json_simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.databind.ObjectMapper;

import dz.nft.nipa.transaction.service.TransactionServiceImpl;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class SendBase64File2BlockchainApi {
	
	private static final Logger log = LoggerFactory.getLogger(SendBase64File2BlockchainApi.class);
	
	public void sendData(HashMap<String, Object> requestMap, String token) throws IOException {
		
		JSONObject json = new JSONObject(requestMap);
		OkHttpClient client = new OkHttpClient();
		
		// 전달하고자 하는 데이터를 JSON 형식으로 FormDataPart에 삽입.
		RequestBody body = RequestBody.create(MediaType.parse("application/json"), json.toJSONString());
		
		// url안의 주소는 호출하고자 하는 API의 주소 및 포트번호
		Request request = new Request.Builder().addHeader("Authorization", "Bearer " + token)
				.url("http://121.78.145.25:4000/NFT_Create").post(body).build(); // SK_배포용
		//Request request = new Request.Builder().addHeader("Authorization", "Bearer " + token)
		//		.url("http://211.232.75.182:4000/NFT_Create").post(body).build(); // LG_test용
        
        HashMap<String, Object> resultMap = new HashMap<String, Object>();
        ObjectMapper mapper = new ObjectMapper();
		try {
			// 외부API에 전달하는 값
	        Response response = client.newCall(request).execute();
	        
			// 외부 API로 부터 반환받는 값
	        String resultData = response.body().string();
	        resultMap = (HashMap<String, Object>) mapper.readValue(resultData, Map.class);
	        
	        if (resultMap != null && resultMap.get("success").toString().equals("true")) {
	        	// log.info("Create_NFT API Result : "+resultMap.get("success").toString());
				new MessageRedirectUtil().redirect("NFT 발급에 성공하였습니다.", "/admin/nftList");
			} else {
				if (!resultMap.get("success").toString().equals("true")) {
					log.info("Create_NFT API Result : false");
					throw new IOException();
				}else {
					log.info("Create_NFT API - 리턴값 없음");
					throw new IOException();
				}
			}
	        
		} catch (IOException e) {
			// e.printStackTrace();
			log.info("Create_NFT API - 응답 없음");
			new MessageRedirectUtil().redirect("NFT 발급 중 예상하지 못한 오류가 발생하였습니다.", "/admin/input");
		}
		
	}
	
	
}
