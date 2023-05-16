package dz.nft.nipa.block.controller;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

import dz.nft.nipa.dto.EthBlockDto;
import dz.nft.nipa.dto.EthTransactionDto;
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
import dz.nft.nipa.dto.BlockDto;
import dz.nft.nipa.transaction.service.TransactionServiceImpl;
import dz.nft.nipa.utils.MessageRedirectUtil;

@Controller
@Slf4j
public class BlockController {
	
	@Autowired
	private BlockServiceImpl blockServ;
	
	@Autowired
	private TransactionServiceImpl tranServ;
	
	@GetMapping("/blockDetail")
	public String blockDetail(Model model, @RequestParam(defaultValue = "0") int blNum) {
		log.debug("blockDetail==========================================");
		if (blNum == 0) {
			return "redirect:./error";
		}

		EthBlockDto dto = blockServ.getBlDataById(blNum);

		// EthBlockDto dto = new EthBlockDto();
//		String tmp = "0x0";
//		dto.setHash(tmp.getBytes(StandardCharsets.UTF_8));
//		dto.setMinerHash(tmp.getBytes(StandardCharsets.UTF_8));
//		dto.setParentHash(tmp.getBytes(StandardCharsets.UTF_8));
//		dto.setTimestamp("0x3");
		if (dto == null) {
			return "redirect:./error";
		}

//		EthTransactionDto dto1 = new EthTransactionDto();
//		dto1.setHash(tmp.getBytes(StandardCharsets.UTF_8));
//		dto1.setBlockNumber(Integer.parseInt("1x1"));
//		dto1.setInsertedDt("1x2");

		model.addAttribute("data", dto);
    model.addAttribute("trList", tranServ.getTrDataByBlocknum(Integer.parseInt(dto.getNumber())));
		// model.addAttribute("trList", dto1);
		return "/block/blockDetail";
	}
	
	@RequestMapping("/blockList")
	public String blockList(Model model, @RequestParam(defaultValue = "1") int pageNum) {
		
		ArrayList<BlockDto> list = blockServ.getTotalBlList(pageNum);
		
		int count = blockServ.getBlockCnt();
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
		
		return "/block/blockList";
	}
	
	@PostMapping("/updateBlList")
	@ResponseBody
	public ArrayList<BlockDto> updateBlList(int pageNum){
		return blockServ.getTotalBlList(pageNum);
	}
	
	@RequestMapping("/searchByBlHash")
	public String searchByHash(String searchWord){
		int blNum = blockServ.getBlNumByHash(searchWord.trim());
		return "redirect:./blockDetail?blNum="+blNum;
	}
	
	@RequestMapping("/prevBlock")
	public String prevBlock(@RequestParam(value = "h") String blHash) {
		int blNum = blockServ.getBlNumByHash(blHash);
		if (blNum == 0) {
			return null;
		}
		return "redirect:./blockDetail?blNum="+blNum;
	}
	
}
