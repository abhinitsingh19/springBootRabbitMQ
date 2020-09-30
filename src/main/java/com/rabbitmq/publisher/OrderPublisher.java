package com.rabbitmq.publisher;

import java.util.UUID;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.rabbitmq.config.MessagingConfig;
import com.rabbitmq.dto.Order;
import com.rabbitmq.dto.OrderStatus;

@RestController
public class OrderPublisher 
{
	@Autowired
	private RabbitTemplate template;
	
	@RequestMapping(value = "/order/{restaurentName}", method = RequestMethod.POST)
	public String bookOrder(@RequestBody Order order,@PathVariable String restaurentName)
	{
		order.setOrderId(UUID.randomUUID().toString());
		//restaurentservice
		//paymentService
		OrderStatus orderStatus = new OrderStatus(order, "In Progress", "order Placed Successfully In "+restaurentName);
		template.convertAndSend(MessagingConfig.RABBITMQ_TOPIC_EXCHANGE, MessagingConfig.RABBITMQ_ROUTING_KEY, orderStatus);
		
		return "success";
		
	}

}
