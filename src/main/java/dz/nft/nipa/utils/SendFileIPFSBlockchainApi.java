package dz.nft.nipa.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import dz.nft.nipa.SessionConst;
import okhttp3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class SendFileIPFSBlockchainApi {
	
	private static final Logger log = LoggerFactory.getLogger(SendFileIPFSBlockchainApi.class);
	
	public String sendData(MultipartFile file) throws IOException {

		OkHttpClient client = new OkHttpClient();

		log.info("file.getName() = {}", file.getName());
		log.info("file.originalFilename = {}", file.getOriginalFilename());
		log.info("file.getContentType() = {}", file.getContentType());

		// multipart
		MultipartBody requestBody = new MultipartBody.Builder()
				.setType(MultipartBody.FORM)
				.addFormDataPart("file", file.getOriginalFilename(),
						RequestBody.create(file.getBytes(), MediaType.parse(Objects.requireNonNull(file.getContentType()))))
				// Objects.requireNonNull 란 null 이 아닌 경우에만 실행하는 메소드 null 일 경우 NullPointerException 발생
				.build();
		
		// url안의 주소는 호출하고자 하는 API의 주소 및 포트번호
		Request request = new Request.Builder()
				.url(SessionConst.DOCUCHAIN_URL + "/saveIPFS")
				.post(requestBody)
				.build(); // 이더리움 용
        
        HashMap<String, Object> resultMap;
        ObjectMapper mapper = new ObjectMapper();
		try {
			// 외부API에 전달하는 값
	        Response response = client.newCall(request).execute();
	        
			// 외부 API로 부터 반환받는 값
	        String resultData = response.body().string();
	        resultMap = (HashMap<String, Object>) mapper.readValue(resultData, Map.class);
			log.info("resultMap = {}", resultMap);

/*
	        if (resultMap != null && resultMap.get("code").toString().equals("100")) {
	        	// log.info("Create_NFT API Result : "+resultMap.get("success").toString());
				String ipfsCid = resultMap.get("message").toString();
				log.info("ipfsCid = {}", ipfsCid);
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
*/
			if (resultMap != null && resultMap.get("code").toString().equals("100")) {
				// log.info("Create_NFT API Result : "+resultMap.get("success").toString());
				String ipfsCid = resultMap.get("message").toString();
				log.info("ipfsCid = {}", ipfsCid);
				return ipfsCid;
//				new MessageRedirectUtil().redirect("NFT 발급에 성공하였습니다.", "/admin/nftList");
			} else {
				log.info("Create_NFT API - 리턴값 없음");
				throw new IOException();
			}


		} catch (IOException e) {
			// e.printStackTrace();
			log.info("Create_NFT API - 응답 없음");
			new MessageRedirectUtil().redirect("NFT 발급 중 예상하지 못한 오류가 발생하였습니다.", "/admin/input");
		}
		return null;
	}
	
	
}
