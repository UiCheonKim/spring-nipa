package dz.nft.nipa.utils;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItem;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

public class Base64EncodeingUtil {
	
	private static final Logger log = LoggerFactory.getLogger(Base64EncodeingUtil.class);
	
	public MultipartFile send2BlockChainApi(String nftUrl) throws IOException {
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

		// File 객체를 MultipartFile 객체로 변환
		// DiskFileItem 이란 FileItem 의 구현체로서, 파일을 디스크에 기록하는 방식으로 파일을 처리한다.
		FileItem fileItem = new DiskFileItem(
				"file", // 파일 필드의 이름을 나타내는 문자열
				Files.probeContentType(file.toPath()), // 파일의 컨텐츠 타입을 나타내는 문자열
				false, // 파일 업로드가 아닌 일반 양식 필드인지 여부
				file.getName(), // 파일의 이름을 나타내는 문자열
				(int) file.length(), // 임시 파일을 생성하기 위한 메모리의 임계값
				file.getParentFile() // 파일이 저장될 임시 디렉토리를 지정
		);

		try (FileInputStream fis = new FileInputStream(file)) {
//			InputStream input = new FileInputStream(file);
//			OutputStream os = fileItem.getOutputStream();
//			IOUtils.copy(input, os);
			// Or faster..
			IOUtils.copy(fis, fileItem.getOutputStream());
		}  catch (IOException e) {
			e.printStackTrace();
		}

		return new CommonsMultipartFile(fileItem);


/*
		byte[] data = new byte[(int) file.length()];

		try (FileInputStream stream = new FileInputStream(file)) {
			stream.read(data, 0, data.length);
		} catch (Throwable e) {
			log.info("txt 변환 오류");
			return null;
		}

		String base64data = Base64.getEncoder().encodeToString(data);

		return base64data;
*/
	}


}
