package dz.nft.nipa.mainboard.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import dz.nft.nipa.block.service.BlockServiceImpl;
import dz.nft.nipa.dto.DataNftDto;
import dz.nft.nipa.dto.DataTypeDto;
import dz.nft.nipa.dto.TransactionDto;
import dz.nft.nipa.mainboard.service.MainboardServiceImpl;
import dz.nft.nipa.transaction.service.TransactionServiceImpl;
import dz.nft.nipa.utils.MessageRedirectUtil;

@Controller
public class MainboardController {
	
	private static final Logger log = LoggerFactory.getLogger(MainboardController.class);

	@Autowired
	private MainboardServiceImpl mainServ;
	
	@Autowired
	private BlockServiceImpl blockServ;
	
	@Autowired
	private TransactionServiceImpl tranServ;
	
	@RequestMapping("/")
	public String mainboardPage(Model model, HttpServletRequest req, HttpServletResponse resp) {
		
		if("121.78.145.152".equals(req.getServerName())) {
			try {
				resp.sendRedirect("https://nipanft.docuchain.kr/");
				return null;
			} catch (IOException e) {
				log.info("도메인 포워딩 에러");
				return null;
			}
		}
		
		ArrayList<DataTypeDto> typeList = mainServ.getAllDataType();
		model.addAttribute("typeList", typeList);
		
		ArrayList<HashMap<String, Object>> returnList = new ArrayList<HashMap<String, Object>>();
		for (DataTypeDto dto : typeList) {
			HashMap<String, Object> map = new HashMap<String, Object>();
			ArrayList<String> dateList = mainServ.getNftDateList(dto.getTypeNum());
			map.put("dateList", dateList);
			map.put("typeDto", dto);
			returnList.add(map);
		}
		model.addAttribute("tabList", returnList);
		
		return "/mainboard/mainPage";
	}
	
	@RequestMapping("/tabs")
	public String firstTab(Model model, String dateType, int typeNum) {
		if (dateType.equals("ALL")) {
			model.addAttribute("dataList", mainServ.getAllNftListByDataType(typeNum));
		}else {
			model.addAttribute("dataList", mainServ.getNftListByDateType(typeNum, dateType));
		}
		return "/mainboard/module/firstTab";
	}
	
	
	@RequestMapping("/nftDetail")
	public String nftDetail(Model model, int nftNum, @RequestParam(required = false) String sYn, HttpSession session) {
		
		// nft data 테이블 readCnt증가 쿼리 - 필요 없는 것 확정되면 지우기
		// mainServ.updateReadCnt(nftNum);
		HashMap<String, Object> nftUriMap = null;
		HashMap<String, Object> nftDataMap = mainServ.getNftDataBynftNum(nftNum);

		if (nftDataMap == null) {
			return "redirect:./error";
		}

		if (session.getAttribute("nftUri") == null || session.getAttribute("nftNum") == null) {
			nftUriMap = tranServ.readNftContents(nftDataMap.get("nft_id").toString());
			session.setAttribute("nftNum", nftNum);
			session.setAttribute("nftUri", nftUriMap.get("data"));
			model.addAttribute("uri", nftUriMap.get("data"));
			int cnt = Integer.parseInt(nftDataMap.get("nft_cnt").toString()) + 1;
			nftDataMap.put("nft_cnt", cnt);

		} else if (Integer.parseInt(session.getAttribute("nftNum").toString()) != nftNum) {
			nftUriMap = tranServ.readNftContents(nftDataMap.get("nft_id").toString());
			session.setAttribute("nftNum", nftNum);
			session.setAttribute("nftUri", nftUriMap.get("data"));
			model.addAttribute("uri", nftUriMap.get("data"));
			int cnt = Integer.parseInt(nftDataMap.get("nft_cnt").toString()) + 1;
			nftDataMap.put("nft_cnt", cnt);

		}

		model.addAttribute("sYn", sYn);
		model.addAttribute("uri", session.getAttribute("nftUri"));
		model.addAttribute("data", nftDataMap);
		return "/nft/nftDetail";
		
	}
	
	/*
	// increaseReadCnt - nft 열람 Read_NFT Api에 등록
	@RequestMapping("/read")
	@ResponseBody
	public HashMap<String, Object> readNftContents(String nftId) {
		log.info("Read_NFT 호출 성공");
		return tranServ.readNftContents(nftId);;
	}
	*/
	
	@RequestMapping("/intro")
	public String intro() {
		return "/mainboard/introPage";
	}
	
	@GetMapping("/file")
	public void setImageFileById(int id, HttpServletResponse response) throws IOException {
		
		// 배포시 수정
		// StringBuilder sb = new StringBuilder("file:///c:/nipa_upload/"); // 개발용
        StringBuilder sb = new StringBuilder("file:///nipa_upload/"); // 배포용
        
        // 파일이 실제로 저장되어 있는 경로에
        String fileLocation = mainServ.getImgLocation(id);
        
        if (fileLocation == null) {
			new MessageRedirectUtil().redirect("잘못된 접근입니다.", "/error");
			return;
		}
        
        sb.append(fileLocation);
        // 파일 이름을 더해
        
        URL fileUrl = new URL(sb.toString());
        // file URL을 생성하고 
        
        IOUtils.copy(fileUrl.openStream(), response.getOutputStream());
        // IOUtils.copy는 input에서 output으로 encoding 맞춰서 복사하는 메소드다
        // openStream으로 fileUrl의 통로( 입력 스트림 )를 열고 response의 outputStream에 복사하면 끝
 
    }
	
