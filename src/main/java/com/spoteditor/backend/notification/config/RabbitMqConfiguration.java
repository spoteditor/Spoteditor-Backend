package com.spoteditor.backend.notification.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMqConfiguration {

	public static final String FANOUT_EXCHANGE_NAME = "announceExchange";
	public static final String TOPIC_EXCHANGE_NAME = "activityExchange";
	public static final String DIRECT_EXCHANGE_NAME = "followExchange";

	public static final String FOLLOW_QUEUE = "followQueue";
	public static final String ANNOUNCEMENT_QUEUE = "announcementQueue";
	public static final String ACTIVITY_QUEUE = "activityQueue";

	public static final String FOLLOW_ROUTING_KEY = "follow";
	public static final String ACTIVITY_ROUTING_KEY = "user.activity.#";

	@Bean
	public Queue followQueue() {
		return new Queue(FOLLOW_QUEUE, true);
	}

	@Bean
	public Queue activityQueue() {
		return new Queue(ACTIVITY_QUEUE, true);
	}

	@Bean
	public Queue announcementQueue() {
		return new Queue(ANNOUNCEMENT_QUEUE, true);
	}

	@Bean
	public FanoutExchange fanoutExchange() {
		return new FanoutExchange(FANOUT_EXCHANGE_NAME);
	}

	@Bean
	public TopicExchange topicExchange() {
		return new TopicExchange(TOPIC_EXCHANGE_NAME);
	}

	@Bean
	public DirectExchange directExchange() {
		return new DirectExchange(DIRECT_EXCHANGE_NAME);
	}

	@Bean
	public Binding announcementBinding(Queue announcementQueue, FanoutExchange fanoutExchange) {
		return BindingBuilder.bind(announcementQueue).to(fanoutExchange);
	}

	@Bean
	public Binding activityBinding(Queue activityQueue, TopicExchange topicExchange) {
		return BindingBuilder.bind(activityQueue).to(topicExchange).with(ACTIVITY_ROUTING_KEY);
	}

	@Bean
	public Binding followBinding(Queue followQueue, DirectExchange directExchange) {
		return BindingBuilder.bind(followQueue).to(directExchange).with(FOLLOW_ROUTING_KEY);
	}
}
