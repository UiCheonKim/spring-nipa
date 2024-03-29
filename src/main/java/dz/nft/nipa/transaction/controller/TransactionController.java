package dz.nft.nipa.transaction.controller;

import java.io.ByteArrayOutputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import dz.nft.nipa.dto.*;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Hex;
import org.jose4j.json.internal.json_simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import dz.nft.nipa.block.service.BlockServiceImpl;
import dz.nft.nipa.transaction.service.TransactionServiceImpl;

@Controller
@Slf4j
public class TransactionController {
	
	@Autowired
	private TransactionServiceImpl tranServ;
	
	@Autowired
	private BlockServiceImpl blockServ;
	
	@GetMapping("/tranDetail")
	public String tranDetail(Model model, @RequestParam(defaultValue = "0") String trNum) throws ParseException {
		if (trNum == null) {
			return "redirect:./error";
		}
		log.trace("tranDetail trNum: {}", trNum);
		EthTransactionDto dto = tranServ.getTrDataById(trNum);
		log.trace("EthTransactionDto dto: {}", dto);

		InputDataDto transHash = tranServ.getTransHashInput(trNum);
		log.trace("transHash: {}", transHash.getInput());
//		String transHashInput = tranServ.getTransHashInput(trNum);
		String transHashInput = transHash.getInput();
		log.trace("transHashInput: {}", transHashInput);

		// trNum에 fd216f30이 포함되면 Read_NFT, 8e15ee89이 포함되면 Register_NFT
		// transHashInput가 null 이 아닐 때
		if (transHashInput != null) {
			if (transHashInput.contains("fd216f30")) {
				log.trace("Read_NFT");
				dto.setFcn("Read_NFT");
			} else if (transHashInput.contains("8e15ee89")) {
				log.trace("Register_NFT");
				dto.setFcn("Register_NFT");
			}
		}

		DataNftDto nftData = new DataNftDto();
		ArrayList<String> nftList = tranServ.getNFTID();
		for(int i = 0; i < nftList.size(); i++) {
//			log.trace("nftList.get({}) = {}", i, nftList.get(i));
//			log.trace("hexToUtf8(transHashInput.substring(2)) = {}", hexToUtf8(transHashInput.substring(2)));
			if (hexToUtf8(transHashInput.substring(2)).contains(nftList.get(i))) {
				nftData = tranServ.getNFTData(nftList.get(i).toUpperCase());
				break;
			}
		}

		log.trace("nftData: {}", nftData);

		String key = nftData.getNftId();

		boolean isDelete = false;
		if (nftData.getDelYn() != null) {
			if (nftData.getDelYn().equals("Y")) {
				isDelete = true;
			}
		}

		String nftId = nftData.getNftId();
		String owner = nftData.getNftOwner();
//		String timestamp = nftData.getNftOriginalDate(); // timestamp 는 무엇을 의미하는지?
		String timestamp = dto.getInsertedAt();
		String tokenUri = nftData.getNftUri();
		String minter = nftData.getNftPublisher();
		String fcn = dto.getFcn();
		String title = nftData.getNftTitle();

		// yyyy-MM-dd hh:mm:ss 를 timestamp로 변환
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		Date date = dateFormat.parse(timestamp);
		long unixTimestamp = date.getTime() / 1000; // 밀리초에서 초로 변환

		StringBuilder sb = new StringBuilder();
		sb.append("{\n");
		sb.append("  \"set\": \n");
		sb.append("  {\n");
		sb.append("    \"key\": \"").append(key).append("\",\n");
		sb.append("    \"is_delete\": ").append(isDelete).append(",\n");
		sb.append("    \"value\": \n");
		sb.append("    {\n");
		sb.append("      \"nft_id\": \"").append(nftId).append("\",\n");
		sb.append("      \"owner\": \"").append(owner).append("\",\n");
		sb.append("      \"timestamp\": \"").append(unixTimestamp).append("\",\n");
		sb.append("      \"tokenuri\": \"").append(tokenUri).append("\",\n");
		sb.append("      \"minter\": \"").append(minter).append("\",\n");
		sb.append("      \"fcn\": \"").append(fcn).append("\",\n");
		sb.append("      \"title\": \"").append(title).append("\"\n");
		sb.append("    }\n");
		sb.append("  }\n");
		sb.append("}");

		String jsonString = sb.toString();

		WriteSetDto writeSetDto = new WriteSetDto();



		ObjectMapper objectMapper = new ObjectMapper();
		String jsonPost;
		try {
			jsonPost = objectMapper.writeValueAsString(writeSetDto);
		} catch (JsonProcessingException e) {
			throw new RuntimeException(e);
		}

		dto.setNftId(nftData.getNftId());
//		dto.setWriteSet(jsonPost);

		dto.setWriteSet(jsonString);



		String tmp = "0x0";
		// 뭔지 몰라서 주석 처리함
//		dto.setHash(tmp.getBytes(StandardCharsets.UTF_8));
//		dto.setBlockNumber(Integer.parseInt("1x1"));
//		dto.setInsertedDt("1x2");

		if (dto == null) {
			return "redirect:./error";
		}
//		EthBlockDto dto1 = new EthBlockDto();
//
//		dto1.setHash(tmp.getBytes(StandardCharsets.UTF_8));
//		dto1.setMinerHash(tmp.getBytes(StandardCharsets.UTF_8));
//		dto1.setParentHash(tmp.getBytes(StandardCharsets.UTF_8));
//		dto1.setTimestamp("0x3");
//		log.info("tranDetail dto: {}", dto);

		model.addAttribute("data", dto);
    model.addAttribute("blData", blockServ.getBlDataByBlocknum(dto.getBlockNumber()));
		return "/transaction/transactionDetail";
	}
	
