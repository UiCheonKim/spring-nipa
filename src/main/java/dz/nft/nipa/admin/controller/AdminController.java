package dz.nft.nipa.admin.controller;

import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import dz.nft.nipa.admin.service.AdminServiceImpl;
import dz.nft.nipa.dto.DataDetailDto;
import dz.nft.nipa.dto.DataNftDto;
import dz.nft.nipa.dto.DataTypeDto;
import dz.nft.nipa.mainboard.service.MainboardServiceImpl;
import dz.nft.nipa.security.MyAuthentication;
import dz.nft.nipa.transaction.service.TransactionServiceImpl;
import dz.nft.nipa.utils.MessageRedirectUtil;

@Slf4j
@Controller
@RequestMapping("/admin")
public class AdminController {
	
	@Autowired
	private MainboardServiceImpl mainServ;

	@Autowired
	private AdminServiceImpl adminServ;
	
	@Autowired
	private TransactionServiceImpl tranServ;
	
	@RequestMapping("/login")
	public String loginPage(Model model) {
		adminServ.createAdminUser();
		return "/admin/loginPage";
	}

	@RequestMapping("/input")
	public String insertPage(Model model, Principal principal) {
		model.addAttribute("type", mainServ.getAllDataType());
		return "/admin/insertPage";
	}
	
	@RequestMapping("/getDetailList")
	@ResponseBody
	public ArrayList<DataDetailDto> getDetailList(@RequestParam(defaultValue = "0") int typeNum){
		if (typeNum == 0) {
			return null;
		}
		return mainServ.getAllDataDetail(typeNum);
	}

	@RequestMapping("/insertNftProc")
	@ResponseBody
	public void insertNftProc(DataNftDto dto, Principal principal, @RequestPart MultipartFile file) throws IOException{
		
		if (file.isEmpty() || dto.getNftUri().isEmpty() || dto.getNftUri().isBlank() || dto.getNftUri() == null) {
			new MessageRedirectUtil().redirect("동영상 URI 혹은 첨부파일을 입력해주세요.", "/admin/input");
			
		} else if (dto.toString().contains("<") 
				|| dto.toString().contains(">")
				|| dto.toString().contains("@")
				|| dto.toString().contains("#")
				|| dto.toString().contains("$")
				|| dto.toString().contains("&")
				|| dto.toString().contains("%")
				|| dto.toString().contains("|")
				|| dto.toString().contains("'")
				|| dto.toString().toLowerCase().contains("xml")
				|| dto.toString().toLowerCase().contains("script")
				|| dto.toString().toLowerCase().contains("select")
				|| dto.toString().toLowerCase().contains("delete")
				|| dto.toString().toLowerCase().contains("from")
				|| dto.toString().toLowerCase().contains("insert")
				|| dto.toString().toLowerCase().contains("update")
				|| dto.toString().toLowerCase().contains("drop")
			){
			new MessageRedirectUtil().redirect("허용되지 않는 입력값이 포함되어있습니다.", "/admin/input");
			
		} else {
			// session token 값 추출
			MyAuthentication my = (MyAuthentication) principal;
			// userNum 가져오기
			dto.setUserNum(my.getUserDto().getUserNum());
			
			// pk 세팅
			int createNftKey = adminServ.createNftKey();
			dto.setNftNum(createNftKey);
			log.trace("pk 세팅 = {}", createNftKey);

			// dto.getNftId() 값이 없으면 생성
			if (dto.getNftId() == null || dto.getNftId().isEmpty() || dto.getNftId().isBlank()) {
				dto.setNftId(UUID.randomUUID().toString().replace("-", "").toUpperCase());
			}

//			adminServ.makeThumbnailImg(dto.getNftNum(), file); // DB에 이미지 관련 데이터 저장
			adminServ.insertNftData(dto); // DB에 데이터 저장 및 트랜잭션 발생
			adminServ.makeThumbnailImg(dto.getNftNum(), file);
			
			new MessageRedirectUtil().redirect("NFT 발급에 성공하였습니다.", "/admin/nftList");
		}

	}
	
	@PostMapping("/testTitle")
	@ResponseBody
	public int testTitle(String nftTitle) {
		log.trace("testTitle : " + nftTitle);
		return adminServ.testTitle(nftTitle);
	}
	
	
	@RequestMapping("/nftList")
	public String nftList(Model model, @RequestParam(defaultValue = "1") int typeNum, @RequestParam(defaultValue = "1")  int pageNum, @RequestParam(defaultValue = "10") int listNum) {
		
		ArrayList<DataTypeDto> dto = mainServ.getAllDataType();
		
		int count = mainServ.getAdminNftListCnt(typeNum);
		int totalPageCount = (int) Math.ceil(count / (float) listNum);
		int currentPage = pageNum;
		int beginPage = ((currentPage - 1) / 10) * 10 + 1;
		int endPage = ((currentPage - 1) / 10 + 1) * (10);
		if (endPage > totalPageCount) {
			endPage = totalPageCount;
		}
		
		model.addAttribute("type", typeNum);
		model.addAttribute("typeList", dto);
		model.addAttribute("beginPage", beginPage);
		model.addAttribute("endPage", endPage);
		model.addAttribute("currentPage", currentPage);
		model.addAttribute("totalPageCount", totalPageCount);
		model.addAttribute("listNum", listNum);
		model.addAttribute("yearData", mainServ.getNftDateList(typeNum));
		model.addAttribute("nftList", mainServ.getAdminNftListByDataType(typeNum, pageNum, listNum));
		model.addAttribute("count", count);
		
		return "/admin/nftList";
	}
	
