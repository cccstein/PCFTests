package rconsumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@SpringBootApplication
@RestController
public class RConsumer {
	
	@Autowired
	Receiver receiver;

	public static void main(String[] args) throws InterruptedException {
		SpringApplication.run(RConsumer.class, args);
	}
	
	@RequestMapping("/")
	public String report() {
		return "Messages Received: " + receiver.noOfReceives + "<br>";
	}
}
