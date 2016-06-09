package rsender;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class RSender implements CommandLineRunner {

	private int noOfSends = 0;


	@Autowired
	RabbitTemplate rabbitTemplate;
	
	@Autowired
	RabbitConfigSender rabbitConfigSender;

	public static void main(String[] args) throws InterruptedException {
		SpringApplication.run(RSender.class, args);
	}

	@RequestMapping("/")
	public String report() {
		return "Messages Sent: " + noOfSends + "<br>";
	}

	@Override
	public void run(String... args) throws Exception {
		while (true) {
			System.out.println("Waiting five seconds...");
			Thread.sleep(5000);
			System.out.println("Sending message...");
			rabbitTemplate.convertAndSend(rabbitConfigSender.exchangeName,rabbitConfigSender.routingKey, "Hello from RSender");
			noOfSends++;
		}
	}
}
