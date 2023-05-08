package dz.nft.nipa;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@SpringBootTest
class TestHttp {
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void RequestGetBlockChain() throws RestClientException, JsonProcessingException {
        String Url = "https://3ee8-218-236-45-186.ngrok-free.app/nipa/pingping";
//        String Id = "identityy";
//        HttpHeaders headers = new HttpHeaders();
//        headers.add("Authorization", "Bearer eyJyZWdEYXRlIjoxNjgwNTEzOTM2Mzk1LCJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VySWQiOiJkaWdpdGFsem9uZSIsInN1YiI6IlBheWxvYWQtU3ViamVjdCIsImlhdCI6MTY4MDUxMzkzNiwiZXhwIjoxNjgwNTE3NTM2fQ._vJ6GMrexFLlqfmZazhi2oFQrPf0iCsT9Mv4rFCI_ik");

        Map<String, Object> jsonRequest = new ConcurrentHashMap<>();

//        String paramUrl = TokenUrl + "?id=" + Id;


//        HttpEntity<?> request = new HttpEntity<>(jsonRequest, headers);

        RestTemplate rt = new RestTemplate();
        ResponseEntity<String> response = rt.exchange(
                Url,
                HttpMethod.GET,
                null,
                String.class
        );

        // ResponseBody를 JsonNode로 변환
        JsonNode rootNode = objectMapper.readTree(response.getBody());
        // key 값으로 value 추출
        String value = rootNode.get("code").asText();
        System.out.println(value);

        if (value.equals("200")) {
            System.out.println("성공");
        } else {
            System.out.println("실패");
        }

        // uuid 생성
        String uuid = UUID.randomUUID().toString();
        System.out.println(uuid);

        String uuid2 = UUID.randomUUID().toString().replaceAll("-", "").toUpperCase();
        System.out.println(uuid2);

        System.out.println("response = " + response);
        System.out.println(response.getBody());

    }
}
