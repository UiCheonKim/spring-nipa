package dz.nft.nipa.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Base64EncodeingUtil {
	
	private static final Logger log = LoggerFactory.getLogger(Base64EncodeingUtil.class);
	
	public String send2BlockChainApi(String nftUrl) throws IOException {
		// 서버 배포시 수정해야함
		
		// 개발용 코드
		String saveFilePath = "C:/testTextFile"; // txt파일이 저장될 경로
		Files.createDirectories(Paths.get(saveFilePath)); // 폴더 생성
		// String fileName = nftUrl.substring(nftUrl.length()-9, nftUrl.length());

		
/*
		// 배포용 코드
		String saveFilePath = "/testTextFile"; // txt파일이 저장될 경로
		Files.createDirectories(Paths.get(saveFilePath)); // 폴더 생성
		// String fileName = nftUrl.substring(nftUrl.length()-9, nftUrl.length());
*/
		
		// 파일 객체 생성
		// File file = new File(saveFilePath+"/"+fileName+".txt"); // 파일을 매번 새로 만들어야 할 경우 - ex) log
		File file = new File(saveFilePath+"/uriText.txt");

		try {
			// true 지정시 파일의 기존 내용에 이어서 작성
			FileWriter fw = new FileWriter(file, false);

			// 파일안에 문자열 쓰기
			fw.write(nftUrl);
			fw.flush();

			// 객체 닫기
			fw.close();

		} catch (Exception e) {
			log.info("txt 작성 오류");
			return null;
		}

		byte[] data = new byte[(int) file.length()];

		try (FileInputStream stream = new FileInputStream(file)) {
			stream.read(data, 0, data.length);
		} catch (Throwable e) {
			log.info("txt 변환 오류");
			return null;
		}

		String base64data = Base64.getEncoder().encodeToString(data);

		return base64data;
	}

}
