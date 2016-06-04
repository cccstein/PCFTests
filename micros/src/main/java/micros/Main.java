package micros;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class Main {
	private int authedRequests;
	private int nonAuthedRequests;

	private static final String CONSUMER_KEY = "abcdefg";
	private static final String CONSUMER_SECRET = "123456";

	public static void main(String[] args) {
		SpringApplication.run(Main.class, args);
	}

	@RequestMapping("/service")
	public String service(@RequestParam(value = "consumerKey", defaultValue = "") String consumerKey,
			@RequestParam(value = "consumerSecret", defaultValue = "") String consumerSecret) {
		if ((consumerKey != null) && (consumerSecret != null) && (consumerKey.equals(CONSUMER_KEY))
				&& (consumerSecret.equals(CONSUMER_SECRET))) {
			authedRequests++;
		} else {
			nonAuthedRequests++;
		}
		return "service";
	}
	
	@RequestMapping("/") 
	public String report() {
		return "Authenticated requests:" + authedRequests + "<br>" +
				"Non-Authenticated requests:" + nonAuthedRequests + "<br>";
	}
}
