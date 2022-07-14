package dz.nft.nipa.common.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class CommonController {
	
	@RequestMapping("/mainHeader")
	public String mainHeader() {
		return "/common/mainHeader";
	}
	
	@RequestMapping("/header")
	public String header() {
		return "/common/header";
	}
	
	@RequestMapping("/footer")
	public String footer() {
		return "/common/footer";
	}
	
	@RequestMapping("/terms")
	public String terms() {
		return "/common/terms";
	}
	
}
