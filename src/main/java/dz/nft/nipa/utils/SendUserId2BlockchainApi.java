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

public class SendUserId2BlockchainApi {
	
	private static final Logger logger = LoggerFactory.getLogger(SendUserId2BlockchainApi.class);

	public String sendData(String userId) {
		
		Map<String, Object> map = new HashMap<String, Object>();
		// map.put("username", "digitalzonenipanft");
		map.put("username", userId);
		
		JSONObject json = new JSONObject(map);
		OkHttpClient client = new OkHttpClient();
		
		String resultStr = null;
		
		// 전달하고자 하는 데이터를 JSON 형식으로 FormDataPart에 삽입.
		RequestBody body = RequestBody.create(MediaType.parse("application/json"), json.toJSONString());
		// logger.info("Create_USER API sendData : "+json.toJSONString());
		// logger.info("Create_USER API sendData_bodyType : "+body.contentType());
		
		// url안의 주소는 호출하고자 하는 API의 주소 및 포트번호
		Request request = new Request.Builder().url("http://121.78.145.25:4000/users").post(body).build(); // SK_배포용
		// Request request = new Request.Builder().url("http://211.232.75.182:4000/users").post(body).build(); // LG_test용
		
		HashMap<String, Object> resultMap = null;
        ObjectMapper mapper = new ObjectMapper();
		
        try {
			// 외부API에 전달하는 값
	        Response response = client.newCall(request).execute();
	        
			// 외부 API로 부터 반환받는 값
	        String resultData = response.body().string();
	        resultMap = (HashMap<String, Object>) mapper.readValue(resultData, Map.class);
	        
	        if (resultMap != null && resultMap.get("success").toString().equals("true")) {
	        	// logger.info("Create_USER API Result : "+resultMap.get("success").toString());
	        	resultStr = resultMap.get("token").toString();
			} else {
				if (!resultMap.get("success").toString().equals("true")) {
					logger.info("Create_USER API Result : false");
					throw new IOException();
				}else {
					logger.info("Create_USER API - 리턴값 없음");
					throw new IOException();
				}
			}
	        
		} catch (IOException e) {
			// e.printStackTrace();
			logger.info("Create_USER API - 응답 없음");
			new MessageRedirectUtil().redirect("token발급에 실패하였습니다.", "/admin/input");
		}
        
		return resultStr;
		
	}
	
}
