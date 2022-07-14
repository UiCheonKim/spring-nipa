package dz.nft.nipa.security.handlers;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import dz.nft.nipa.admin.service.AdminService;

@Component
public class AuthSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

	@Autowired
	AdminService adminService;

	private static final Logger logger = LoggerFactory.getLogger(AuthSuccessHandler.class);

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
		
		// String ip = CommonUtil.getClientIp(request);
		logger.info("::::::::::::::: 로그인 성공 :::::::::::::::");

		super.setDefaultTargetUrl("/admin/nftList");
		super.onAuthenticationSuccess(request, response, authentication);
	}

}
