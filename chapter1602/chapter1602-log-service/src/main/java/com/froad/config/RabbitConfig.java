package com.froad.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.froad.rabbit.Receiver;

/**
 * @author ZHUZIHUI
 * @date 2018年11月13日
 */
@Configuration
public class RabbitConfig {

	public final static String QUEUE_NAME = "spring-boot";

	@Bean
	Queue queue() {
		return new Queue(QUEUE_NAME, false);
	}

	@Bean
	TopicExchange exchange() {
		return new TopicExchange("spring-boot-exchange");
	}

	@Bean
	Binding binding(Queue queue, TopicExchange exchange) {
		return BindingBuilder.bind(queue).to(exchange).with(QUEUE_NAME);
	}

	@Bean
	SimpleMessageListenerContainer container(ConnectionFactory connectionFactory,
			MessageListenerAdapter messageListenerAdapter) {
		SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
		container.setConnectionFactory(connectionFactory);
		container.setQueueNames(QUEUE_NAME);
		container.setMessageListener(messageListenerAdapter);
		return container;
	}

	@Bean
	MessageListenerAdapter messageListenerAdapter(Receiver receiver) {
		return new MessageListenerAdapter(receiver, "receiveMessage");
	}
}
