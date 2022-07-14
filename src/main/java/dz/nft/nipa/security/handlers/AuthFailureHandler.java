package dz.nft.nipa.security.handlers;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import dz.nft.nipa.admin.service.AdminService;
import dz.nft.nipa.utils.MessageRedirectUtil;

@Component
public class AuthFailureHandler implements AuthenticationFailureHandler {

	@Autowired
	AdminService adminService;

	private static final Logger logger = LoggerFactory.getLogger(AuthSuccessHandler.class);

	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
		// String ip = CommonUtil.getClientIp(request);
		logger.info("::::::::::::::: 로그인 실패 :::::::::::::::");

		String id = exception.getMessage();
		
		//response.sendRedirect("/admin/login?yu="+URLEncoder.encode(mess));
		
		/*
		// url 주소가 /admin/loginProc으로 출력됨. 사용 x
		request.setAttribute("mess", mess);
		RequestDispatcher rd = request.getRequestDispatcher("/admin/login");
		rd.forward(request, response);
		*/
		
		/*
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		out.println("<script>alert('"+id+"'); location.href='/admin/login';</script>");
		out.flush();
		*/
		
		new MessageRedirectUtil().redirect(id, "/admin/login");
	}
	
}
