package rsender;

import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class RabbitConfigSender {
	
	String exchangeName = "rsender-exchange";
	String routingKey = "rsenderRoutingKey";
	
	@Bean
	TopicExchange exchange() {
		return new TopicExchange(exchangeName);
	}
  

}