	//==============================================================================
	
	// nft 전체 현황
	@RequestMapping("/nft")
	public String nftPage(Model model) {
		model.addAttribute("nftCnt", mainServ.getNftDataCnt());
		// model.addAttribute("blCnt", blockServ.getBlockCnt());
		// model.addAttribute("trCnt", tranServ.getTransCnt());
		model.addAttribute("blCnt", blockServ.getFirstBlockNum());
		model.addAttribute("trCnt", tranServ.getFirstTransNum());
		model.addAttribute("recentBlList", blockServ.getRecentBlList());
		model.addAttribute("recentTrList", tranServ.getRecentTrList());
		model.addAttribute("totalCnt", tranServ.getTotalReadCnt());
		
		return "/nft/nftPage";
	}
	
	@RequestMapping("/getNftPageUpdateData")
	@ResponseBody
	public HashMap<String, Object> getNftPageUpdateData(){
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("nftCnt", mainServ.getNftDataCnt());
		map.put("readCnt", tranServ.getTotalReadCnt());
		map.put("blCnt", blockServ.getFirstBlockNum());
		map.put("trCnt", tranServ.getFirstTransNum());
		return map;
	}
	
	@RequestMapping("/getNftPageUpdateBlData")
	@ResponseBody
	public ArrayList<HashMap<String, Object>> getNftPageUpdateBlData(){
		return blockServ.getRecentBlList();
	}
	
	@RequestMapping("/getNftPageUpdateTrData")
	@ResponseBody
	public ArrayList<TransactionDto> getNftPageUpdateTrData(){
		return tranServ.getRecentTrList();
	}
	
	//==============================================================================
	
	// 검색
	@PostMapping("/testSearchId")
	@ResponseBody
	public HashMap<String, Object> testSearchId(String searchWord){
		
		HashMap<String, Object> map = new HashMap<String, Object>();
		
		if (searchWord.contains("<") 
			|| searchWord.contains(">")
			|| searchWord.contains("@")
			|| searchWord.contains("#")
			|| searchWord.contains("$")
			|| searchWord.contains("&")
			|| searchWord.contains("%")
			|| searchWord.contains("|")
			|| searchWord.contains("'")
			|| searchWord.toLowerCase().contains("xml")
			|| searchWord.toLowerCase().contains("script")
			|| searchWord.toLowerCase().contains("select")
			|| searchWord.toLowerCase().contains("delete")
			|| searchWord.toLowerCase().contains("from")
			|| searchWord.toLowerCase().contains("insert")
			|| searchWord.toLowerCase().contains("update")
			|| searchWord.toLowerCase().contains("drop")
			) {
			return map;
		} 
		
		map.put("result", mainServ.getTestResult4SearchWord(searchWord.toLowerCase()));
		return map;
	}
	
	@PostMapping("/testSearchHash")
	@ResponseBody
	public HashMap<String, Object> testSearchHash(String searchWord){
		
		HashMap<String, Object> map = new HashMap<String, Object>();
		
		if (searchWord.contains("<") 
			|| searchWord.contains(">")
			|| searchWord.contains("@")
			|| searchWord.contains("#")
			|| searchWord.contains("$")
			|| searchWord.contains("&")
			|| searchWord.contains("%")
			|| searchWord.contains("|")
			|| searchWord.contains("'")
			|| searchWord.toLowerCase().contains("xml")
			|| searchWord.toLowerCase().contains("script")
			|| searchWord.toLowerCase().contains("select")
			|| searchWord.toLowerCase().contains("delete")
			|| searchWord.toLowerCase().contains("from")
			|| searchWord.toLowerCase().contains("insert")
			|| searchWord.toLowerCase().contains("update")
			|| searchWord.toLowerCase().contains("drop")
			) {
			return map;
		}
		
		int tranResult = tranServ.getTestResult4SearchHash(searchWord.trim());
		int blockResult = blockServ.getTestResult4SearchHash(searchWord.trim());
		
		if (tranResult == 0 && blockResult == 1) {
			map.put("result", 1);
			return map;
		} else if (tranResult == 1 && blockResult == 0) {
			map.put("result", 2);
			return map;
		}
		
		map.put("result", 0);
		return map;
	}
	
	
	@RequestMapping("/searchById")
	public String searchById(String searchWord){
		DataNftDto dto = mainServ.getNftDataByNftId(searchWord.toLowerCase().trim());
		if (dto == null) {
			return "redirect:./";
		}
		return "redirect:./nftDetail?nftNum="+dto.getNftNum()+"&sYn=Y";
	}
	
	
}
