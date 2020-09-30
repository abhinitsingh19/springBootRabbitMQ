package com.rabbitmq.config;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;



@Configuration
public class MessagingConfig 
{

	public static final String RABBITMQ_ROUTING_KEY = "rabbitmqRoutingKey";
	public static final String RABBITMQ_TOPIC_EXCHANGE = "rabbitmqTopicExchange";
	public static final String RABBITMQ_QUEUE = "rabbitmqQueue";

	@Bean
	public Queue queue()
	{
		return new Queue(RABBITMQ_QUEUE);
		
	}
	@Bean
	public TopicExchange exchange()
	{
		return new TopicExchange(RABBITMQ_TOPIC_EXCHANGE);
		
	}
	@Bean
	public Binding bindingQueueWithExchnageViaARoutingKey()
	{
		return BindingBuilder.bind(queue()).to(exchange()).with(RABBITMQ_ROUTING_KEY);
		
	}
	@Bean
	public MessageConverter converter()
	{
		return new Jackson2JsonMessageConverter();
	}
	
	@Bean
	public AmqpTemplate template(ConnectionFactory connectionFactory)
	{
		final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
		rabbitTemplate.setMessageConverter(converter());
		return rabbitTemplate;
	}
	
	
	
	
	
}
