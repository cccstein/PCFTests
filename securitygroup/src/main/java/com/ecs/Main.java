package com.ecs;

import java.io.IOException;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPClientConfig;
import org.apache.commons.net.ftp.FTPReply;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@Controller
@RequestMapping("/")
public class Main
{
	public static void main(String[] args) {
		SpringApplication.run(Main.class, args);
	}

	@RequestMapping(method = RequestMethod.GET)
	public @ResponseBody String root() {
		// read URL as an environment variable
		String webRequestUrl = System.getenv("MICROS_URL");
		if (webRequestUrl == null) {
			return "env var MICROS_URL:<url> is not set";
		}
		
		String webRequest;
		System.out.println("Making Http request to " + webRequestUrl);
		webRequest = "Http request to <i>" + webRequestUrl + "</i> succeeded: <b>";
		webRequest += makeHttpRequest(webRequestUrl) + "</b><br><hr>";
		
/*		webRequestUrl = "http://gturnquist-quoters.cfapps.io/api/random";
		System.out.println("Making Http request to " + webRequestUrl);
		webRequest += "Http request to <i>" + webRequestUrl + "</i> succeeded: <b>";
		webRequest += makeHttpRequest(webRequestUrl) + "</b><br><hr>";

		String ftpRequestUrl = "ftp.example.com";
		System.out.println("Making FTP request to " + ftpRequestUrl);
		String ftpRequest = "Ftp request to <i>" + ftpRequestUrl + "</i> succeeded: <b>";
		ftpRequest += makeFtpRequest(ftpRequestUrl) + "</b><br><hr>";
		ftpRequestUrl = "mirror.liquidtelecom.com";
		System.out.println("Making FTP request to " + ftpRequestUrl);
		ftpRequest += "Ftp request to <i>" + ftpRequestUrl + "</i> succeeded: <b>";
		ftpRequest += makeFtpRequest(ftpRequestUrl) + "</b><br><hr>";
		*/
		
		
		return webRequest;// + ftpRequest;
	}

	public boolean makeHttpRequest(String url) {
		boolean result = false;
		RestTemplate restTemplate = new RestTemplate();
		try {
			restTemplate.getForObject(url, String.class);
			result = true;
		} catch (Exception e) {
		}
		return result;
	}

	public boolean makeFtpRequest(String url) {
		boolean result = false;
		FTPClient ftp = new FTPClient();
		FTPClientConfig config = new FTPClientConfig();
		ftp.configure(config);
		try {
			int reply;
			String server = url;
			ftp.connect(server);
			// After connection attempt, you should check the reply code to
			// verify
			// success.
			reply = ftp.getReplyCode();

			if (FTPReply.isPositiveCompletion(reply)) {
				ftp.logout();
				result = true;
			} else {
				ftp.disconnect();
				result = false;
			}
		} catch (Exception e) {
			result = false;
		} finally {
			if (ftp.isConnected()) {
				try {
					ftp.disconnect();
				} catch (IOException ioe) {
					// do nothing
				}
			}
		}
		return result;
	}

	
	
	
}
