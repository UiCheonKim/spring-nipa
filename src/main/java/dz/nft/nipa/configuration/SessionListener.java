package dz.nft.nipa.configuration;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

public class SessionListener implements HttpSessionListener {

	@Override
	public void sessionCreated(HttpSessionEvent se) {
		se.getSession().setMaxInactiveInterval(30 * 60); // 세션만료30분
	}

	@Override
	public void sessionDestroyed(HttpSessionEvent se) {

	}

}
