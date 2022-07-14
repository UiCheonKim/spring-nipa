package dz.nft.nipa.utils;

import java.util.Date;

public class UnixTimeUtil {
	
	public String makeUnixTime() {
		
		Date today = new Date();
		long unixTime = (long) today.getTime() / 1000;
		
		/*
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss z");
		String todayDateFormat = dateFormat.format(today);
		*/
		
		return Long.toString(unixTime);
	}
	
}
