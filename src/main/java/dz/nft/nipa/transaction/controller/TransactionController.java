package dz.nft.nipa.transaction.controller;

import java.util.ArrayList;

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
public class TransactionController {
	
	@Autowired
	private TransactionServiceImpl tranServ;
	
	@Autowired
	private BlockServiceImpl blockServ;
	
	@GetMapping("/tranDetail")
	public String tranDetail(Model model, @RequestParam(defaultValue = "0") int trNum) {
		if (trNum == 0) {
			return "redirect:./error";
		}
		TransactionDto dto = tranServ.getTrDataById(trNum);
		if (dto == null) {
			return "redirect:./error";
		}
		model.addAttribute("data", dto);
		model.addAttribute("blData", blockServ.getBlDataByBlocknum(dto.getBlockid()));
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
