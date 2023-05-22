package dz.nft.nipa.transaction.controller;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import dz.nft.nipa.dto.EthBlockDto;
import dz.nft.nipa.dto.EthTransactionDto;
import dz.nft.nipa.dto.WriteSetDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import dz.nft.nipa.block.service.BlockServiceImpl;
import dz.nft.nipa.dto.TransactionDto;
import dz.nft.nipa.transaction.service.TransactionServiceImpl;

@Controller
@Slf4j
public class TransactionController {
	
	@Autowired
	private TransactionServiceImpl tranServ;
	
	@Autowired
	private BlockServiceImpl blockServ;
	
	@GetMapping("/tranDetail")
	public String tranDetail(Model model, @RequestParam(defaultValue = "0") String trNum) {
		if (trNum == null) {
			return "redirect:./error";
		}
		log.trace("tranDetail trNum: {}", trNum);
		EthTransactionDto dto = tranServ.getTrDataById(trNum);

		String transHashInput = tranServ.getTransHashInput(trNum);
		log.trace("transHashInput: {}", transHashInput);

		// trNum에 fd216f30이 포함되면 Read_NFT, 8e15ee89이 포함되면 Register_NFT
		if (transHashInput.contains("fd216f30")) {
			log.trace("Read_NFT");
			dto.setFcn("Read_NFT");
		} else if (transHashInput.contains("8e15ee89")) {
			log.trace("Register_NFT");
			dto.setFcn("Register_NFT");
		}
		log.trace("EthTransactionDto.getFcn: {}", dto.getFcn());

		WriteSetDto writeSetDto = new WriteSetDto();
		writeSetDto.setName("문");
		writeSetDto.setNamename("정인");

		ObjectMapper objectMapper = new ObjectMapper();
		String jsonPost;
		try {
			jsonPost = objectMapper.writeValueAsString(writeSetDto);
		} catch (JsonProcessingException e) {
			throw new RuntimeException(e);
		}

		dto.setWriteSet(jsonPost);



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
		log.info("tranDetail dto: {}", dto);

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
		int trNum = tranServ.getTrNumByHash(searchWord.trim());
		return "redirect:./tranDetail?trNum="+trNum;
	}
	
}