	@PostMapping("/updateHideYn")
	@ResponseBody
	public void updateHideYn(int nftNum) {
		log.trace("숨기기 nfNum = {}", nftNum);
		mainServ.updateHideYn(nftNum);
	}
	
	@PostMapping("/updatedelYn")
	@ResponseBody
	public void updatedelYn(int nftNum, Principal principal) {
		
		// session token 값 추출
		MyAuthentication my = (MyAuthentication) principal;
//		String token = my.getUserToken();
		String nftId = mainServ.getNftId4Admin(nftNum);
		
//		int confirmReadRecord = tranServ.confirmReadRecord(nftId);

		// nftId 가 null 이면 삭제
		if (nftId == null || nftId.isEmpty() || nftId.isBlank()) {
			new MessageRedirectUtil().redirect("열람 이력이 있는 NFT 는 삭제가 불가능 합니다.", "/admin/nftList");
		} else {
			mainServ.updatedelYn(nftNum, nftId);
			new MessageRedirectUtil().redirect("nft 삭제가 완료되었습니다.", "/admin/nftList");
		}
		
	}
	
	@RequestMapping("/searchList")
	public String searchByConditions(Model model, @RequestParam(defaultValue = "0") int typeNum, @RequestParam(required = false) String searchType, @RequestParam(required = false) String searchWord, @RequestParam(defaultValue = "1")  int pageNum, @RequestParam(defaultValue = "10") int listNum){
		
		if (typeNum == 0) {
			return "redirect:./error";
		}
		
		if (searchWord == null || searchWord.trim().isEmpty() || searchType == null || searchType.isEmpty() ) {
			new MessageRedirectUtil().redirect("검색어를 입력해주세요.", "/admin/nftList");
		} else if (
				searchWord.contains("<") 
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
			new MessageRedirectUtil().redirect("검색어에 잘못된 값이 입력되었습니다.", "/admin/nftList");
			
		} else if (
				searchType.contains("<") 
				|| searchType.contains(">")
				|| searchType.contains("@")
				|| searchType.contains("#")
				|| searchType.contains("$")
				|| searchType.contains("&")
				|| searchType.contains("%")
				|| searchType.contains("|")
				|| searchType.contains("'")
				|| searchType.toLowerCase().contains("xml")
				|| searchType.toLowerCase().contains("script")
				|| searchType.toLowerCase().contains("select")
				|| searchType.toLowerCase().contains("delete")
				|| searchType.toLowerCase().contains("from")
				|| searchType.toLowerCase().contains("insert")
				|| searchType.toLowerCase().contains("update")
				|| searchType.toLowerCase().contains("drop")
			) {
			new MessageRedirectUtil().redirect("검색어에 잘못된 값이 입력되었습니다.", "/admin/nftList");
		}
		
		model.addAttribute("searchType", searchType);
		model.addAttribute("searchWord", searchWord);
		
		if (searchWord.trim().equals("발행")) {
			searchWord = "N";
		} else if (searchWord.trim().equals("삭제")) {
			searchWord = "Y";
		}
		
		ArrayList<DataTypeDto> dto = mainServ.getAllDataType();
		ArrayList<HashMap<String, Object>> ArrMap = mainServ.searchByConditions(typeNum, searchType, searchWord.trim(), pageNum, listNum);
		
		//int count = ArrMap.size();
		int count = mainServ.searchByConditionsCnt(typeNum, searchType, searchWord.trim());
		int totalPageCount = (int) Math.ceil(count / (float) listNum);
		int currentPage = pageNum;
		int beginPage = ((currentPage - 1) / 10) * 10 + 1;
		int endPage = ((currentPage - 1) / 10 + 1) * (10);
		if (endPage > totalPageCount) {
			endPage = totalPageCount;
		}
		
		model.addAttribute("type", typeNum);
		model.addAttribute("typeList", dto);
		model.addAttribute("beginPage", beginPage);
		model.addAttribute("endPage", endPage);
		model.addAttribute("currentPage", currentPage);
		model.addAttribute("totalPageCount", totalPageCount);
		model.addAttribute("yearData", mainServ.getNftDateList(typeNum));
		model.addAttribute("listNum", listNum);
		model.addAttribute("searchList", ArrMap);
		model.addAttribute("count", count);
		
		return "/admin/searchList";
	}

}