	@RequestMapping("/tranList")
	public String tranList(Model model, @RequestParam(defaultValue = "1") int pageNum) {
		
		ArrayList<TransactionDto> list = tranServ.getTotalTrList(pageNum);
		
		int count = tranServ.getTransCnt();
		int totalPageCount = (int) Math.ceil(count/10.0);
		int currentPage = pageNum;
		int beginPage = ((currentPage - 1)/10)*10 + 1;
		int endPage = ((currentPage - 1)/10 + 1)*(10);
		if(endPage > totalPageCount) {
			endPage = totalPageCount;
		}
		
		model.addAttribute("beginPage", beginPage);
	    model.addAttribute("endPage", endPage);
		model.addAttribute("currentPage", currentPage);
		model.addAttribute("totalPageCount", totalPageCount);
		model.addAttribute("list", list);
		model.addAttribute("count", count);
		
		return "/transaction/transactionList";
	}
	
	@PostMapping("/updateTrList")
	@ResponseBody
	public ArrayList<TransactionDto> updateTrList(int pageNum){
		return tranServ.getTotalTrList(pageNum);
	}
	
	@RequestMapping("/searchByTrHash")
	public String searchByHash(String searchWord){
		if (searchWord == null) {
			return "redirect:./error";
		}
		String trNum = "0x" + tranServ.getTrNumByHash(searchWord.trim()).substring(2);
		System.out.println(trNum);
		return "redirect:./tranDetail?trNum="+trNum;
	}

	public static String hexToUtf8(String hex) {
//		StringBuilder sb = new StringBuilder();
//		log.trace("hex = {}", hex);
//
//		for (int i = 0; i < hex.length(); i += 2) {
//			String str = hex.substring(i, i + 2); // 16진수 문자열을 2자리씩 잘라서 문자열 배열에 저장
//			sb.append((char) Integer.parseInt(str, 16)); // 16진수 문자열을 10진수로 변환 // 10진수를 문자로 변환 // 문자열을 붙임
//		}
//		return sb.toString();
//		log.trace("hex = {}", hex);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		for (int i = 0; i < hex.length(); i += 2) {
			String str = hex.substring(i, i + 2);
			int byteVal = Integer.parseInt(str, 16);
			baos.write(byteVal);
		}
		return baos.toString(StandardCharsets.UTF_8);
	}

	
}
