package com.AngelBarreraSanchez.Yopmail;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Connection.Method;
import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.AngelBarreraSanchez.Yopmail.Domain.Mail;

/**
 * YopmailReader 
 * @author Angel Barrera Sanchez
 * Simple yopmail reader tool
 */
public class YopmailReader {
	
	/** BASE MOBILE YOPMAIL URL */
	private final String YOPMAIL_BASE_URL = "http://m.yopmail.com/en/";
	
	/** MOBILE USER AGENT */
	private final String MOBILE_USER_AGENT = "Mozilla/5.0 (X11; Linux i686) AppleWebKit/537.4 (KHTML, like Gecko) Chrome/22.0.1229.79 Safari/537.4";
	
	/** Singleton Instance */
	private static YopmailReader instance;
	
	/** Private Constructor, avoid instance outside this class */
	private YopmailReader(){}
	
	/**
	 * Gets an instance of the YopmailReader
	 * @return a YopmailReader instance
	 */
	public static YopmailReader getInstance(){
		if(instance==null){
			instance = new YopmailReader();
		}
		return instance;
	}
	
	/**
	 * Returns a list of max 15 mail object
	 * @param yopmailUserName whith or whithout @yopmail.com
	 * @param page page number to get list
	 * @return the mail list
	 * @throws IOException
	 */
	public List<Mail> getIncomingMailList(final String yopmailUserName, final int page) throws IOException{
		Response res = Jsoup.connect(new StringBuffer().append(YOPMAIL_BASE_URL).append("inbox.php?login=").append(yopmailUserName.toLowerCase().replaceAll("@yopmail.com", "")).append("&p=").append(page<=0?1:page).append("&d=&ctrl=&scrl=&spam=true&yf=HZwD0ZGH5AwLlAGpjBGt0Aj&yp=YZGD0BQt3AGLjBGL4ZmNkBN&yj=RZGHjZmLlAwNkAmtmZGV4BN&v=2.6&r_c=&id=").toString())
				.userAgent(MOBILE_USER_AGENT)
				.method(Method.GET)
				.execute();
		
		Document doc = Jsoup.parse(res.body());
		Elements mails = doc.getElementsByClass("lm_m");
		List<Mail> mailList = new ArrayList<>(15);
		for(Element mail : mails){
			Mail m = new Mail();
			m.setAccount(yopmailUserName.toLowerCase().replaceAll("@yopmail.com", ""));
			m.setUrl(mail.attr("href"));
			m.setSubject(mail.getElementsByAttributeValue("class", "lms_m").get(0).text());
			m.setSender(mail.getElementsByAttributeValue("class", "lmf").get(0).text());
			mailList.add(m);
		}
		return mailList;
	}
	
	/**
	 * Returns a list of max 15 mail object from page 1
	 * @param yopmailUserName whith or whithout @yopmail.com
	 * @return the mail list
	 * @throws IOException
	 */
	public List<Mail> getIncomingMailList(final String yopmailUserName) throws IOException{
		return getIncomingMailList(yopmailUserName, 1);
	}
	
	/**
	 * Gets the email content in plain text
	 * @param mail mail object
	 * @return plain content of the email
	 * @throws IOException
	 */
	public String getMailContent(final Mail mail) throws IOException{
		Response res = Jsoup.connect(new StringBuffer().append(YOPMAIL_BASE_URL).append(mail.getUrl()).toString())
				.userAgent(MOBILE_USER_AGENT)
				.method(Method.GET)
				.execute();
		
		return Jsoup.parse(res.body()).getElementById("mailmillieu").text();
	}
	
	/**
	 * Gets the email content in plain text of the last incoming mail
	 * @param yopmailUserName whith or whithout @yopmail.com
	 * @return plain content of the email
	 * @throws IOException
	 */
	public String getLastMailContent(final String yopmailUserName) throws IOException{
		Response res = Jsoup.connect(new StringBuffer().append(YOPMAIL_BASE_URL).append("inbox.php?login=").append(yopmailUserName.toLowerCase().replaceAll("@yopmail.com", "")).append("&p=1").append("&d=&ctrl=&scrl=&spam=true&yf=HZwD0ZGH5AwLlAGpjBGt0Aj&yp=YZGD0BQt3AGLjBGL4ZmNkBN&yj=RZGHjZmLlAwNkAmtmZGV4BN&v=2.6&r_c=&id=").toString())
				.userAgent(MOBILE_USER_AGENT)
				.method(Method.GET)
				.execute();
		
		final Elements elements = Jsoup.parse(res.body()).getElementsByClass("lm_m");
		res = Jsoup.connect(new StringBuffer().append(YOPMAIL_BASE_URL).append(elements.get(0).attr("href")).toString())
				.userAgent(MOBILE_USER_AGENT)
				.cookies(res.cookies())
				.method(Method.GET)
				.execute();
		
		return Jsoup.parse(res.body()).getElementById("mailmillieu").text();
	}
}